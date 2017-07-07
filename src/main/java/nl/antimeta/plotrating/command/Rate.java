package nl.antimeta.plotrating.command;

import nl.antimeta.bukkit.framework.command.annotation.Command;
import nl.antimeta.bukkit.framework.command.model.BukkitCommand;
import nl.antimeta.bukkit.framework.command.model.BukkitPlayerCommand;
import nl.antimeta.plotrating.PRDatabase;
import nl.antimeta.plotrating.entity.Plot;
import nl.antimeta.plotrating.entity.Rating;
import nl.antimeta.plotrating.model.RateStatus;
import nl.antimeta.plotrating.util.ResponseUtil;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

@Command(main = "rate",
        permission = "pr.rate")
public class Rate extends PlotCommand {

    private final int minRatings;
    private final int maxRatings;
    private final int minRating;
    private final int maxRating;

    public Rate(FileConfiguration config) {
        minRatings = config.getInt("min-ratings");
        maxRatings = config.getInt("max-ratings");
        minRating = config.getInt("min-rating");
        maxRating = config.getInt("max-rating");
    }

    @Override
    protected boolean onPlotCommand(BukkitPlayerCommand bukkitPlayerCommand) {
        String[] args = bukkitPlayerCommand.getArgs();
        CommandSender sender = bukkitPlayerCommand.getSender();

        if (args.length == 0) {
            ResponseUtil.missingRating(sender);
            return true;
        }

        if (!StringUtils.isNumeric(args[0])) {
            ResponseUtil.wrongRating(sender);
            return true;
        }

        Integer intRating = Integer.valueOf(args[0]);

        if (intRating >= minRating && intRating <= maxRating) {
            Plot databasePlot = PRDatabase.getInstance().getPlotFromSquared(basePlot.getId().x, basePlot.getId().y);
            if (databasePlot == null) {
                ResponseUtil.plotRateNotRequested(sender);
            } else {
                if (databasePlot.getPlayerUUID().equals(bukkitPlayerCommand.getPlayerUUID().toString())) {
                    return ResponseUtil.cannotRateOwnPlot(sender);
                }

                RateStatus status = RateStatus.findStatus(databasePlot.getRateStatus());
                if (status != null) {
                    switch (status) {
                        case ACCEPTED:
                            return ResponseUtil.plotAlreadyAccepted(sender);
                        case REJECTED:
                            return ResponseUtil.plotAlreadyRejected(sender);
                        case PENDING:
                            List<Rating> currentRatings = PRDatabase.getInstance().getRatings(databasePlot);
                            if (currentRatings.size() >= maxRatings) {
                                return ResponseUtil.maxRatingsOnPlot(sender);
                            } else {

                                for (Rating rating : currentRatings) {
                                    if (rating.getPlayerUUID().equals(bukkitPlayerCommand.getPlayerUUID().toString())) {
                                        return ResponseUtil.cannotRateThePlotMultipleTimes(sender);
                                    }
                                }

                                Rating rating = new Rating(databasePlot.getId(), Integer.valueOf(args[0]), bukkitPlayerCommand.getPlayerUUID().toString());
                                if (args[1] != null) {
                                    String description = null;

                                    for (int i = 1; i < args.length - 1; i++) {
                                        if (description == null) {
                                            description = args[i];
                                        } else {
                                            description = description + " " + args[i];
                                        }
                                    }

                                    rating.setDescription(description);
                                }

                                PRDatabase.getInstance().saveRating(rating);
                                currentRatings.add(rating);
                                if (currentRatings.size() >= minRatings) {
                                    return ResponseUtil.plotReachedEnoughRatings(sender);
                                } else {
                                    return ResponseUtil.plotRatingSend(sender);
                                }
                            }
                    }
                }
            }
        } else {
            ResponseUtil.wrongRating(sender, minRating, maxRating);
        }

        return true;
    }

    @Override
    protected void onNoPermission(BukkitCommand bukkitCommand) {
        ResponseUtil.noPermission(bukkitCommand.getSender());
    }
}

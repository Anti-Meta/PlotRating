package nl.antimeta.plotrating.command;

import nl.antimeta.plotrating.PlotRatingDatabase;
import nl.antimeta.plotrating.entity.Plot;
import nl.antimeta.plotrating.entity.Rating;
import nl.antimeta.plotrating.model.RateStatus;
import nl.antimeta.plotrating.util.ResponseUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class Rate extends PlotCommand {

    private final int minRatings;
    private final int maxRatings;

    public Rate(FileConfiguration config) {
        super("rate");
        minRatings = config.getInt("min-ratings");
        maxRatings = config.getInt("max-ratings");
    }

    @Override
    boolean executePlayerPlotCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            return false;
        }

        Plot databasePlot = PlotRatingDatabase.getInstance().getPlotFromSquared(basePlot.getId().x, basePlot.getId().y);
        if (databasePlot == null) {
            ResponseUtil.plotRateNotRequested(sender);
        } else {
            if (databasePlot.getPlayerUUID().equals(playerUUID.toString())) {
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
                        List<Rating> currentRatings = PlotRatingDatabase.getInstance().getRatings(databasePlot.getId());
                        if (currentRatings.size() >= maxRatings) {
                            return ResponseUtil.maxRatingsOnPlot(sender);
                        } else {

                            for (Rating rating : currentRatings) {
                                if (rating.getPlayerUUID().equals(playerUUID.toString())) {
                                    return ResponseUtil.cannotRateThePlotMultipleTimes(sender);
                                }
                            }

                            Rating rating = new Rating(databasePlot.getId(), Integer.valueOf(args[0]), playerUUID.toString());
                            if (args.length > 1) {
                                rating.setDescription(args[1]);
                            }

                            PlotRatingDatabase.getInstance().saveRating(rating);
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

        return true;
    }
}

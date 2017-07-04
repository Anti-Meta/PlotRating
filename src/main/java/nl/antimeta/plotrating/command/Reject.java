package nl.antimeta.plotrating.command;

import nl.antimeta.bukkit.framework.command.annotation.Command;
import nl.antimeta.bukkit.framework.command.model.BukkitCommand;
import nl.antimeta.bukkit.framework.command.model.BukkitPlayerCommand;
import nl.antimeta.plotrating.PlotRatingDatabase;
import nl.antimeta.plotrating.entity.Plot;
import nl.antimeta.plotrating.entity.Rating;
import nl.antimeta.plotrating.model.RateStatus;
import nl.antimeta.plotrating.util.ResponseUtil;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

@Command(main = "reject",
        permission = "pr.reject",
        aliases = {"rejected"})
public class Reject extends PlotCommand {

    private final int minRatings;

    Reject(FileConfiguration config) {
        minRatings = config.getInt("min-ratings");
    }

    @Override
    protected boolean onPlotCommand(BukkitPlayerCommand bukkitPlayerCommand) {

        if (owner) {
            return ResponseUtil.cannotChangeStatusOfOwnPlot(bukkitPlayerCommand.getPlayer());
        } else {
            Plot databasePlot = PlotRatingDatabase.getInstance().getPlotFromSquared(basePlot.getId().x, basePlot.getId().y);
            if (databasePlot == null) {
                ResponseUtil.plotRateNotRequested(bukkitPlayerCommand.getPlayer());
            } else {
                List<Rating> currentRatings = PlotRatingDatabase.getInstance().getRatings(databasePlot.getId());
                if (currentRatings.size() >= minRatings) {
                    databasePlot.setRateStatus(RateStatus.REJECTED.getStatus());
                    PlotRatingDatabase.getInstance().savePlot(databasePlot);
                    ResponseUtil.plotRateStatusChanged(bukkitPlayerCommand.getPlayer(), RateStatus.REJECTED);
                } else {
                    ResponseUtil.plotNeedsMoreRatings(bukkitPlayerCommand.getPlayer(), currentRatings.size(), minRatings, RateStatus.REJECTED);
                }
            }
        }

        return true;
    }

    @Override
    protected void onNoPermission(BukkitCommand bukkitCommand) {
        ResponseUtil.noPermission(bukkitCommand.getSender());
    }
}

package nl.antimeta.plotrating.command;

import nl.antimeta.bukkit.framework.command.annotation.Command;
import nl.antimeta.bukkit.framework.command.model.BukkitCommand;
import nl.antimeta.bukkit.framework.command.model.BukkitPlayerCommand;
import nl.antimeta.plotrating.PRDatabase;
import nl.antimeta.plotrating.entity.Plot;
import nl.antimeta.plotrating.entity.Rating;
import nl.antimeta.plotrating.model.RateStatus;
import nl.antimeta.plotrating.util.RatingUtil;
import nl.antimeta.plotrating.util.ResponseUtil;

import java.util.List;

@Command(main = "status",
        permission = "pr.status")
public class Status extends PlotCommand {

    @Override
    protected boolean onPlotCommand(BukkitPlayerCommand bukkitPlayerCommand) {
        String adminPermission = bukkitPlayerCommand.getPermission() + ".admin";
        if (owner || bukkitPlayerCommand.getPlayer().hasPermission(adminPermission)) {
            Plot databasePlot = PRDatabase.getInstance().getPlotFromSquared(basePlot.getId().x, basePlot.getId().y);
            if (databasePlot != null) {
                List<Rating> plotRatings = PRDatabase.getInstance().getRatings(databasePlot);

                double averageRating = RatingUtil.getAverageRating(plotRatings);

                ResponseUtil.plotStatus(bukkitPlayerCommand.getSender(), RateStatus.findStatus(databasePlot.getRateStatus()), averageRating);
            } else {
                ResponseUtil.plotRateNotRequested(bukkitPlayerCommand.getSender());
            }
        }
        return true;
    }

    @Override
    protected void onNoPermission(BukkitCommand bukkitCommand) {
        ResponseUtil.noPermission(bukkitCommand.getSender());
    }
}

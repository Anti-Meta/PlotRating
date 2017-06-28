package nl.antimeta.plotrating.command;

import nl.antimeta.bukkit.framework.command.annotation.Command;
import nl.antimeta.bukkit.framework.command.model.BukkitCommand;
import nl.antimeta.bukkit.framework.command.model.BukkitPlayerCommand;
import nl.antimeta.plotrating.PlotRatingDatabase;
import nl.antimeta.plotrating.entity.Plot;
import nl.antimeta.plotrating.model.RateStatus;
import nl.antimeta.plotrating.util.ResponseUtil;

@Command(main = "status",
        permission = "pr.status")
public class Status extends PlotCommand {

    @Override
    protected boolean onPlotCommand(BukkitPlayerCommand bukkitPlayerCommand) {
        if (owner) {
            Plot databasePlot = PlotRatingDatabase.getInstance().getPlotFromSquared(basePlot.getId().x, basePlot.getId().y);
            if (databasePlot != null) {
                return ResponseUtil.plotStatus(bukkitPlayerCommand.getSender(), RateStatus.findStatus(databasePlot.getRateStatus()));
            }
        }
        return false;
    }

    @Override
    protected void onNoPermission(BukkitCommand bukkitCommand) {
        ResponseUtil.noPermission(bukkitCommand.getSender());
    }
}

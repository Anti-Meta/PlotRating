package nl.antimeta.plotrating.command;

import com.intellectualcrafters.plot.object.Plot;
import nl.antimeta.bukkit.framework.command.PlayerCommand;
import nl.antimeta.bukkit.framework.command.model.BukkitPlayerCommand;
import nl.antimeta.plotrating.util.PlotUtil;
import nl.antimeta.plotrating.util.ResponseUtil;

public abstract class PlotCommand extends PlayerCommand {

    Plot basePlot;
    boolean owner;

    @Override
    protected boolean onPlayerCommand(BukkitPlayerCommand bukkitPlayerCommand) {
        Plot currentPlot = PlotUtil.getPlot(bukkitPlayerCommand.getPlayer().getLocation());

        if (currentPlot == null) {
            return ResponseUtil.notAPlot(bukkitPlayerCommand.getSender());
        } else {
            if (currentPlot.isBasePlot()) {
                basePlot = currentPlot;
            } else {
                basePlot = currentPlot.getBasePlot(true);
            }
            owner = basePlot.isOwner(bukkitPlayerCommand.getPlayerUUID());
            return onPlotCommand(bukkitPlayerCommand);
        }
    }

    protected abstract boolean onPlotCommand(BukkitPlayerCommand bukkitPlayerCommand);
}

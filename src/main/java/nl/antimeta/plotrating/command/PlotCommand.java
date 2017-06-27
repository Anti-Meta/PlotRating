package nl.antimeta.plotrating.command;

import com.intellectualcrafters.plot.object.Plot;
import nl.antimeta.plotrating.util.PlotUtil;
import nl.antimeta.plotrating.util.ResponseUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public abstract class PlotCommand extends PlayerCommand {

    Plot basePlot;
    boolean owner;

    PlotCommand(String... subCommands) {
        super(subCommands);
    }

    @Override
    public boolean executePlayerCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Plot currentPlot = PlotUtil.getPlot(player.getLocation());

        if (currentPlot == null) {
            return ResponseUtil.notAPlot(sender);
        } else {
            if (currentPlot.isBasePlot()) {
                basePlot = currentPlot;
            } else {
                basePlot = currentPlot.getBasePlot(true);
            }
            owner = basePlot.isOwner(playerUUID);
            return executePlayerPlotCommand(sender, cmd, label, args);
        }
    }

    abstract boolean executePlayerPlotCommand(CommandSender sender, Command cmd, String label, String[] args);
}

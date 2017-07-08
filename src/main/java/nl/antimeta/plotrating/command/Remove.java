package nl.antimeta.plotrating.command;

import nl.antimeta.bukkit.framework.command.annotation.Command;
import nl.antimeta.bukkit.framework.command.model.BukkitCommand;
import nl.antimeta.bukkit.framework.command.model.BukkitPlayerCommand;
import nl.antimeta.plotrating.PRDatabase;
import nl.antimeta.plotrating.util.ResponseUtil;

import java.sql.SQLException;

@Command(main = "remove",
        permission = "pr.remove",
        aliases = {"delete", "clear"})
public class Remove extends PlotCommand {

    @Override
    protected boolean onPlotCommand(BukkitPlayerCommand bukkitPlayerCommand) {
        try {
            if (PRDatabase.getInstance().deletePlotAndRatings(basePlot.getId())) {
                ResponseUtil.plotCleared(bukkitPlayerCommand.getPlayer());
            } else {
                ResponseUtil.nothingToDelete(bukkitPlayerCommand.getPlayer());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    protected void onNoPermission(BukkitCommand bukkitCommand) {
        ResponseUtil.noPermission(bukkitCommand.getSender());
    }
}

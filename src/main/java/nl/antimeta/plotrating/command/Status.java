package nl.antimeta.plotrating.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class Status extends PlotCommand {

    public Status() {
        super("status");
    }

    @Override
    boolean executePlayerPlotCommand(CommandSender sender, Command cmd, String label, String[] args) {
        return false;
    }
}

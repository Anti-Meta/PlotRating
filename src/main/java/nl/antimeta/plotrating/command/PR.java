package nl.antimeta.plotrating.command;

import nl.antimeta.bukkit.framework.command.MainCommand;
import nl.antimeta.plotrating.Constants;
import org.bukkit.configuration.file.FileConfiguration;

public class PR extends MainCommand {
    public PR(FileConfiguration config) {
        super(Constants.MainCommand, Constants.Auteur);

        addSubCommand(new Rate(config));
        addSubCommand(new Request());
        addSubCommand(new Status());
        addSubCommand(new Help());
        addSubCommand(new Pending());
        addSubCommand(new Accept(config));
        addSubCommand(new Reject(config));
        addSubCommand(new Remove());
    }
}

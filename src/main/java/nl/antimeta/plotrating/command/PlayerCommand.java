package nl.antimeta.plotrating.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public abstract class PlayerCommand extends BaseCommand {

    Player player;
    UUID playerUUID;

    PlayerCommand(String... subCommands) {
        super(subCommands);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            this.player = (Player) sender;
            playerUUID = player.getUniqueId();
            return executePlayerCommand(sender, cmd, label, args);
        }

        return true;
    }

    public abstract boolean executePlayerCommand(CommandSender sender, Command cmd, String label, String[] args);
}

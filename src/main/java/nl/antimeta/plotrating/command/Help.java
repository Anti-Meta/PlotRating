package nl.antimeta.plotrating.command;

import nl.antimeta.bukkit.framework.command.PlayerCommand;
import nl.antimeta.bukkit.framework.command.annotation.Command;
import nl.antimeta.bukkit.framework.command.model.BukkitCommand;
import nl.antimeta.bukkit.framework.command.model.BukkitPlayerCommand;
import nl.antimeta.plotrating.util.ResponseUtil;
import org.bukkit.entity.Player;

@Command(main = "help")
public class Help extends PlayerCommand {
    @Override
    protected boolean onPlayerCommand(BukkitPlayerCommand bukkitPlayerCommand) {
        Player player = bukkitPlayerCommand.getPlayer();
        ResponseUtil.helpCommand(player);
        return true;
    }

    @Override
    protected void onNoPermission(BukkitCommand bukkitCommand) {

    }
}

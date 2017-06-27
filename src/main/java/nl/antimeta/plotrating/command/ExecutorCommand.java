package nl.antimeta.plotrating.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ExecutorCommand implements CommandExecutor {

    private HashMap<String, CommandExecutor> commandMap = new HashMap<>();

    public void registerCommand(List<String> commands, CommandExecutor commandExecutor) {
        for (String command : commands) {
            registerCommand(command, commandExecutor);
        }
    }

    public void registerCommand(String command, CommandExecutor commandExecutor) {
        if (commandExecutor == null || command == null || command.isEmpty()) {
            throw new IllegalArgumentException("invalid command paramters specified");
        }
        this.commandMap.put(command.toLowerCase(), commandExecutor);
    }

    private CommandExecutor getCommandExecutor(String command) {
        return this.commandMap.get(command.toLowerCase());
    }

    private boolean hasCommand(String command) {
        return this.commandMap.containsKey(command.toLowerCase());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length >= 1 && hasCommand(args[0])) {
            CommandExecutor executor = this.getCommandExecutor(args[0]);
            return executor.onCommand(sender, cmd, label, removeFirstArg(args));
        } else {
            //TODO add help command
            //sender.sendMessage(ChatColor.GREEN + "Do " + ChatColor.AQUA + "/plotrate help " + ChatColor.GREEN + "to see help for the plugin.");
            return false;
        }
    }

    private String[] removeFirstArg(String[] args) {
        return Arrays.copyOfRange(args, 1, args.length);
    }
}

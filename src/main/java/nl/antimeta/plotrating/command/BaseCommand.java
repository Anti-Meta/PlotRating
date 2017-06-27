package nl.antimeta.plotrating.command;

import org.bukkit.command.CommandExecutor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class BaseCommand implements CommandExecutor {

    public final List<String> SUBS;

    BaseCommand(String... subCommands) {
        SUBS = new ArrayList<>();
        Collections.addAll(SUBS, subCommands);
    }
}

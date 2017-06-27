package nl.antimeta.plotrating;

import com.intellectualcrafters.plot.PS;
import nl.antimeta.plotrating.command.ExecutorCommand;
import nl.antimeta.plotrating.command.Rate;
import nl.antimeta.plotrating.command.Request;
import nl.antimeta.plotrating.model.DatabaseModel;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginLogger;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

import static com.intellectualcrafters.plot.util.MainUtil.sendMessage;

public class Main extends JavaPlugin implements Listener {

    private static Main main;

    private static final String MAIN = "plotrate";
    private static final String PR_RATE = "pr-rate";
    private static final String PR_REQUEST = "pr-request";
    private static final String PR_STATUS = "pr-status";

    @Override
    public void onEnable() {
        main = this;
        setupPlotSquired();
        setupConfig();
        setupDatabase();
        setupCommands();
    }

    public static PluginLogger getStaticLogger() {
        return (PluginLogger) main.getLogger();
    }

    private void setupCommands() {

        Rate rate = new Rate(getConfig());
        Request request = new Request();

        ExecutorCommand executor = new ExecutorCommand();
        executor.registerCommand(rate.SUBS, rate);
        executor.registerCommand(request.SUBS, request);

        this.getCommand(PR_RATE).setExecutor(rate);
        this.getCommand(PR_REQUEST).setExecutor(request);
        this.getCommand(MAIN).setExecutor(executor);

        this.getLogger().info("PlotRating Enabled");
    }

    private void setupConfig() {
        try {
            if (!getDataFolder().exists()) {
                getDataFolder().mkdirs();
            }

            File config = new File(getDataFolder(), "config.yml");

            if (!config.exists()) {
                getLogger().info("Config.yml not found, creating!");
                getConfig().options().copyDefaults(true);
                saveDefaultConfig();
            } else {
                getLogger().info("Config.yml found, loading!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupPlotSquired() {
        final Plugin plotsquared = Bukkit.getServer().getPluginManager().getPlugin("PlotSquared");

        if ((PS.get() == null) || !plotsquared.isEnabled()) {
            sendMessage(null, "&c Could not find plotsquared! Disabling plugin...");
            Bukkit.getServer().getPluginManager().disablePlugin(this);
            return;
        }

        Bukkit.getServer().getPluginManager().registerEvents(this, this);
    }

    private void setupDatabase() {
        try {
            DatabaseModel databaseModel = new DatabaseModel(getConfig());
            getLogger().info(databaseModel.getHost());
            getLogger().info(databaseModel.getDatabase());
            getLogger().info(String.valueOf(databaseModel.getPort()));
            getLogger().info(databaseModel.getUsername());
            getLogger().info(databaseModel.getPassword());
            PlotRatingDatabase database = PlotRatingDatabase.getInstance(databaseModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {

    }
}

package nl.antimeta.plotrating;

import com.intellectualcrafters.plot.PS;
import nl.antimeta.plotrating.command.PR;
import nl.antimeta.plotrating.listener.OnPlotClearListener;
import nl.antimeta.plotrating.model.DatabaseModel;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginLogger;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

import static com.intellectualcrafters.plot.util.MainUtil.sendMessage;

public class Main extends JavaPlugin {

    private static Main main;

    @Override
    public void onEnable() {
        main = this;
        setupPlotSquired();
        setupConfig();
        setupDatabase();
        setupListeners();
        setupCommands();
    }

    private void setupListeners() {
        new OnPlotClearListener(this);
    }

    public static PluginLogger getStaticLogger() {
        return (PluginLogger) main.getLogger();
    }

    private void setupCommands() {
        this.getCommand(Constants.MainCommand).setExecutor(new PR(getConfig()));
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
    }

    private void setupDatabase() {
        try {
            DatabaseModel databaseModel = new DatabaseModel(getConfig());
            getLogger().info(databaseModel.getHost());
            getLogger().info(databaseModel.getDatabase());
            getLogger().info(String.valueOf(databaseModel.getPort()));
            getLogger().info(databaseModel.getUsername());
            getLogger().info(databaseModel.getPassword());
            PRDatabase.setupInstance(databaseModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {

    }
}

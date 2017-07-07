package nl.antimeta.plotrating.listener;

import com.plotsquared.bukkit.events.PlotClearEvent;
import nl.antimeta.plotrating.Main;
import nl.antimeta.plotrating.PRDatabase;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.sql.SQLException;

public class OnPlotClearListener implements Listener {

    public OnPlotClearListener(Main main) {
        main.getServer().getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    public void onPlotClearEvent(PlotClearEvent event) {
        executeListener(event);
    }

    private void executeListener(PlotClearEvent event) {
        Main.getStaticLogger().info("Calling onPlotDeleteEvent");

        try {
            PRDatabase.getInstance().deletePlotAndRatings(event.getPlotId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

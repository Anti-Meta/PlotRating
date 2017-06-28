package nl.antimeta.plotrating.listener;

import com.plotsquared.bukkit.events.PlotDeleteEvent;
import nl.antimeta.plotrating.PlotRatingDatabase;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.sql.SQLException;

public class OnPlotDeleteListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlotDeleteEvent(PlotDeleteEvent event) {
        try {
            PlotRatingDatabase.getInstance().deletePlotAndRatings(event.getPlotId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

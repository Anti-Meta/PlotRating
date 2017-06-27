package nl.antimeta.plotrating.util;

import com.intellectualcrafters.plot.object.Plot;
import org.bukkit.Location;

public class PlotUtil {

    public static Plot getPlot(Location location) {
        return BukkitUtil.getLocation(location).getPlot();
    }
}

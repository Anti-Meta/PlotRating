package nl.antimeta.plotrating.util;

import com.intellectualcrafters.plot.api.PlotAPI;
import com.intellectualcrafters.plot.object.Plot;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PlotUtil {

    private static final PlotAPI api = new PlotAPI();

    public static Plot getPlot(Location location) {
        return BukkitUtil.getLocation(location).getPlot();
    }

    public static Plot getPlot(Player player, int x, int y) {
        Set<Plot> playerPlots = api.getPlayerPlots(player);
        List<Plot> listPlayerPlots = new ArrayList<>();
        listPlayerPlots.addAll(playerPlots);
        for (Plot plot : listPlayerPlots) {
            if (plot.getId().x == x && plot.getId().y == y) {
                return plot;
            }
        }
        return null;
    }
}

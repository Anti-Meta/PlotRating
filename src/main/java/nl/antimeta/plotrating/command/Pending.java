package nl.antimeta.plotrating.command;

import nl.antimeta.bukkit.framework.command.PlayerCommand;
import nl.antimeta.bukkit.framework.command.annotation.Command;
import nl.antimeta.bukkit.framework.command.model.BukkitCommand;
import nl.antimeta.bukkit.framework.command.model.BukkitPlayerCommand;
import nl.antimeta.plotrating.PRDatabase;
import nl.antimeta.plotrating.entity.Plot;
import nl.antimeta.plotrating.entity.Rating;
import nl.antimeta.plotrating.util.ResponseUtil;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;

import java.util.List;

@Command(main = "pending",
    permission = "pr.pending")
public class Pending extends PlayerCommand {

    @Override
    protected boolean onPlayerCommand(BukkitPlayerCommand bukkitPlayerCommand) {
        List<Plot> pendingPlots = PRDatabase.getInstance().findAllPendingPlots();

        if (bukkitPlayerCommand.getArgs().length == 0) {
            showFirstPageOfPendingPlots(bukkitPlayerCommand.getPlayer(), pendingPlots);
        } else {
            showPageOfPendingPlots(bukkitPlayerCommand.getPlayer(), pendingPlots, bukkitPlayerCommand.getArgs()[0]);
        }

        return true;
    }

    private void showFirstPageOfPendingPlots(Player player, List<Plot> pendingPlots) {
        showPageOfPendingPlots(player, pendingPlots, "1");
    }

    private void showPageOfPendingPlots(Player player, List<Plot> pendingPlots, String page) {
        if (StringUtils.isNumeric(page)) {
            Integer pageNumber = Integer.valueOf(page);
            Integer selectedNumber = pageNumber - 1;

            int showing = 0;
            boolean first = true;

            for (int i = selectedNumber * 5; i < pendingPlots.size(); i++) {
                if (showing == 5) {
                    break;
                }

                Plot selectedPlot = pendingPlots.get(i);

                List<Rating> selectedPlotRatings = PRDatabase.getInstance().getRatings(selectedPlot);
                if (first) {
                    ResponseUtil.firstPendingCommand(player, selectedPlot, selectedPlotRatings);
                    first = false;
                } else {
                    ResponseUtil.pendingCommand(player, selectedPlot, selectedPlotRatings);
                }
                showing++;
            }

            double maxPageDouble = pendingPlots.size() / 5.0;
            int maxPageNumber = (int) Math.ceil(maxPageDouble);

            ResponseUtil.pageFooter(player, pageNumber, maxPageNumber, showing);
        }
    }

    @Override
    protected void onNoPermission(BukkitCommand bukkitCommand) {
        ResponseUtil.noPermission(bukkitCommand.getSender());
    }
}

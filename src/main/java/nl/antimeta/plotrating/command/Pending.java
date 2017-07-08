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
        String[] args = bukkitPlayerCommand.getArgs();

        if (args.length == 0) {
            showFirstPageOfPendingPlots(bukkitPlayerCommand.getPlayer(), pendingPlots);
        } else {
            if (StringUtils.isNumeric(args[0])) {
                Integer page = Integer.valueOf(args[0]);
                if (page <= 0) {
                    showFirstPageOfPendingPlots(bukkitPlayerCommand.getPlayer(), pendingPlots);
                } else {
                    showPageOfPendingPlots(bukkitPlayerCommand.getPlayer(), pendingPlots, page);
                }
            }
        }

        return true;
    }

    private void showFirstPageOfPendingPlots(Player player, List<Plot> pendingPlots) {
        showPageOfPendingPlots(player, pendingPlots, 1);
    }

    private void showPageOfPendingPlots(Player player, List<Plot> pendingPlots, Integer page) {
        Integer selectedNumber = page - 1;

        int showing = 0;
        boolean first = true;

        for (int i = selectedNumber * 4; i < pendingPlots.size(); i++) {
            if (showing == 4) {
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

        double maxPageDouble = pendingPlots.size() / 4.0;
        int maxPageNumber = (int) Math.ceil(maxPageDouble);

        ResponseUtil.pageFooter(player, page, maxPageNumber, showing);
    }

    @Override
    protected void onNoPermission(BukkitCommand bukkitCommand) {
        ResponseUtil.noPermission(bukkitCommand.getSender());
    }
}

package nl.antimeta.plotrating.command;

import nl.antimeta.bukkit.framework.command.PlayerCommand;
import nl.antimeta.bukkit.framework.command.annotation.Command;
import nl.antimeta.bukkit.framework.command.model.BukkitCommand;
import nl.antimeta.bukkit.framework.command.model.BukkitPlayerCommand;
import nl.antimeta.plotrating.PlotRatingDatabase;
import nl.antimeta.plotrating.entity.Plot;
import nl.antimeta.plotrating.util.ResponseUtil;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;

import java.util.List;

@Command(main = "pending",
    permission = "pr.pending")
public class Pending extends PlayerCommand {

    @Override
    protected boolean onPlayerCommand(BukkitPlayerCommand bukkitPlayerCommand) {
        List<Plot> pendingPlots = PlotRatingDatabase.getInstance().findAllPendingPlots();

        if (bukkitPlayerCommand.getArgs().length == 0) {
            showFirstPageOfPendingPlots(bukkitPlayerCommand.getPlayer(), pendingPlots);
        } else {
            showPageOfPendingPlots(bukkitPlayerCommand.getPlayer(), pendingPlots, bukkitPlayerCommand.getArgs()[0]);
        }

        return true;
    }

    private void showFirstPageOfPendingPlots(Player player, List<Plot> pendingPlots) {
        showPageOfPendingPlots(player, pendingPlots, "0");
    }

    private void showPageOfPendingPlots(Player player, List<Plot> pendingPlots, String page) {
        if (StringUtils.isNumeric(page)) {
            Integer pageNumber = Integer.valueOf(page);

            int showing = 0;

            for (int i = pageNumber * 10; i < pendingPlots.size(); i++) {
                Plot selectedPlot = pendingPlots.get(i);
                ResponseUtil.pendingCommand(player, selectedPlot);
                showing++;
            }

            int maxPageNumber = (int) Math.ceil(pageNumber / pendingPlots.size());

            ResponseUtil.pageFooter(player, pageNumber, maxPageNumber, showing);
        }

    }

    @Override
    protected void onNoPermission(BukkitCommand bukkitCommand) {
        ResponseUtil.noPermission(bukkitCommand.getSender());
    }
}

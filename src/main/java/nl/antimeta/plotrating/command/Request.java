package nl.antimeta.plotrating.command;

import nl.antimeta.bukkit.framework.command.annotation.Command;
import nl.antimeta.bukkit.framework.command.model.BukkitCommand;
import nl.antimeta.bukkit.framework.command.model.BukkitPlayerCommand;
import nl.antimeta.plotrating.PRDatabase;
import nl.antimeta.plotrating.entity.Plot;
import nl.antimeta.plotrating.model.RateStatus;
import nl.antimeta.plotrating.util.ResponseUtil;
import org.bukkit.command.CommandSender;

@Command(main = "request",
        permission = "pr.request",
        aliases = {"req"})
public class Request extends PlotCommand {

    @Override
    protected boolean onPlotCommand(BukkitPlayerCommand bukkitPlayerCommand) {
        CommandSender sender = bukkitPlayerCommand.getSender();

        if (owner) {
            Plot databasePlot = PRDatabase.getInstance().getPlotFromSquared(basePlot.getId().x, basePlot.getId().y);
            if (databasePlot == null) {
                Plot newPlot = new Plot(sender);
                newPlot.setRateStatus(RateStatus.PENDING.getStatus());
                newPlot.setPlotXId(basePlot.getId().x);
                newPlot.setPlotYId(basePlot.getId().y);
                PRDatabase.getInstance().savePlot(newPlot);
                return ResponseUtil.requestSend(sender);
            } else {
                RateStatus status = RateStatus.findStatus(databasePlot.getRateStatus());
                if (status != null) {
                    switch (status) {
                        case ACCEPTED:
                            return ResponseUtil.plotAlreadyAccepted(sender);
                        case PENDING:
                            return ResponseUtil.plotAlreadyPending(sender);
                        default:
                            databasePlot.setInternRateStatus(RateStatus.PENDING);
                            databasePlot.setRateStatus(RateStatus.PENDING.getStatus());
                            PRDatabase.getInstance().savePlot(databasePlot);
                            return ResponseUtil.requestSend(sender);
                    }
                }
                return false;
            }
        } else {
            return ResponseUtil.notYourPlot(sender);
        }
    }

    @Override
    protected void onNoPermission(BukkitCommand bukkitCommand) {
        ResponseUtil.noPermission(bukkitCommand.getSender());
    }
}

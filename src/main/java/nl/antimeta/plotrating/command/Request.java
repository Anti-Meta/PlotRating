package nl.antimeta.plotrating.command;

import nl.antimeta.plotrating.PlotRatingDatabase;
import nl.antimeta.plotrating.entity.Plot;
import nl.antimeta.plotrating.model.RateStatus;
import nl.antimeta.plotrating.util.ResponseUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class Request extends PlotCommand {

    public Request() {
        super("request", "req");
    }

    @Override
    boolean executePlayerPlotCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (owner) {
            Plot databasePlot = PlotRatingDatabase.getInstance().getPlotFromSquared(basePlot.getId().x, basePlot.getId().y);
            if (databasePlot == null) {
                Plot newPlot = new Plot(sender);
                newPlot.setRateStatus(RateStatus.PENDING.getStatus());
                newPlot.setPlotXId(basePlot.getId().x);
                newPlot.setPlotYId(basePlot.getId().y);
                PlotRatingDatabase.getInstance().savePlot(newPlot);
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
                            PlotRatingDatabase.getInstance().savePlot(databasePlot);
                            return ResponseUtil.requestSend(sender);
                    }
                }
                return false;
            }
        } else {
            return ResponseUtil.notYourPlot(sender);
        }
    }
}

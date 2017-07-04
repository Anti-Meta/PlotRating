package nl.antimeta.plotrating.util;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import nl.antimeta.plotrating.Main;
import nl.antimeta.plotrating.entity.Plot;
import nl.antimeta.plotrating.model.RateStatus;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ResponseUtil {

    public static void helpCommand(CommandSender sender) {
        sender.sendMessage(ChatColor.DARK_AQUA + "----- " + ChatColor.AQUA + "Plot Ratings Commands" + ChatColor.DARK_AQUA + " -----");
        sender.sendMessage(baseHelpText("request"));
        sender.sendMessage(" - Send a rate request.");

        sender.sendMessage(baseHelpText("rate <number> <desc>"));
        sender.sendMessage(" - Rate a pending request. Description is optional and may contain whitespaces.");

        sender.sendMessage(baseHelpText("status"));
        sender.sendMessage(" - View the status of the current plot.");

        sender.sendMessage(baseHelpText("pending <page>"));
        sender.sendMessage(" - Returns all the pending plots.");

        sender.sendMessage(baseHelpText("accept"));
        sender.sendMessage(" - Accept a pending plot.");

        sender.sendMessage(baseHelpText("reject"));
        sender.sendMessage(" - Reject a pending plot.");
    }

    private static String baseHelpText(String name) {
        return ChatColor.DARK_AQUA + "/pr " + ChatColor.AQUA + name;
    }

    public static void pendingCommand(Player sender, Plot plot) {
        String playerName = plot.getPlayer().getName();
        String baseMessage = playerName
                + " plot X: " + String.valueOf(plot.getPlotXId())
                + " Y: " + String.valueOf(plot.getPlotYId() + " | ");

        TextComponent message = new TextComponent(baseMessage);

        com.intellectualcrafters.plot.object.Plot squaredPlot = PlotUtil.getPlot(plot.getPlayer(), plot.getPlotXId(), plot.getPlotYId());
        if (squaredPlot != null) {
            com.intellectualcrafters.plot.object.Location side = squaredPlot.getSide();
            String worldX = String.valueOf(side.getX());
            String worldY = String.valueOf(side.getY());
            String worldZ = String.valueOf(side.getZ());
            String command = "/tp " + worldX + " " + worldY + " " + worldZ;

            TextComponent tpLink = new TextComponent(" [TP]");
            tpLink.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command));
            message.addExtra(tpLink);
        }

        sender.spigot().sendMessage(message);
    }

    public static void pageFooter(Player sender, int page, int maxPage, int showing) {
        send(sender, "Currently showing " + showing + "of page " + page + "/" + maxPage);
    }

    public static boolean notAPlot(CommandSender sender) {
        return sendError(sender, "This is not a plot!");
    }

    public static boolean requestSend(CommandSender sender) {
        return sendGood(sender, "Request send!");
    }

    public static boolean plotStatus(CommandSender sender, RateStatus rateStatus) {
        StringBuilder message = new StringBuilder("The status of this plot is ");

        switch (rateStatus) {
            case ACCEPTED:
                message.append("Accepted!");
                break;
            case PENDING:
                message.append("Pending!");
                break;
            case REJECTED:
                message.append("Rejected!");
                break;
            case NONE:
                message.append("Unknown!");
                break;
            default:
                message.append("Unknown!");
                break;
        }

        return send(sender, message.toString());
    }

    public static boolean noPermission(CommandSender sender) {
        return sendError(sender, "You do not have permission to use this command");
    }

    public static boolean plotAlreadyPending(CommandSender sender) {
        return sendError(sender, "This plot is already pending.");
    }

    public static boolean plotAlreadyAccepted(CommandSender sender) {
        return sendError(sender, "This plot is already accepted.");
    }

    public static boolean plotAlreadyRejected(CommandSender sender) {
        return sendError(sender, "This plot is already rejected.");
    }

    public static boolean notYourPlot(CommandSender sender) {
        return sendError(sender, "This plot is not yours.");
    }

    public static boolean plotRateNotRequested(CommandSender sender) {
        return sendError(sender, "No pending rate request found on this plot.");
    }

    public static boolean plotNeedsMoreRatings(CommandSender sender, int currentRates, int minRates, RateStatus status) {
        sendError(sender, "This plot is only rated " + currentRates + " times!");
        return sendError(sender, "You can change the rate status to " + status.getStatus() + " only when " + minRates + " players have voted on this plot.");
    }

    public static boolean plotRateStatusChanged(CommandSender sender, RateStatus status) {
        return sendGood(sender, "Plot status changed to " + status.getStatus() + "!");
    }

    public static boolean cannotRateOwnPlot(CommandSender sender) {
        return sendError(sender, "You cannot rate on your own plot.");
    }

    public static boolean cannotChangeStatusOfOwnPlot(CommandSender sender) {
        return sendError(sender, "You cannot change the status of your own plot");
    }

    public static boolean maxRatingsOnPlot(CommandSender sender) {
        return sendError(sender, "This plot already reached max ratings. You can only add a new rating when someone removes his rating.");
    }

    public static boolean cannotRateThePlotMultipleTimes(CommandSender sender) {
        return sendError(sender, "Already Rated. You cannot rate multiple times on a plot.");
    }

    public static boolean plotNeedMoreRatings(CommandSender sender, int minRatings, int currentRatings) {
        return sendError(sender, "This plot need at least " + minRatings + " ratings. Currently this plot has " + currentRatings + " ratings!");
    }

    public static boolean plotReachedEnoughRatings(CommandSender sender) {
        return sendGood(sender, "Rating send! This plot reached enough rates you can now close the rating by typing '/pr accept' or '/pr reject'.");
    }

    public static boolean plotRatingSend(CommandSender sender) {
        return sendGood(sender, "Rating send!");
    }

    public static boolean send(CommandSender sender, String message) {
        sender.sendMessage(message);
        return true;
    }

    public static boolean send(CommandSender sender, String message, ChatColor color) {
        sender.sendMessage(String.format(message, color));
        return true;
    }

    private static boolean sendGood(CommandSender sender, String message) {
        if (sender != null) {
            sender.sendMessage(String.format("%s" + message, ChatColor.GREEN));
        } else {
            Main.getStaticLogger().warning("Sender is null cannot send message!!");
        }
        return true;
    }

    private static boolean sendError(CommandSender sender, String message) {
        if (sender != null) {
            sender.sendMessage(String.format("%s" + message, ChatColor.RED));
        } else {
            Main.getStaticLogger().warning("Sender is null cannot send message!!");
        }
        return true;
    }
}

package nl.antimeta.plotrating.util;

import nl.antimeta.plotrating.Main;
import nl.antimeta.plotrating.model.RateStatus;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class ResponseUtil {

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

    public static boolean cannotRateOwnPlot(CommandSender sender) {
        return sendError(sender, "You cannot rate on your own plot.");
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
        return sendGood(sender, "Rating send! This plot reached enough rates you can now close the rating by typing '/plotrate finish' or '/pr-finish'.");
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

package nl.antimeta.plotrating.model;

import org.bukkit.ChatColor;

public enum RateStatus {

    NONE("None", ChatColor.WHITE),
    PENDING("Pending", ChatColor.YELLOW),
    REJECTED("Rejected", ChatColor.RED),
    ACCEPTED("Accepted", ChatColor.GREEN);

    private final String status;
    private final ChatColor color;

    RateStatus(String status, ChatColor color) {
        this.status = status;
        this.color = color;
    }

    public static RateStatus findStatus(String status) {
        if (status.equalsIgnoreCase(NONE.getStatus())) {
            return NONE;
        } else if (status.equalsIgnoreCase(PENDING.getStatus())) {
            return PENDING;
        } else if (status.equalsIgnoreCase(REJECTED.getStatus())) {
            return REJECTED;
        } else if (status.equalsIgnoreCase(ACCEPTED.getStatus())) {
            return ACCEPTED;
        } else {
            return null;
        }
    }

    public String getStatus() {
        return status;
    }

    public ChatColor getColor() {
        return color;
    }
}

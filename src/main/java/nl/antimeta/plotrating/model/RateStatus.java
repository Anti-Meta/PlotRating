package nl.antimeta.plotrating.model;

public enum RateStatus {

    NONE("None"),
    PENDING("Pending"),
    REJECTED("Rejected"),
    ACCEPTED("Accepted");

    private final String status;

    RateStatus(String status) {
        this.status = status;
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
}

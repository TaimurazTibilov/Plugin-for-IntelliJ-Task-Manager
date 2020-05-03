package org.taimuraztibilov.taskmanager;

public class States {
    public static final int OPEN = 1;
    public static final int IN_PROGRESS = 5;
    public static final int FROZEN = 10;
    public static final int CLOSED = 13;
    public static final int ARCHIVED = 25;
    public static final int UNKNOWN = 60;

    public String toString(int state) {
        switch (state) {
            case OPEN:
                return "Open";
            case IN_PROGRESS:
                return "In progress";
            case FROZEN:
                return "Frozen";
            case CLOSED:
                return "Closed";
            case ARCHIVED:
                return "Archived";
            default:
                return "Unknown";
        }
    }
}

package org.taimuraztibilov.taskmanager.base;

import java.util.Vector;

public class States {
    public static final int OPEN = 1;
    public static final int IN_PROGRESS = 2;
    public static final int FROZEN = 3;
    public static final int CLOSED = 4;
    public static final int ARCHIVED = 5;
    public static final int UNKNOWN = 6;

    public static String toString(int state) {
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

    public static int toInt(String state) {
        switch (state) {
            case "Open":
                return OPEN;
            case "In progress":
                return IN_PROGRESS;
            case "Frozen":
                return FROZEN;
            case "Closed":
                return CLOSED;
            case "Archived":
                return ARCHIVED;
            default:
                return UNKNOWN;
        }
    }

    public static Vector<String> getArray() {
        Vector<String> result = new Vector<>();
        for (int i = 1; i < 6; i++)
            result.add(toString(i));
        return result;
    }
}

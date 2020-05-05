package org.taimuraztibilov.taskmanager;

import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TimeManager {
    private static TimeManager instance;
    private KeyPoint current;
    private LocalDateTime start;

    public static synchronized TimeManager getInstance() {
        if (instance == null)
            instance = new TimeManager();
        return instance;
    }

    public synchronized void trackKeyPoint(KeyPoint keyPoint) throws SQLException {
        if (current != null)
            stopTracking();
        current = keyPoint;
        DataBaseManager.getInstance().editData("task", "state", Integer.toString(States.IN_PROGRESS),
                current.getTaskId());
        start = LocalDateTime.now();
    }

    public synchronized KeyPoint stopTracking() throws SQLException {
        current.setTimeSpent(current.getTimeSpent().plus(Duration.between(start, LocalDateTime.now())));
        start = LocalDateTime.now();
        DataBaseManager.getInstance().editData("task", "state", Integer.toString(States.OPEN),
                current.getTaskId());
        return current;
    }
}

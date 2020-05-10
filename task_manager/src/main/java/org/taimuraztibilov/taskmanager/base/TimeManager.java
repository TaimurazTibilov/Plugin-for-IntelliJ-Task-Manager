package org.taimuraztibilov.taskmanager.base;

import com.intellij.openapi.components.Service;

import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class TimeManager {
    private static TimeManager instance;
    private int taskId;
    private LocalDateTime start;

    private TimeManager() {
        taskId = -1;
    }

    public static synchronized TimeManager getInstance() {
        if (instance == null)
            instance = new TimeManager();
        return instance;
    }

    public synchronized boolean trackKeyPoint(int id) throws SQLException {
        if (taskId != -1)
            return false;
        taskId = id;
        DataBaseManager.getInstance().editData("task", "state", Integer.toString(States.IN_PROGRESS), id);
        start = LocalDateTime.now();
        return true;
    }

    public synchronized LocalTime stopTracking() throws SQLException {
        DataBaseManager.getInstance().editData("task", "state", Integer.toString(States.OPEN), taskId);
        if (taskId == -1)
            return LocalTime.MIN;
        LocalTime result = LocalTime.MIN.plus(Duration.between(start, LocalDateTime.now()));
        start = LocalDateTime.now();
        taskId = -1;
        return result;
    }

    public int getTaskId() {
        return taskId;
    }
}

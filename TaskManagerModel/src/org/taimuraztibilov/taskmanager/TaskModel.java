package org.taimuraztibilov.taskmanager;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class TaskModel {
    private int id;
    private int milestoneId;
    private String title;
    private String description;
    private LocalDateTime createdOn;
    private LocalDateTime deadline;
    private LocalTime timeEstimated;
    private LocalTime timeSpent;
    private int state;

    public TaskModel(int id, int milestoneId, String title, String description, LocalDateTime createdOn,
                     LocalDateTime deadline, LocalTime timeEstimated, LocalTime timeSpent, int state) {
        this.id = id;
        this.milestoneId = milestoneId;
        this.title = title;
        this.description = description;
        this.createdOn = createdOn;
        this.deadline = deadline;
        this.timeEstimated = timeEstimated;
        this.timeSpent = timeSpent;
        this.state = state;
    }
}

package org.taimuraztibilov.taskmanager;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class TaskModel {
    private final int id;
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

    public int getId() {
        return id;
    }

    public int getMilestoneId() {
        return milestoneId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public LocalTime getTimeEstimated() {
        return timeEstimated;
    }

    public LocalTime getTimeSpent() {
        return timeSpent;
    }

    public int getState() {
        return state;
    }

    public void setMilestoneId(int milestoneId) {
        this.milestoneId = milestoneId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public void setTimeEstimated(LocalTime timeEstimated) {
        this.timeEstimated = timeEstimated;
    }

    public void setTimeSpent(LocalTime timeSpent) {
        this.timeSpent = timeSpent;
    }

    public void setState(int state) {
        this.state = state;
    }
}

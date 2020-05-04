package org.taimuraztibilov.taskmanager;

import java.time.LocalTime;

public class KeyPoint {
    private final int id;
    private final int taskId;
    private String title;
    private String solution;
    private LocalTime timeEstimated;
    private LocalTime timeSpent;
    private int state;
    private DataEditor listenerOnEdit;

    public KeyPoint(int id, int taskId, String title, String solution,
                    LocalTime timeEstimated, LocalTime timeSpent, int state) {
        this.id = id;
        this.taskId = taskId;
        this.title = title;
        this.solution = solution;
        this.timeEstimated = timeEstimated;
        this.timeSpent = timeSpent;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public int getTaskId() {
        return taskId;
    }

    public String getTitle() {
        return title;
    }

    public String getSolution() {
        return solution;
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

    public KeyPoint setListenerOnEdit(DataEditor listenerOnEdit) {
        this.listenerOnEdit = listenerOnEdit;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSolution(String solution) {
        this.solution = solution;
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

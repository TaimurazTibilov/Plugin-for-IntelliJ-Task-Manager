package org.taimuraztibilov.taskmanager;

import java.sql.SQLException;
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

    public void setTitle(String title) throws SQLException {
        this.title = title;
        listenerOnEdit.editKeyPoint(this);
    }

    public void setSolution(String solution) throws SQLException {
        this.solution = solution;
        listenerOnEdit.editKeyPoint(this);
    }

    public void setTimeEstimated(LocalTime timeEstimated) throws SQLException {
        this.timeEstimated = timeEstimated;
        listenerOnEdit.editKeyPoint(this);
    }

    public void setTimeSpent(LocalTime timeSpent) throws SQLException {
        this.timeSpent = timeSpent;
        listenerOnEdit.editKeyPoint(this);
    }

    public void setState(int state) throws SQLException {
        this.state = state;
        listenerOnEdit.editKeyPoint(this);
    }
}

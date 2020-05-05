package org.taimuraztibilov.taskmanager;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class KeyPoint {
    private final int id;
    private final int taskId;
    private String solution;
    private LocalDate date;
    private LocalTime timeSpent;
    private DataEditor listenerOnEdit;

    public KeyPoint(int id, int taskId, String solution, LocalDate date, LocalTime timeSpent) {
        this.id = id;
        this.taskId = taskId;
        this.solution = solution;
        this.date = date;
        this.timeSpent = timeSpent;
    }

    public int getId() {
        return id;
    }

    public int getTaskId() {
        return taskId;
    }

    public String getSolution() {
        return solution;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTimeSpent() {
        return timeSpent;
    }

    public KeyPoint setListenerOnEdit(DataEditor listenerOnEdit) {
        this.listenerOnEdit = listenerOnEdit;
        return this;
    }

    public void setSolution(String solution) throws SQLException {
        this.solution = solution;
        listenerOnEdit.editKeyPoint(this);
    }

    public void setDate(LocalDate date) throws SQLException {
        this.date = date;
        listenerOnEdit.editKeyPoint(this);
    }

    public void setTimeSpent(LocalTime timeSpent) throws SQLException {
        this.timeSpent = timeSpent;
        listenerOnEdit.editKeyPoint(this);
    }
}

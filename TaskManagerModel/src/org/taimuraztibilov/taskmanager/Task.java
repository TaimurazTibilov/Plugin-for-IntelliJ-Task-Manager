package org.taimuraztibilov.taskmanager;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class Task {
    private final int id;
    private int milestoneId;
    private String title;
    private String description;
    private LocalDateTime createdOn;
    private LocalDateTime deadline;
    private LocalTime timeEstimated;
    private LocalTime timeSpent;
    private int state;
    private ArrayList<Label> labels;
    private ArrayList<KeyPoint> keyPoints;
    private DataEditor listenerOnEdit;

    public Task(int id, int milestoneId, String title, String description, LocalDateTime createdOn,
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
        this.labels = new ArrayList<>();
        this.keyPoints = new ArrayList<>();
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

    public ArrayList<Label> getLabels() {
        return labels;
    }

    public ArrayList<KeyPoint> getKeyPoints() {
        return keyPoints;
    }

    public Task setListenerOnEdit(DataEditor listenerOnEdit) {
        this.listenerOnEdit = listenerOnEdit;
        return this;
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

    public void setLabels(ArrayList<Label> labels) {
        this.labels = labels;
    }

    public void setKeyPoints(ArrayList<KeyPoint> keyPoints) {
        this.keyPoints = keyPoints;
    }

    public void addLabel(Label label) {
        if (labels.contains(label))
            return;
        labels.add(label);
    }

    public void removeLabel(Label label) {
        labels.remove(label);
    }

    public void addKeyPoint(KeyPoint keyPoint) {
        keyPoints.add(keyPoint);
    }

    public void removeKeyPoint(KeyPoint keyPoint) {
        keyPoints.remove(keyPoint);
    }
}

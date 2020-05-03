package org.taimuraztibilov.taskmanager;

import java.time.LocalDateTime;

public class MilestoneModel {
    private final int id;
    private final int projectId;
    private String title;
    private String description;
    private LocalDateTime deadline;
    private int state;

    public MilestoneModel(int id, int projectId, String title, String description, LocalDateTime deadline, int state) {
        this.id = id;
        this.projectId = projectId;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public int getProjectId() {
        return projectId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public int getState() {
        return state;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public void setState(int state) {
        this.state = state;
    }
}

package org.taimuraztibilov.taskmanager;

public class LabelModel {
    private final int id;
    private final int projectId;
    private String color;
    private String title;
    private String description;

    public LabelModel(int id, int projectId, String color, String title, String description) {
        this.id = id;
        this.projectId = projectId;
        this.color = color;
        this.title = title;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public int getProjectId() {
        return projectId;
    }

    public String getColor() {
        return color;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

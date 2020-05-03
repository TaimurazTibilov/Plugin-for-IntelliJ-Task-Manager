package org.taimuraztibilov.taskmanager;

public class LabelModel {
    private int id;
    private int projectId;
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
}

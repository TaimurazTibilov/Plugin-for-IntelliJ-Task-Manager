package org.taimuraztibilov.taskmanager;

public class ProjectModel {
    private int id;
    private String title;
    private String description;
    private int state;

    public ProjectModel(int id, String title, String description, int state) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.state = state;
    }
}

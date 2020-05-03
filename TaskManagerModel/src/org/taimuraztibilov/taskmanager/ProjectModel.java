package org.taimuraztibilov.taskmanager;

public class ProjectModel {
    private final int id;
    private String title;
    private String description;
    private int state;

    public ProjectModel(int id, String title, String description, int state) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
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

    public void setState(int state) {
        this.state = state;
    }
}

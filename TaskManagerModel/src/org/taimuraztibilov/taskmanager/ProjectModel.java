package org.taimuraztibilov.taskmanager;

import java.sql.SQLException;

public class ProjectModel {
    private final int id;
    private String title;
    private String description;
    private int state;
    private DataEditor listenerOnEdit;

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

    public ProjectModel setListenerOnEdit(DataEditor listenerOnEdit) {
        this.listenerOnEdit = listenerOnEdit;
        return this;
    }

    public void setTitle(String title) throws SQLException {
        this.title = title;
        listenerOnEdit.editProject(this);
    }

    public void setDescription(String description) throws SQLException {
        this.description = description;
        listenerOnEdit.editProject(this);
    }

    public void setState(int state) throws SQLException {
        this.state = state;
        listenerOnEdit.editProject(this);
    }
}

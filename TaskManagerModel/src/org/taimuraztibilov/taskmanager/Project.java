package org.taimuraztibilov.taskmanager;

import java.sql.SQLException;
import java.util.ArrayList;

public class Project {
    private final int id;
    private String title;
    private String description;
    private int state;
    private ArrayList<Milestone> milestones;
    private DataEditor listenerOnEdit;

    public Project(int id, String title, String description, int state) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.state = state;
        this.milestones = new ArrayList<>();
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

    public ArrayList<Milestone> getMilestones() {
        return milestones;
    }

    public Project setListenerOnEdit(DataEditor listenerOnEdit) {
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

    public void setMilestones(ArrayList<Milestone> milestones) {
        this.milestones = milestones;
    }
}

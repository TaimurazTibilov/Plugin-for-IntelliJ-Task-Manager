package org.taimuraztibilov.taskmanager;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Milestone {
    private final int id;
    private final int projectId;
    private String title;
    private String description;
    private LocalDateTime deadline;
    private int state;
    private ArrayList<Task> tasks;
    private DataEditor listenerOnEdit;

    public Milestone(int id, int projectId, String title, String description, LocalDateTime deadline, int state) {
        this.id = id;
        this.projectId = projectId;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.state = state;
        this.tasks = new ArrayList<>();
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

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public Milestone setListenerOnEdit(DataEditor listenerOnEdit) {
        this.listenerOnEdit = listenerOnEdit;
        return this;
    }

    public void setTitle(String title) throws SQLException {
        this.title = title;
        listenerOnEdit.editMilestone(this);
    }

    public void setDescription(String description) throws SQLException {
        this.description = description;
        listenerOnEdit.editMilestone(this);
    }

    public void setDeadline(LocalDateTime deadline) throws SQLException {
        this.deadline = deadline;
        listenerOnEdit.editMilestone(this);
    }

    public void setState(int state) throws SQLException {
        this.state = state;
        listenerOnEdit.editMilestone(this);
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }
}

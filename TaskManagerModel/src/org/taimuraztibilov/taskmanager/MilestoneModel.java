package org.taimuraztibilov.taskmanager;

import java.time.LocalDateTime;

public class MilestoneModel {
    private int id;
    private int projectId;
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
}

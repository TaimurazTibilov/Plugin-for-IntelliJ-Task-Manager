package org.taimuraztibilov.taskmanager;

import java.time.LocalTime;

public class KeyPointModel {
    private int id;
    private int taskId;
    private String title;
    private String solution;
    private LocalTime timeEstimated;
    private LocalTime timeSpent;
    private int state;

    public KeyPointModel(int id, int taskId, String title, String solution,
                         LocalTime timeEstimated, LocalTime timeSpent, int state) {
        this.id = id;
        this.taskId = taskId;
        this.title = title;
        this.solution = solution;
        this.timeEstimated = timeEstimated;
        this.timeSpent = timeSpent;
        this.state = state;
    }
}

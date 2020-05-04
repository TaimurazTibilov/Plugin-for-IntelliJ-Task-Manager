package org.taimuraztibilov.taskmanager;

import java.sql.SQLException;

public interface DataEditor {
    void editProject(Project edited) throws SQLException;
    void editMilestone(Milestone edited) throws SQLException;
    void editTask(Task edited) throws SQLException;
    void editLabel(Label edited) throws SQLException;
    void editKeyPoint(KeyPoint edited) throws SQLException;
    void addLabelToTask(int labelId, int taskId) throws SQLException;
    void removeLabelFromTask(int labelId, int taskId) throws SQLException;
}

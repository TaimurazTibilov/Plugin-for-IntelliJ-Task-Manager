package org.taimuraztibilov.taskmanager;

import java.sql.SQLException;

public interface DataEditor {
    void editProject(Project edited) throws SQLException;
    void editMilestone(Milestone edited) throws SQLException;
}

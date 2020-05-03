package org.taimuraztibilov.taskmanager;

import java.sql.SQLException;

public interface DataEditor {
    void editProject(ProjectModel edited) throws SQLException;
    void editMilestone(MilestoneModel edited) throws SQLException;
}

package org.taimuraztibilov.taskmanager;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) throws SQLException {
        DataBaseManager manager = DataBaseManager.getInstance();
        manager.addProject("title","", States.OPEN);
        manager.addMilestone(1, "title", "", LocalDateTime.now(), States.OPEN);
        manager.deleteProject(1);
    }
}

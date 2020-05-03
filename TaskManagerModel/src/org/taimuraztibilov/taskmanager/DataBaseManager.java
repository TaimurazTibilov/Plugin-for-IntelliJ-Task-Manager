package org.taimuraztibilov.taskmanager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.sqlite.*;

public class DataBaseManager {
    private static DataBaseManager instance;
    private String connectionPath;
    private Connection connection;

    private DataBaseManager(String connectionPath) throws SQLException {
        this.connectionPath = connectionPath;
        DriverManager.registerDriver(new JDBC());
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.connectionPath);
        connection.createStatement().executeUpdate("pragma foreign_keys = on");
        createProjectTable();
    }

    public static synchronized DataBaseManager getInstance() throws SQLException {
        if (instance == null)
            instance = new DataBaseManager("plugin.sqlite");
        return instance;
    }

    public void changeConnection(String newPath) throws IOException, SQLException {
        Files.copy(Paths.get(connectionPath), Paths.get(newPath));
        connectionPath = newPath;
        connection = DriverManager.getConnection("jdbc:sqlite:" + connectionPath);
    }

    private void createProjectTable() throws SQLException {
        Statement statement = connection.createStatement();
        statement.setQueryTimeout(10);

        statement.executeUpdate("create table if not exists project (" +
                "id integer primary key autoincrement not null," +
                "title text not null," +
                "description text," +
                "state int not null)");
//        statement.executeUpdate("insert into project (name, state) values('my_first_proj', 1)");
    }

    private void createMilestoneTable() throws SQLException {
        Statement statement = connection.createStatement();
        statement.setQueryTimeout(10);

        statement.executeUpdate("create table if not exists milestone (" +
                "id integer primary key autoincrement not null," +
                "project_id integer not null," +
                "title text not null," +
                "description text," +
                "deadline text not null," +
                "state int not null," +
                "foreign key (project_id) references project(id))");
    }

    private void createTaskTable() throws SQLException {
        Statement statement = connection.createStatement();
        statement.setQueryTimeout(10);

        statement.executeUpdate("create table if not exists task (" +
                "id integer primary key autoincrement not null," +
                "milestone_id integer not null," +
                "title text not null," +
                "description text," +
                "created_on text not null," +
                "deadline text not null," +
                "time_estimated text," +
                "time_spent text," +
                "state int not null," +
                "foreign key (milestone_id) references milestone(id))");
    }

    private void createKeyPointTable() throws SQLException {
        Statement statement = connection.createStatement();
        statement.setQueryTimeout(10);

        statement.executeUpdate("create table if not exists keypoint (" +
                "id integer primary key autoincrement not null," +
                "task_id integer not null," +
                "title text not null," +
                "solution text," +
                "time_estimated text," +
                "time_spent text," +
                "state int not null," +
                "foreign key (task_id) references task(id))");
    }

    private void createLabelTable() throws SQLException {
        Statement statement = connection.createStatement();
        statement.setQueryTimeout(20);

        statement.executeUpdate("create table if not exists label (" +
                "id integer primary key autoincrement not null," +
                "title text not null," +
                "color text not null)");
        statement.executeUpdate("create table if not exists label_task (" +
                "label_id integer not null," +
                "task_id integer not null," +
                "foreign key (label_id) references label(id)," +
                "foreign key (task_id) references task(id))");
    }
}

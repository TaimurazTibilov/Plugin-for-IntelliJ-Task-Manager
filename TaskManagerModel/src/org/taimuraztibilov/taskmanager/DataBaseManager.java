package org.taimuraztibilov.taskmanager;

import java.awt.image.AreaAveragingScaleFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import org.sqlite.*; // TODO: 03.05.2020 make all methods synchronized

public class DataBaseManager implements DataEditor {
    private static DataBaseManager instance;
    private String connectionPath;
    private Connection connection;

    private DataBaseManager(String connectionPath) throws SQLException {
        this.connectionPath = connectionPath;
        DriverManager.registerDriver(new JDBC());
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.connectionPath);
        connection.createStatement().executeUpdate("pragma foreign_keys = on");
        createProjectTable();
        createMilestoneTable();
        createTaskTable();
        createKeyPointTable();
        createLabelTable();
    }

    public static synchronized DataBaseManager getInstance() throws SQLException {
        if (instance == null)
            instance = new DataBaseManager("taskmanager.sqlite");
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

    public void addProject(String title, String description, int state) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("insert into " +
                "project (title, description, state) values (?, ?, ?)");
        statement.setObject(1, title);
        statement.setObject(2, description);
        statement.setObject(3, state);
        statement.executeUpdate();
    }

    public void deleteProject(int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("delete from project where id = ?");
        statement.setObject(1, id);
        statement.executeUpdate();
    }

    public ProjectModel getProject(int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select * from project where id = ?");
        statement.setObject(1, id);
        ResultSet result = statement.executeQuery();
        return new ProjectModel(
                result.getInt("id"),
                result.getString("title"),
                result.getString("description"),
                result.getInt("state")).setListenerOnEdit(this);
    }

    public ArrayList<ProjectModel> getProjects() throws SQLException {
        ArrayList<ProjectModel> projects = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("select * from project");
        while (result.next())
            projects.add(new ProjectModel(
                    result.getInt("id"),
                    result.getString("title"),
                    result.getString("description"),
                    result.getInt("state")).setListenerOnEdit(this));

        return projects;
    }

    public void editProject(ProjectModel edited) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("update project set title = ?," +
                "description = ?, state = ? where id = ?");
        statement.setObject(1, edited.getTitle());
        statement.setObject(2, edited.getDescription());
        statement.setObject(3, edited.getState());
        statement.setObject(4, edited.getId());
        statement.executeUpdate();
    }

    public void addMilestone(int projectId, String title, String description, LocalDateTime deadline, int state)
            throws SQLException {
        PreparedStatement statement = connection.prepareStatement("insert into milestone " +
                "(project_id, title, description, deadline, state) values (?, ?, ?, ?, ?)");
        statement.setObject(1, projectId);
        statement.setObject(2, title);
        statement.setObject(3, description);
        statement.setObject(4, deadline.toString());
        statement.setObject(5, state);
        statement.executeUpdate();
    }

    public MilestoneModel getMilestone(int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select * from milestone where id = ?");
        statement.setObject(1, id);
        ResultSet result = statement.executeQuery();
        return new MilestoneModel(
                result.getInt("id"),
                result.getInt("project_id"),
                result.getString("title"),
                result.getString("description"),
                LocalDateTime.parse(result.getString("deadline")),
                result.getInt("state")).setListenerOnEdit(this);
    }

    public ArrayList<MilestoneModel> getMilestones(int projectId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select * from milestone where project_id = ?");
        statement.setObject(1, projectId);
        ResultSet result = statement.executeQuery();
        ArrayList<MilestoneModel> milestones = new ArrayList<>();
        while (result.next()) {
            milestones.add(new MilestoneModel(
                    result.getInt("id"),
                    result.getInt("project_id"),
                    result.getString("title"),
                    result.getString("description"),
                    LocalDateTime.parse(result.getString("deadline")),
                    result.getInt("state")).setListenerOnEdit(this));
        }
        return milestones;
    }

    public void editMilestone(MilestoneModel edited) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("update milestone set " +
                "title = ?, description = ?, deadline = ?, state = ? where id = ?");
        statement.setObject(1, edited.getTitle());
        statement.setObject(2, edited.getDescription());
        statement.setObject(3, edited.getDeadline().toString());
        statement.setObject(4, edited.getState());
        connection.createStatement().executeUpdate("");
    }

    public void addTask(int milestoneId, String title, String description, LocalDateTime deadline,
                        LocalTime timeEstimated, int state) {
        // TODO: 03.05.2020 create row of task in table
        // select max(id) from task - получить идентификатор последней добавленной задачи
    }
}

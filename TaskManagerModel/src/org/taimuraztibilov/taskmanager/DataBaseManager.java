package org.taimuraztibilov.taskmanager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import org.sqlite.*;

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

    public synchronized void changeConnection(String newPath) throws IOException, SQLException {
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

    public synchronized Project addProject(String title, String description, int state) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("insert into " +
                "project (title, description, state) values (?, ?, ?)");
        statement.setObject(1, title);
        statement.setObject(2, description);
        statement.setObject(3, state);
        statement.executeUpdate();
        ResultSet newId = connection.createStatement().executeQuery("select max(id) from project");
        return new Project(newId.getInt(1), title, description, state).setListenerOnEdit(this);
    }

    public synchronized void deleteProject(int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("delete from project where id = ?");
        statement.setObject(1, id);
        statement.executeUpdate();
    }

    public synchronized Project getProject(int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select * from project where id = ?");
        statement.setObject(1, id);
        ResultSet result = statement.executeQuery();
        return new Project(
                result.getInt("id"),
                result.getString("title"),
                result.getString("description"),
                result.getInt("state")).setListenerOnEdit(this);
    }

    public synchronized ArrayList<Project> getProjects() throws SQLException {
        ArrayList<Project> projects = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("select id from project");
        while (result.next())
            projects.add(getProject(result.getInt("id")));

        return projects;
    }

    public synchronized void editProject(Project edited) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("update project set title = ?," +
                "description = ?, state = ? where id = ?");
        statement.setObject(1, edited.getTitle());
        statement.setObject(2, edited.getDescription());
        statement.setObject(3, edited.getState());
        statement.setObject(4, edited.getId());
        statement.executeUpdate();
    }

    public synchronized Milestone addMilestone(int projectId, String title, String description,
                                               LocalDateTime deadline, int state)
            throws SQLException {
        PreparedStatement statement = connection.prepareStatement("insert into milestone " +
                "(project_id, title, description, deadline, state) values (?, ?, ?, ?, ?)");
        statement.setObject(1, projectId);
        statement.setObject(2, title);
        statement.setObject(3, description);
        statement.setObject(4, deadline.toString());
        statement.setObject(5, state);
        statement.executeUpdate();
        ResultSet newId = connection.createStatement().executeQuery("select max(id) from milestone");
        return new Milestone(newId.getInt(1), projectId, title,
                description, deadline, state).setListenerOnEdit(this);
    }

    public synchronized Milestone getMilestone(int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select * from milestone where id = ?");
        statement.setObject(1, id);
        ResultSet result = statement.executeQuery();
        return new Milestone(
                result.getInt("id"),
                result.getInt("project_id"),
                result.getString("title"),
                result.getString("description"),
                LocalDateTime.parse(result.getString("deadline")),
                result.getInt("state")).setListenerOnEdit(this);
    }

    public synchronized ArrayList<Milestone> getMilestones(int projectId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select id from milestone where project_id = ?");
        statement.setObject(1, projectId);
        ResultSet result = statement.executeQuery();
        ArrayList<Milestone> milestones = new ArrayList<>();
        while (result.next()) {
            milestones.add(getMilestone(result.getInt("id")));
        }
        return milestones;
    }

    public synchronized void editMilestone(Milestone edited) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("update milestone set " +
                "title = ?, description = ?, deadline = ?, state = ? where id = ?");
        statement.setObject(1, edited.getTitle());
        statement.setObject(2, edited.getDescription());
        statement.setObject(3, edited.getDeadline().toString());
        statement.setObject(4, edited.getState());
        connection.createStatement().executeUpdate("");
    }

    public synchronized Label addLabel(String color, String title) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("insert into label (title, color) values (?, ?)");
        statement.setObject(1, title);
        statement.setObject(2, color);
        statement.executeUpdate();
        ResultSet newId = connection.createStatement().executeQuery("select max(id) from label");
        return new Label(newId.getInt(1), color, title).setListenerOnEdit(this);
    }

    public synchronized void deleteLabel(int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("delete from label_task where label_id = ?");
        statement.setObject(1, id);
        statement.executeUpdate();
        statement = connection.prepareStatement("delete from label where id = ?");
        statement.setObject(1, id);
        statement.executeUpdate();
    }

    public synchronized Label getLabel(int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select * from label where id = ?");
        statement.setObject(1, id);
        ResultSet result = statement.executeQuery();
        return new Label(
                result.getInt("id"),
                result.getString("color"),
                result.getString("title")).setListenerOnEdit(this);
    }

    public synchronized ArrayList<Label> getLabels() throws SQLException {
        ResultSet result = connection.createStatement().executeQuery("select id from task");
        ArrayList<Label> labels = new ArrayList<>();
        while (result.next()) {
            labels.add(getLabel(result.getInt(1)));
        }
        return labels;
    }

    public synchronized void editLabel(Label label) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("update label " +
                "set title = ?, color = ? where id = ?");
        statement.setObject(1, label.getTitle());
        statement.setObject(2, label.getColor());
        statement.setObject(3, label.getId());
        statement.executeUpdate();
    }

    public synchronized Task addTask(int milestoneId, String title, String description, LocalDateTime deadline,
                                     LocalTime timeEstimated, int state, ArrayList<Label> labels)
            throws SQLException {
        LocalDateTime createdOn = LocalDateTime.now();
        PreparedStatement statement = connection.prepareStatement("insert into task " +
                "(milestone_id, title, description, created_on, deadline, time_estimated, state) " +
                "values (?, ?, ?, ?, ?, ?, ?)");
        statement.setObject(1, milestoneId);
        statement.setObject(2, title);
        statement.setObject(3, description);
        statement.setObject(4, createdOn.toString());
        statement.setObject(5, deadline.toString());
        statement.setObject(6, timeEstimated == null ? "" : timeEstimated.toString());
        statement.setObject(7, state);
        statement.executeUpdate();
        ResultSet newId = connection.createStatement().executeQuery("select max(id) from task");
        Task newTask = new Task(newId.getInt(1), milestoneId, title, description, createdOn,
                deadline, timeEstimated, null, state).setListenerOnEdit(this);
        for (Label label : labels) {
            statement = connection.prepareStatement("insert into label_task (label_id, task_id) values (?, ?)");
            statement.setObject(1, label.getId());
            statement.setObject(2, newTask.getId());
            statement.executeUpdate();
            newTask.addLabel(label);
        }
        return newTask;
    }

    public synchronized Task getTask(int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select * from task where id = ?");
        statement.setObject(1, id);
        ResultSet result = statement.executeQuery();
        LocalTime estimated = result.getString("time_estimated") == null ||
                result.getString("time_estimated").isBlank() ? LocalTime.MIN :
                LocalTime.parse(result.getString("time_estimated"));
        LocalTime spent = result.getString("time_spent") == null ||
                result.getString("time_spent").isBlank() ? LocalTime.MIN :
                LocalTime.parse(result.getString("time_spent"));
        Task task = new Task(id,
                result.getInt("milestone_id"),
                result.getString("title"),
                result.getString("description"),
                LocalDateTime.parse(result.getString("created_on")),
                LocalDateTime.parse(result.getString("deadline")),
                estimated,
                spent,
                result.getInt("state")).setListenerOnEdit(this);
        statement = connection.prepareStatement("select label_id from label_task where task_id = ?");
        result = statement.executeQuery();
        while (result.next()) {
            // TODO: 04.05.2020 Write method that adds labels to the Task
        }
        return task;
    }

    public synchronized ArrayList<Task> getTasks(int milestoneId) throws SQLException {
        ArrayList<Task> tasks = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("select id from task where milestone_id = ?");
        statement.setObject(1, milestoneId);
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            tasks.add(getTask(result.getInt("id")));
        }
        return tasks;
    }
}

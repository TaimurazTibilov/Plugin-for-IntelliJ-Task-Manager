package org.taimuraztibilov.taskmanager.base;

import org.junit.Test;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Objects;

import static org.junit.Assert.*;

public class PluginManagersTest {

    @Test
    public void getInstance() {
        try {
            DataBaseManager.getInstance();
        } catch (Exception e) {
            throw new AssertionError();
        }
    }


    @Test
    public void editData() {
        Project proj = null;
        try {
            proj = DataBaseManager.getInstance().addProject("title", "description", States.OPEN);
            proj.setTitle("new title");
            proj.setState(States.IN_PROGRESS);
        } catch (SQLException throwables) {
            throw new AssertionError();
        }
        assert proj.getTitle().equals("new title");
        assert proj.getState() == States.IN_PROGRESS;
    }

    @Test
    public void addProject() {
        Project proj = null;
        try {
            proj = DataBaseManager.getInstance().addProject("title", "description", States.OPEN);
        } catch (SQLException throwables) {
            throw new AssertionError();
        }
        assert proj.getTitle().equals("title");
        assert proj.getState() == States.OPEN;
    }

    @Test
    public void deleteProject() {
        try {
            DataBaseManager.getInstance().addProject("title", "description", States.OPEN);
            ArrayList<Project> projects = DataBaseManager.getInstance().getProjects();
            DataBaseManager.getInstance().deleteProject(projects.get(0).getId());
            if (projects.size() - 1 != DataBaseManager.getInstance().getProjects().size()) throw new AssertionError();
        } catch (Exception e) {
            throw new AssertionError();
        }
    }

    @Test
    public void getProject() {
        Project proj = null;
        try {
            proj = DataBaseManager.getInstance().addProject("title", "description", States.OPEN);
            assert Objects.equals(proj.getId(), DataBaseManager.getInstance().getProject(proj.getId()).getId());
        } catch (SQLException throwables) {
            throw new AssertionError();
        }
    }

    @Test
    public void addMilestone() {
        Milestone milestone = null;
        try {
            milestone = DataBaseManager.getInstance().addMilestone(
                    DataBaseManager.getInstance().addProject("title", "description", States.OPEN).getId(),
                    "title", "description", LocalDateTime.now(), States.OPEN);
        } catch (SQLException throwables) {
            throw new AssertionError();
        }
        assert milestone.getTitle().equals("title");
        assert milestone.getState() == States.OPEN;
    }

    @Test
    public void getMilestone() {

        try {
            Project p = DataBaseManager.getInstance().addProject("title", "description", States.OPEN);
            Milestone m = DataBaseManager.getInstance().addMilestone(
                    p.getId(), "title", "description", LocalDateTime.now(), States.OPEN);
            assert m.getId() == DataBaseManager.getInstance().getMilestone(m.getId()).getId();
        } catch (SQLException e) {
            throw new AssertionError();
        }
    }

    @Test
    public void addLabel() {
        try {
            DataBaseManager.getInstance().addLabel("0xFFFFFF", "title");

        } catch (SQLException e) {
            throw new AssertionError();
        }
    }

    @Test
    public void deleteLabel() {

        try {
            int id = DataBaseManager.getInstance().addLabel("0xFFFFFF", "title").getId();
            var labels = DataBaseManager.getInstance().getLabels();
            DataBaseManager.getInstance().deleteLabel(id);
            assert labels.size() - 1 == DataBaseManager.getInstance().getLabels().size();
        } catch (SQLException e) {
            throw new AssertionError();
        }
    }

    @Test
    public void getLabel() {

        try {
            var l = DataBaseManager.getInstance().addLabel("0xFFFFFF", "title");
            assert l.getId() == DataBaseManager.getInstance().getLabel(l.getId()).getId();
        } catch (SQLException e) {
            throw new AssertionError();
        }
    }

    @Test
    public void addTask() {

        try {
            DataBaseManager.getInstance().addMilestone(
                    DataBaseManager.getInstance().getProjects().get(0).getId(),
                    "title", "description", LocalDateTime.now(), States.OPEN);
            DataBaseManager.getInstance().addTask(DataBaseManager.getInstance().getMilestones(
                    DataBaseManager.getInstance().getProjects().get(0).getId()
                    ).get(0).getId(), "t", "d", LocalDateTime.now(),
                    LocalTime.MIN, 1, new ArrayList<>());
        } catch (SQLException e) {
            throw new AssertionError();
        }
    }

    @Test
    public void getTask() {

        try {
            DataBaseManager.getInstance().addMilestone(
                    DataBaseManager.getInstance().getProjects().get(0).getId(),
                    "title", "description", LocalDateTime.now(), States.OPEN);
            var t = DataBaseManager.getInstance().addTask(DataBaseManager.getInstance().getMilestones(
                    DataBaseManager.getInstance().getProjects().get(0).getId()
                    ).get(0).getId(), "t", "d", LocalDateTime.now(),
                    LocalTime.MIN, 1, new ArrayList<>());
            assert t.getId() == DataBaseManager.getInstance().getTask(t.getId()).getId();
        } catch (SQLException e) {
            throw new AssertionError();
        }
    }
}
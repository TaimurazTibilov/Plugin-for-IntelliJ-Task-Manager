package org.taimuraztibilov.taskmanager.ui;

import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.components.*;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.util.ui.JBUI;
import com.intellij.util.ui.UIUtil;
import org.taimuraztibilov.taskmanager.base.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class ShowDataFormBuilder {
    private static ArrayList<Project> projects;
    private static ArrayList<Milestone> milestones;
    private static ArrayList<Task> tasks;
    private static JBList<String> list;
    private static int lastSelectedList = -1;

    private static JBList<String> getProjectList() throws SQLException {
        projects = DataBaseManager.getInstance().getProjects();
        DefaultListModel<String> model = new DefaultListModel<>();
        for (int i = 0; i < projects.size(); i++)
            model.add(i, projects.get(i).getTitle());
        JBList<String> projectList = new JBList<>(model);
        projectList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        return projectList;
    }

    private static JBList<String> getMilestoneList(int projectId) throws SQLException {
        milestones = DataBaseManager.getInstance().getMilestones(projectId);
        DefaultListModel<String> model = new DefaultListModel<>();
        for (int i = 0; i < milestones.size(); i++)
            model.add(i, milestones.get(i).getTitle());
        JBList<String> milestoneList = new JBList<>(model);
        milestoneList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        return milestoneList;
    }

    private static JBList<String> getTaskList(int milestoneId) throws SQLException {
        tasks = DataBaseManager.getInstance().getTasks(milestoneId);
        DefaultListModel<String> model = new DefaultListModel<>();
        for (int i = 0; i < tasks.size(); i++)
            model.add(i, tasks.get(i).getTitle());
        JBList<String> tasksList = new JBList<>(model);
        tasksList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        return tasksList;
    }

    public static synchronized void showTasksInformation() {
        JFrame showData = new JFrame("Choose task to track");
        GridConstraints constraints = new GridConstraints();
        JBPanel form = new JBPanel();
        form.setLayout(new GridLayoutManager(3, 3,
                JBUI.insets(20, 8), 8, 20));
        JBLabel label = new JBLabel("Project",
                UIUtil.ComponentStyle.REGULAR, UIUtil.FontColor.NORMAL);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        form.add(label, constraints);
        constraints.setColumn(1);
        label = new JBLabel("Milestone",
                UIUtil.ComponentStyle.REGULAR, UIUtil.FontColor.NORMAL);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        form.add(label, constraints);
        constraints.setColumn(2);
        label = new JBLabel("Task",
                UIUtil.ComponentStyle.REGULAR, UIUtil.FontColor.NORMAL);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        form.add(label, constraints);
        constraints.setColumn(0);
        constraints.setRow(1);

        JBList<String> component = new JBList<>();
        try {
            component = getProjectList();
        } catch (SQLException throwables) {
            // TODO: 09.05.2020 Notification
        }
        component.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selected = ((JBList<?>) e.getSource()).getSelectedIndex();
                PluginManagerService.getInstance().setTrackingProject(selected);
                PluginManagerService.getInstance().setTrackingTask(-1);
                PluginManagerService.getInstance().setTrackingMilestone(-1);
                GridConstraints constraints = new GridConstraints();
                constraints.setRow(1);
                constraints.setColumn(1);
                JBList<String> component = new JBList<>();
                try {
                    component = getMilestoneList(selected);
                } catch (SQLException throwables) {
                    // TODO: 09.05.2020 Notification
                }
                component.addListSelectionListener(new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        int selected = ((JBList<?>) e.getSource()).getSelectedIndex();
                        PluginManagerService.getInstance().setTrackingMilestone(selected);
                        PluginManagerService.getInstance().setTrackingTask(-1);
                        GridConstraints constraints = new GridConstraints();
                        constraints.setRow(1);
                        constraints.setColumn(2);
                        list = new JBList<>();
                        try {
                            list = getTaskList(selected);
                        } catch (SQLException throwables) {
                            // TODO: 09.05.2020 Notification
                        }
                        list.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                if (e.getClickCount() == 2) {
                                    int selected = ((JBList<?>) e.getSource()).locationToIndex(e.getPoint());
                                    showTaskData(tasks.get(selected));
                                }
                            }
                        });
                        form.add(list, constraints);
                    }
                });
                component.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (e.getClickCount() == 2) {
                            int selected = ((JBList<?>) e.getSource()).locationToIndex(e.getPoint());
                            showMilestoneData(milestones.get(selected));
                        }
                    }
                });
                form.add(component, constraints);
            }
        });
        component.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selected = ((JBList<?>) e.getSource()).locationToIndex(e.getPoint());
                    showProjectData(projects.get(selected));
                }
            }
        });
        form.add(component, constraints);
        constraints.setColumn(0);
        constraints.setRow(0);

        JBPanel buttons = new JBPanel(new GridLayoutManager(1, 2,
                JBUI.insets(10, 10), 0, 0));
        JButton createButton = new JButton("Track");
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showData.dispose();
            }
        });
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (PluginManagerService.getInstance().getTrackingTask() == -1)
                        return;
                    int onTrack = tasks.get(list.getSelectedIndex()).getId();
                    PluginManagerService.getInstance().setTrackingTask(onTrack);
                    LocalTime tracked = TimeManager.getInstance().stopTracking();
                    int trackedId = TimeManager.getInstance().getTaskId();
                    TimeManager.getInstance().trackKeyPoint(onTrack);
                    showData.dispose();
                    if (trackedId == -1) {
                        AddDataFormBuilder.addKeyPointByUser(tracked, trackedId);
                    }
                } catch (SQLException throwables) {
                    // TODO: 08.05.2020 Show notification about exception
                }
            }
        });
        buttons.add(createButton, constraints);
        constraints.setColumn(1);
        buttons.add(cancelButton, constraints);
        constraints.setColumn(2);
        constraints.setRow(2);
        form.add(buttons, constraints);
        showData.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        showData.setContentPane(form);
        showData.pack();
        showData.setVisible(true);
    }

    public static synchronized void showProjects() {
        JFrame showData = new JFrame("Choose project to track");
        GridConstraints constraints = new GridConstraints();
        JBPanel form = new JBPanel();
        form.setLayout(new GridLayoutManager(3, 1,
                JBUI.insets(20, 8), 8, 20));
        JBLabel label = new JBLabel("Project",
                UIUtil.ComponentStyle.REGULAR, UIUtil.FontColor.NORMAL);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        form.add(label, constraints);
        constraints.setRow(1);
        JBList<String> component = new JBList<>();
        try {
            component = getProjectList();
        } catch (SQLException throwables) {
            // TODO: 09.05.2020 Notification
        }
        component.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selected = ((JBList<?>) e.getSource()).locationToIndex(e.getPoint());
                    showProjectData(projects.get(selected));
                }
            }
        });
        component.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selected = ((JBList<?>) e.getSource()).getSelectedIndex();
                PluginManagerService.getInstance().setTrackingProject(projects.get(selected).getId());
                PluginManagerService.getInstance().setTrackingTask(-1);
                PluginManagerService.getInstance().setTrackingMilestone(-1);
            }
        });
        form.add(component, constraints);
        constraints.setRow(2);
        JButton track = new JButton("Track");
        track.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showData.dispose();
            }
        });
        form.add(track, constraints);
        showData.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        showData.setContentPane(form);
        showData.pack();
        showData.setVisible(true);
    }

    public static synchronized void showMilestones() {
        JFrame showData = new JFrame("Choose milestone to track");
        GridConstraints constraints = new GridConstraints();
        JBPanel form = new JBPanel();
        form.setLayout(new GridLayoutManager(3, 1,
                JBUI.insets(20, 8), 8, 20));
        JBLabel label = new JBLabel("Milestone",
                UIUtil.ComponentStyle.REGULAR, UIUtil.FontColor.NORMAL);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        form.add(label, constraints);
        constraints.setRow(1);
        JBList<String> component = new JBList<>();
        try {
            component = getMilestoneList(PluginManagerService.getInstance().getTrackingProject());
        } catch (SQLException throwables) {
            // TODO: 09.05.2020 Notification
        }
        component.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selected = ((JBList<?>) e.getSource()).locationToIndex(e.getPoint());
                    showMilestoneData(milestones.get(selected));
                }
            }
        });
        component.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selected = ((JBList<?>) e.getSource()).getSelectedIndex();
                PluginManagerService.getInstance().setTrackingTask(-1);
                PluginManagerService.getInstance().setTrackingMilestone(milestones.get(selected).getId());
            }
        });
        form.add(component, constraints);
        constraints.setRow(2);
        JButton track = new JButton("Track");
        track.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showData.dispose();
            }
        });
        form.add(track, constraints);
        showData.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        showData.setContentPane(form);
        showData.pack();
        showData.setVisible(true);
    }

    public static synchronized void showTasks() {
        JFrame showData = new JFrame("Choose task to track");
        GridConstraints constraints = new GridConstraints();
        JBPanel form = new JBPanel();
        form.setLayout(new GridLayoutManager(3, 1,
                JBUI.insets(20, 8), 8, 20));
        JBLabel label = new JBLabel("Task",
                UIUtil.ComponentStyle.REGULAR, UIUtil.FontColor.NORMAL);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        form.add(label, constraints);
        constraints.setRow(1);
        JBList<String> component = new JBList<>();
        try {
            component = getTaskList(PluginManagerService.getInstance().getTrackingTask());
        } catch (SQLException throwables) {
            // TODO: 09.05.2020 Notification
        }
        component.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selected = ((JBList<?>) e.getSource()).locationToIndex(e.getPoint());
                    showTaskData(tasks.get(selected));
                }
            }
        });
        component.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selected = ((JBList<?>) e.getSource()).getSelectedIndex();
                PluginManagerService.getInstance().setTrackingTask(tasks.get(selected).getId()); // TODO: 10.05.2020
            }
        });
        form.add(component, constraints);
        constraints.setRow(2);
        JButton track = new JButton("Track");
        track.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (PluginManagerService.getInstance().getTrackingTask() == -1)
                        return;
                    int onTrack = tasks.get(list.getSelectedIndex()).getId();
                    PluginManagerService.getInstance().setTrackingTask(onTrack);
                    LocalTime tracked = TimeManager.getInstance().stopTracking();
                    int trackedId = TimeManager.getInstance().getTaskId();
                    TimeManager.getInstance().trackKeyPoint(onTrack);
                    showData.dispose();
                    if (trackedId != -1) {
                        AddDataFormBuilder.addKeyPointByUser(tracked, trackedId);
                    }
                } catch (SQLException throwables) {
                    // TODO: 08.05.2020 Show notification about exception
                }
            }
        });
        form.add(track, constraints);
        showData.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        showData.setContentPane(form);
        showData.pack();
        showData.setVisible(true);
    }

    private static synchronized void showProjectData(Project project) {
        JFrame createProject = new JFrame(project.getTitle());
        GridConstraints constraints = new GridConstraints();
        JBPanel form = new JBPanel();
        form.setLayout(new GridLayoutManager(3, 1,
                JBUI.insets(12, 20), 20, 20));

        JBPanel title = new JBPanel(new BorderLayout());
        JBLabel label = new JBLabel("Title: " + project.getTitle(),
                UIUtil.ComponentStyle.REGULAR, UIUtil.FontColor.NORMAL);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        title.add(label);
        form.add(title, constraints);
        constraints.setRow(1);

        JBPanel description = new JBPanel(new BorderLayout());
        label = new JBLabel("Description: ",
                UIUtil.ComponentStyle.REGULAR, UIUtil.FontColor.NORMAL);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        description.add(label);
        JBTextArea getDescription = new JBTextArea(project.getDescription(), 6, 50);
        getDescription.setLineWrap(true);
        getDescription.setEditable(false);
        getDescription.setWrapStyleWord(true);
        getDescription.setFont(label.getFont());
        description.add(new JBScrollPane(getDescription), BorderLayout.EAST);
        form.add(description, constraints);
        constraints.setRow(2);

        JBPanel getState = new JBPanel(new BorderLayout());
        label = new JBLabel("State: " + States.toString(project.getState()),
                UIUtil.ComponentStyle.REGULAR, UIUtil.FontColor.NORMAL);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        getState.add(label);
        form.add(getState, constraints);
        createProject.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        createProject.setContentPane(form);
        createProject.pack();
        createProject.setVisible(true);
    }

    private static synchronized void showMilestoneData(Milestone milestone) {
        JFrame createProject = new JFrame(milestone.getTitle());
        GridConstraints constraints = new GridConstraints();
        JBPanel form = new JBPanel();
        form.setLayout(new GridLayoutManager(3, 1,
                JBUI.insets(12, 20), 20, 20));

        JBPanel title = new JBPanel(new BorderLayout());
        JBLabel label = new JBLabel("Title: " + milestone.getTitle(),
                UIUtil.ComponentStyle.REGULAR, UIUtil.FontColor.NORMAL);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        title.add(label);
        form.add(title, constraints);
        constraints.setRow(1);

        JBPanel description = new JBPanel(new BorderLayout());
        label = new JBLabel("Description: ",
                UIUtil.ComponentStyle.REGULAR, UIUtil.FontColor.NORMAL);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        description.add(label);
        JBTextArea getDescription = new JBTextArea(milestone.getDescription(), 6, 50);
        getDescription.setLineWrap(true);
        getDescription.setEditable(false);
        getDescription.setWrapStyleWord(true);
        getDescription.setFont(label.getFont());
        description.add(new JBScrollPane(getDescription), BorderLayout.EAST);
        form.add(description, constraints);
        constraints.setRow(2);

        JBPanel deadline = new JBPanel(new BorderLayout());
        label = new JBLabel("Deadline: " + milestone.getDeadline().toString(),
                UIUtil.ComponentStyle.REGULAR, UIUtil.FontColor.NORMAL);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        deadline.add(label);
        form.add(deadline, constraints);
        constraints.setRow(3);

        JBPanel getState = new JBPanel(new BorderLayout());
        label = new JBLabel("State: " + States.toString(milestone.getState()),
                UIUtil.ComponentStyle.REGULAR, UIUtil.FontColor.NORMAL);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        getState.add(label);
        form.add(getState, constraints);
        createProject.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        createProject.setContentPane(form);
        createProject.pack();
        createProject.setVisible(true);
    }

    private static synchronized void showTaskData(Task task) {
        JFrame createProject = new JFrame(task.getTitle());
        GridConstraints constraints = new GridConstraints();
        JBPanel form = new JBPanel();
        form.setLayout(new GridLayoutManager(3, 1,
                JBUI.insets(12, 20), 20, 20));

        JBPanel title = new JBPanel(new BorderLayout());
        JBLabel label = new JBLabel("Title: " + task.getTitle(),
                UIUtil.ComponentStyle.REGULAR, UIUtil.FontColor.NORMAL);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        title.add(label);
        form.add(title, constraints);
        constraints.setRow(1);

        JBPanel description = new JBPanel(new BorderLayout());
        label = new JBLabel("Description: ",
                UIUtil.ComponentStyle.REGULAR, UIUtil.FontColor.NORMAL);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        description.add(label);
        JBTextArea getDescription = new JBTextArea(task.getDescription(), 6, 50);
        getDescription.setLineWrap(true);
        getDescription.setEditable(false);
        getDescription.setWrapStyleWord(true);
        getDescription.setFont(label.getFont());
        description.add(new JBScrollPane(getDescription), BorderLayout.EAST);
        form.add(description, constraints);
        constraints.setRow(2);

        JBPanel deadline = new JBPanel(new BorderLayout());
        label = new JBLabel("Deadline: " + task.getDeadline().toString(),
                UIUtil.ComponentStyle.REGULAR, UIUtil.FontColor.NORMAL);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        deadline.add(label);
        form.add(deadline, constraints);
        constraints.setRow(3);

        JBPanel panel = new JBPanel(new BorderLayout());
        label = new JBLabel("Label: " +
                (task.getLabels().size() == 0 ? "None" : task.getLabels().get(0).getTitle()),
                UIUtil.ComponentStyle.REGULAR, UIUtil.FontColor.NORMAL);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        panel.add(label);
        form.add(panel, constraints);
        constraints.setRow(3);

        JBPanel getState = new JBPanel(new BorderLayout());
        label = new JBLabel("State: " + States.toString(task.getState()),
                UIUtil.ComponentStyle.REGULAR, UIUtil.FontColor.NORMAL);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        getState.add(label);
        form.add(getState, constraints);
        createProject.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        createProject.setContentPane(form);
        createProject.pack();
        createProject.setVisible(true);
    }
}

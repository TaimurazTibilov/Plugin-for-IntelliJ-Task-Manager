package org.taimuraztibilov.taskmanager.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.JBColor;
import com.intellij.ui.components.*;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.util.ui.JBUI;
import com.intellij.util.ui.UIUtil;
import org.jdesktop.swingx.JXDatePicker;
import org.taimuraztibilov.taskmanager.base.*;
import org.taimuraztibilov.taskmanager.base.Label;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.TimeZone;

public class AddDataFormBuilder {

    public static synchronized void addProjectByUser(Project project) {
        JFrame createProject = new JFrame("Add new project");
        GridConstraints constraints = new GridConstraints();
        JBPanel form = new JBPanel();
        form.setLayout(new GridLayoutManager(4, 1,
                JBUI.insets(12, 20), 20, 20));

        JBPanel title = new JBPanel(new BorderLayout());
        JBLabel label = new JBLabel("Title:              ",
                UIUtil.ComponentStyle.REGULAR, UIUtil.FontColor.NORMAL);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        title.add(label);
        JBTextField getTitle = new JBTextField();
        getTitle.setFont(label.getFont());
        getTitle.setColumns(50);
        title.add(getTitle, BorderLayout.EAST);
        form.add(title, constraints);
        constraints.setRow(1);

        JBPanel description = new JBPanel(new BorderLayout());
        label = new JBLabel("Description: ",
                UIUtil.ComponentStyle.REGULAR, UIUtil.FontColor.NORMAL);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        description.add(label);
        JBTextArea getDescription = new JBTextArea(6, 50);
        getDescription.setLineWrap(true);
        getDescription.setWrapStyleWord(true);
        getDescription.setFont(label.getFont());
        description.add(new JBScrollPane(getDescription), BorderLayout.EAST);
        form.add(description, constraints);
        constraints.setRow(2);

        JBPanel getState = new JBPanel(new BorderLayout());
        label = new JBLabel("State: ",
                UIUtil.ComponentStyle.REGULAR, UIUtil.FontColor.NORMAL);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        getState.add(label);
        DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>(States.getArray());
        ComboBox<String> comboBox = new ComboBox<>(comboBoxModel);
        comboBox.setFont(label.getFont());
        comboBox.setSelectedIndex(1);
        getState.add(comboBox, BorderLayout.EAST);
        form.add(getState, constraints);
        constraints.setRow(0);

        JBPanel buttons = new JBPanel(new GridLayoutManager(1, 2,
                JBUI.insets(10, 10), 0, 0));
        JButton createButton = new JButton("Create");
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createProject.dispose();
            }
        });
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String title = getTitle.getText();
                    String description = getDescription.getText();
                    int state = States.toInt((String) comboBox.getSelectedItem());
                    PluginManagerService.getInstance().setTrackingProject(
                            DataBaseManager.getInstance().addProject(title, description, state).getId());
                    // TODO: 08.05.2020 Notification that project was added correctly
                    createProject.dispose();
                } catch (SQLException throwables) {
                    // TODO: 08.05.2020 Show notification about exception
                }
            }
        });
        buttons.add(createButton, constraints);
        constraints.setColumn(1);
        buttons.add(cancelButton, constraints);
        constraints.setColumn(0);
        constraints.setRow(3);
        form.add(buttons, constraints);
        createProject.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        createProject.setContentPane(form);
        createProject.pack();
        createProject.setVisible(true);
    }

    public static synchronized void addMilestoneByUser(int projectId) {
        JFrame createProject = new JFrame("Add new milestone");
        GridConstraints constraints = new GridConstraints();
        JBPanel form = new JBPanel();
        form.setLayout(new GridLayoutManager(5, 1,
                JBUI.insets(12, 20), 20, 20));

        JBPanel title = new JBPanel(new BorderLayout());
        JBLabel label = new JBLabel("Title:              ",
                UIUtil.ComponentStyle.REGULAR, UIUtil.FontColor.NORMAL);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        title.add(label);
        JBTextField getTitle = new JBTextField("");
        getTitle.setFont(label.getFont());
        getTitle.setColumns(50);
        title.add(getTitle, BorderLayout.EAST);
        form.add(title, constraints);
        constraints.setRow(1);

        JBPanel description = new JBPanel(new BorderLayout());
        label = new JBLabel("Description: ",
                UIUtil.ComponentStyle.REGULAR, UIUtil.FontColor.NORMAL);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        description.add(label);
        JBTextArea getDescription = new JBTextArea(6, 50);
        getDescription.setLineWrap(true);
        getDescription.setWrapStyleWord(true);
        getDescription.setFont(label.getFont());
        description.add(new JBScrollPane(getDescription), BorderLayout.EAST);
        form.add(description, constraints);
        constraints.setRow(2);

        JBPanel deadline = new JBPanel(new BorderLayout());
        label = new JBLabel("Deadline: ",
                UIUtil.ComponentStyle.REGULAR, UIUtil.FontColor.NORMAL);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        deadline.add(label);
        JXDatePicker getDeadline = new JXDatePicker();
        getDeadline.setTimeZone(TimeZone.getDefault());
        getDeadline.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Date.valueOf(LocalDate.now()).after(getDeadline.getDate()))
                    getDeadline.setDate(Date.valueOf(LocalDate.now()));
            }
        });
        deadline.add(getDeadline, BorderLayout.EAST);
        form.add(deadline, constraints);
        constraints.setRow(3);

        JBPanel getState = new JBPanel(new BorderLayout());
        label = new JBLabel("State: ",
                UIUtil.ComponentStyle.REGULAR, UIUtil.FontColor.NORMAL);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        getState.add(label);
        DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>(States.getArray());
        ComboBox<String> comboBox = new ComboBox<>(comboBoxModel);
        comboBox.setFont(label.getFont());
        comboBox.setSelectedIndex(1);
        getState.add(comboBox, BorderLayout.EAST);
        form.add(getState, constraints);
        constraints.setRow(0);

        JBPanel buttons = new JBPanel(new GridLayoutManager(1, 2,
                JBUI.insets(10, 10), 0, 0));
        JButton createButton = new JButton("Create");
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createProject.dispose();
            }
        });
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (getDeadline.getDate() == null) {
                        // TODO: 10.05.2020 show notification
                        return;
                    }
                    String title = getTitle.getText();
                    String description = getDescription.getText();
                    LocalDateTime deadline = LocalDateTime.parse(getDeadline.getDate().toInstant().toString()
                            .substring(0, getDeadline.getDate().toInstant().toString().length() - 1));
                    int state = States.toInt((String) comboBox.getSelectedItem());
                    PluginManagerService.getInstance().setTrackingMilestone(
                            DataBaseManager.getInstance()
                                    .addMilestone(projectId, title, description, deadline, state).getId());
                    // TODO: 08.05.2020 Notification that project was added correctly
                    createProject.dispose();
                } catch (SQLException throwables) {
                    // TODO: 08.05.2020 Show notification about exception
                }
            }
        });
        buttons.add(createButton, constraints);
        constraints.setColumn(1);
        buttons.add(cancelButton, constraints);
        constraints.setColumn(0);
        constraints.setRow(4);
        form.add(buttons, constraints);
        createProject.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        createProject.setContentPane(form);
        createProject.pack();
        createProject.setVisible(true);
    }

    public static synchronized void addTaskByUser(int milestoneId) {
        JFrame createProject = new JFrame("Add new task");
        GridConstraints constraints = new GridConstraints();
        JBPanel form = new JBPanel();
        form.setLayout(new GridLayoutManager(6, 1,
                JBUI.insets(12, 20), 20, 20));

        JBPanel title = new JBPanel(new BorderLayout());
        JBLabel label = new JBLabel("Title:              ",
                UIUtil.ComponentStyle.REGULAR, UIUtil.FontColor.NORMAL);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        title.add(label);
        JBTextField getTitle = new JBTextField("");
        getTitle.setFont(label.getFont());
        getTitle.setColumns(50);
        title.add(getTitle, BorderLayout.EAST);
        form.add(title, constraints);
        constraints.setRow(1);

        JBPanel description = new JBPanel(new BorderLayout());
        label = new JBLabel("Description: ",
                UIUtil.ComponentStyle.REGULAR, UIUtil.FontColor.NORMAL);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        description.add(label);
        JBTextArea getDescription = new JBTextArea(6, 50);
        getDescription.setLineWrap(true);
        getDescription.setWrapStyleWord(true);
        getDescription.setFont(label.getFont());
        description.add(new JBScrollPane(getDescription), BorderLayout.EAST);
        form.add(description, constraints);
        constraints.setRow(2);
        ;

        JBPanel deadline = new JBPanel(new BorderLayout());
        label = new JBLabel("Deadline: ",
                UIUtil.ComponentStyle.REGULAR, UIUtil.FontColor.NORMAL);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        deadline.add(label);
        JXDatePicker getDeadline = new JXDatePicker();
        getDeadline.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Date.valueOf(LocalDate.now()).after(getDeadline.getDate()))
                    getDeadline.setDate(Date.valueOf(LocalDate.now()));
            }
        });
        deadline.add(getDeadline, BorderLayout.EAST);
        form.add(deadline, constraints);
        constraints.setRow(3);

        JBPanel getState = new JBPanel(new BorderLayout());
        label = new JBLabel("State: ",
                UIUtil.ComponentStyle.REGULAR, UIUtil.FontColor.NORMAL);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        getState.add(label);
        DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>(States.getArray());
        ComboBox<String> comboBox = new ComboBox<>(comboBoxModel);
        comboBox.setFont(label.getFont());
        comboBox.setSelectedIndex(1);
        getState.add(comboBox, BorderLayout.EAST);
        form.add(getState, constraints);
        constraints.setRow(4);

        JBPanel getLabels = new JBPanel(new BorderLayout());
        label = new JBLabel("Label: ",
                UIUtil.ComponentStyle.REGULAR, UIUtil.FontColor.NORMAL);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        getState.add(label);
        ComboBox<String> labels = new ComboBox<>();
        ArrayList<Label> arrayList = new ArrayList<>();
        try {
            arrayList = DataBaseManager.getInstance().getLabels();
        } catch (SQLException throwables) {
            // TODO: 08.05.2020 Show notification about exception
        }
        for (Label dataLabel : arrayList) {
            labels.addItem(dataLabel.getTitle());
        }
        labels.addItem("None");
        labels.setSelectedIndex(0);
        getLabels.add(labels, BorderLayout.EAST);
        form.add(getLabels, constraints);
        constraints.setRow(0);

        JBPanel buttons = new JBPanel(new GridLayoutManager(1, 2,
                JBUI.insets(10, 10), 0, 0));
        JButton createButton = new JButton("Create");
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createProject.dispose();
            }
        });
        ArrayList<Label> finalArrayList = arrayList;
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (getDeadline.getDate() == null) {
                        // TODO: 10.05.2020 show notification
                        return;
                    }
                    String title = getTitle.getText();
                    String description = getDescription.getText();
                    LocalDateTime deadline = LocalDateTime.parse(getDeadline.getDate().toInstant().toString()
                            .substring(0, getDeadline.getDate().toInstant().toString().length() - 1));
                    int state = States.toInt((String) comboBox.getSelectedItem());
                    Label label1 = null;
                    if (labels.getSelectedItem() != "None")
                        label1 = finalArrayList.get(labels.getSelectedIndex());
                    ArrayList<Label> labels1 = new ArrayList<>();
                    if (label1 != null)
                        labels1.add(label1);
                    PluginManagerService.getInstance().setTrackingTask(
                            DataBaseManager.getInstance()
                                    .addTask(milestoneId, title, description, deadline, LocalTime.MIN, state, labels1)
                                    .getId());
                    // TODO: 08.05.2020 Notification that project was added correctly
                    createProject.dispose();
                } catch (SQLException throwables) {
                    // TODO: 08.05.2020 Show notification about exception
                }
            }
        });
        buttons.add(createButton, constraints);
        constraints.setColumn(1);
        buttons.add(cancelButton, constraints);
        constraints.setColumn(0);
        constraints.setRow(5);
        form.add(buttons, constraints);
        createProject.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        createProject.setContentPane(form);
        createProject.pack();
        createProject.setVisible(true);
    }

    public static synchronized void addLabelByUser() {
        JFrame createProject = new JFrame("Add new label");
        GridConstraints constraints = new GridConstraints();
        JBPanel form = new JBPanel();
        form.setLayout(new GridLayoutManager(2, 1,
                JBUI.insets(12, 20), 20, 20));

        JBPanel title = new JBPanel(new BorderLayout());
        JBLabel label = new JBLabel("Title: ",
                UIUtil.ComponentStyle.REGULAR, UIUtil.FontColor.NORMAL);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        title.add(label);
        JBTextField getTitle = new JBTextField("");
        getTitle.setFont(label.getFont());
        getTitle.setColumns(50);
        title.add(getTitle, BorderLayout.EAST);
        form.add(title, constraints);

        JBPanel buttons = new JBPanel(new GridLayoutManager(1, 2,
                JBUI.insets(10, 10), 0, 0));
        JButton createButton = new JButton("Create");
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createProject.dispose();
            }
        });
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String title = getTitle.getText();
                    String color = String.valueOf(JBColor.WHITE.getRGB());
                    DataBaseManager.getInstance().addLabel(color, title);
                    // TODO: 08.05.2020 Notification that project was added correctly
                    createProject.dispose();
                } catch (SQLException throwables) {
                    // TODO: 08.05.2020 Show notification about exception
                }
            }
        });
        buttons.add(createButton, constraints);
        constraints.setColumn(1);
        buttons.add(cancelButton, constraints);
        constraints.setColumn(0);
        constraints.setRow(1);
        form.add(buttons, constraints);
        createProject.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        createProject.setContentPane(form);
        createProject.pack();
        createProject.setVisible(true);
    }

    public static synchronized void addKeyPointByUser(LocalTime timeSpent, int taskId) {
        JFrame createProject = new JFrame("Add new keypoint");
        GridConstraints constraints = new GridConstraints();
        JBPanel form = new JBPanel();
        form.setLayout(new GridLayoutManager(2, 1,
                JBUI.insets(12, 20), 20, 20));

        JBPanel description = new JBPanel(new BorderLayout());
        JBLabel label = new JBLabel("Description of solution: ",
                UIUtil.ComponentStyle.REGULAR, UIUtil.FontColor.NORMAL);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        description.add(label);
        JBTextArea getDescription = new JBTextArea(6, 50);
        getDescription.setLineWrap(true);
        getDescription.setWrapStyleWord(true);
        getDescription.setFont(label.getFont());
        description.add(new JBScrollPane(getDescription), BorderLayout.EAST);
        form.add(description, constraints);

        JBPanel buttons = new JBPanel(new GridLayoutManager(1, 2,
                JBUI.insets(10, 10), 0, 0));
        JButton createButton = new JButton("Create");
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createProject.dispose();
            }
        });
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String title = getDescription.getText();
                    DataBaseManager.getInstance().addKeyPoint(taskId, title, LocalDate.now()).setTimeSpent(timeSpent);
                    // TODO: 08.05.2020 Notification that project was added correctly
                    createProject.dispose();
                } catch (SQLException throwables) {
                    // TODO: 08.05.2020 Show notification about exception
                }
            }
        });
        buttons.add(createButton, constraints);
        constraints.setColumn(1);
        buttons.add(cancelButton, constraints);
        constraints.setColumn(0);
        constraints.setRow(1);
        form.add(buttons, constraints);
        createProject.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        createProject.setContentPane(form);
        createProject.pack();
        createProject.setVisible(true);
    }

    public static synchronized void generateReport(String projectPath) {
        JFrame createProject = new JFrame("Create new report");
        GridConstraints constraints = new GridConstraints();
        JBPanel form = new JBPanel();
        form.setLayout(new GridLayoutManager(5, 1,
                JBUI.insets(12, 20), 20, 20));

        JBPanel title = new JBPanel(new BorderLayout());
        JBLabel label = new JBLabel("Full name:   ",
                UIUtil.ComponentStyle.REGULAR, UIUtil.FontColor.NORMAL);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        title.add(label);
        JBTextField getTitle = new JBTextField("");
        getTitle.setFont(label.getFont());
        getTitle.setColumns(50);
        title.add(getTitle, BorderLayout.EAST);
        form.add(title, constraints);
        constraints.setRow(1);

        JBPanel organ = new JBPanel(new BorderLayout());
        label = new JBLabel("Organization:  ",
                UIUtil.ComponentStyle.REGULAR, UIUtil.FontColor.NORMAL);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        organ.add(label);
        JBTextField getOrg = new JBTextField("");
        getOrg.setFont(label.getFont());
        getOrg.setColumns(50);
        organ.add(getOrg, BorderLayout.EAST);
        form.add(organ, constraints);
        constraints.setRow(2);

        JBPanel deadline = new JBPanel(new BorderLayout());
        label = new JBLabel("Date from: ",
                UIUtil.ComponentStyle.REGULAR, UIUtil.FontColor.NORMAL);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        deadline.add(label);
        JXDatePicker getDeadline = new JXDatePicker();
        deadline.add(getDeadline, BorderLayout.EAST);
        form.add(deadline, constraints);
        constraints.setRow(3);

        JBPanel dateTo = new JBPanel(new BorderLayout());
        label = new JBLabel("Date to:   ",
                UIUtil.ComponentStyle.REGULAR, UIUtil.FontColor.NORMAL);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        dateTo.add(label);
        JXDatePicker datePicker = new JXDatePicker();
        dateTo.add(datePicker, BorderLayout.EAST);
        form.add(dateTo, constraints);
        constraints.setRow(0);

        JBPanel buttons = new JBPanel(new GridLayoutManager(1, 2,
                JBUI.insets(10, 10), 0, 0));
        JButton createButton = new JButton("Create");
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createProject.dispose();
            }
        });
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (getDeadline.getDate() == null || datePicker.getDate() == null) {
                        // TODO: 10.05.2020 show notification
                        return;
                    }
                    String name = getTitle.getText();
                    String organ = getOrg.getText();
                    LocalDateTime from = LocalDateTime.parse(getDeadline.getDate().toInstant().toString()
                            .substring(0, getDeadline.getDate().toInstant().toString().length() - 1));
                    LocalDateTime to = LocalDateTime.parse(datePicker.getDate().toInstant().toString()
                            .substring(0, datePicker.getDate().toInstant().toString().length() - 1));
                    ReportManager.getInstance().generateReport(projectPath, from.toLocalDate(), to.toLocalDate(),
                            PluginManagerService.getInstance().getTrackingProject(), organ, name);
                    // TODO: 08.05.2020 Notification that project was added correctly
                    createProject.dispose();
                } catch (SQLException throwables) {
                    // TODO: 08.05.2020 Show notification about exception
                }
            }
        });
        buttons.add(createButton, constraints);
        constraints.setColumn(1);
        buttons.add(cancelButton, constraints);
        constraints.setColumn(0);
        constraints.setRow(4);
        form.add(buttons, constraints);
        createProject.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        createProject.setContentPane(form);
        createProject.pack();
        createProject.setVisible(true);
    }
}

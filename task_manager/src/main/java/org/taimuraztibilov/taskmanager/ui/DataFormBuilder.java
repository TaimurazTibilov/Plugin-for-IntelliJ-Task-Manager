package org.taimuraztibilov.taskmanager.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.ColorPicker;
import com.intellij.ui.JBColor;
import com.intellij.ui.colorpicker.ColorPickerBuilder;
import com.intellij.ui.components.*;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.util.ui.JBUI;
import com.intellij.util.ui.UIUtil;
import org.jdesktop.swingx.JXDatePicker;
import org.taimuraztibilov.taskmanager.base.DataBaseManager;
import org.taimuraztibilov.taskmanager.base.Label;
import org.taimuraztibilov.taskmanager.base.Milestone;
import org.taimuraztibilov.taskmanager.base.PluginManagerService;
import org.taimuraztibilov.taskmanager.base.States;

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

public class DataFormBuilder {

    public void addProjectByUser(Project project) {
        JFrame createProject = new JFrame("Add new project");
        JBPanel form = new JBPanel();
        form.setLayout(new GridLayoutManager(4, 1,
                JBUI.insets(12, 20), 20, 20));

        JBPanel title = new JBPanel(new BorderLayout());
        JBLabel label = new JBLabel("Title: ",
                UIUtil.ComponentStyle.REGULAR, UIUtil.FontColor.NORMAL);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        title.add(label);
        JBTextField getTitle = new JBTextField(project.getName());
        getTitle.setFont(label.getFont());
        title.add(getTitle);
        form.add(title);

        JBPanel description = new JBPanel(new BorderLayout());
        label = new JBLabel("Description: ",
                UIUtil.ComponentStyle.REGULAR, UIUtil.FontColor.NORMAL);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        description.add(label);
        JBTextArea getDescription = new JBTextArea(project.getName(), 0, 30);
        getDescription.setLineWrap(true);
        getDescription.setWrapStyleWord(true);
        getDescription.setFont(label.getFont());
        description.add(new JBScrollPane(getDescription));
        form.add(description);

        JBPanel getState = new JBPanel(new BorderLayout());
        label = new JBLabel("State: ",
                UIUtil.ComponentStyle.REGULAR, UIUtil.FontColor.NORMAL);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        getState.add(label);
        DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>(States.getArray());
        ComboBox<String> comboBox = new ComboBox<>(comboBoxModel);
        comboBox.setFont(label.getFont());
        comboBox.setSelectedIndex(1);
        getState.add(comboBox);
        form.add(getState);

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
                    int state = Integer.parseInt((String) comboBox.getSelectedItem());
                    PluginManagerService.getInstance().setTrackingProject(
                            DataBaseManager.getInstance().addProject(title, description, state).getId());
                    // TODO: 08.05.2020 Notification that project was added correctly
                    createProject.dispose();
                } catch (SQLException throwables) {
                    // TODO: 08.05.2020 Show notification about exception
                }
            }
        });
        buttons.add(createButton);
        buttons.add(cancelButton);
        form.add(buttons);
        createProject.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        createProject.setContentPane(form);
        createProject.pack();
        createProject.setVisible(true);
    }

    public void addMilestoneByUser(int projectId) {
        JFrame createProject = new JFrame("Add new milestone");
        JBPanel form = new JBPanel();
        form.setLayout(new GridLayoutManager(5, 1,
                JBUI.insets(12, 20), 20, 20));

        JBPanel title = new JBPanel(new BorderLayout());
        JBLabel label = new JBLabel("Title: ",
                UIUtil.ComponentStyle.REGULAR, UIUtil.FontColor.NORMAL);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        title.add(label);
        JBTextField getTitle = new JBTextField("");
        getTitle.setFont(label.getFont());
        title.add(getTitle);
        form.add(title);

        JBPanel description = new JBPanel(new BorderLayout());
        label = new JBLabel("Description: ",
                UIUtil.ComponentStyle.REGULAR, UIUtil.FontColor.NORMAL);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        description.add(label);
        JBTextArea getDescription = new JBTextArea("", 0, 30);
        getDescription.setLineWrap(true);
        getDescription.setWrapStyleWord(true);
        getDescription.setFont(label.getFont());
        description.add(new JBScrollPane(getDescription));
        form.add(description);

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
        deadline.add(getDeadline);
        form.add(deadline);

        JBPanel getState = new JBPanel(new BorderLayout());
        label = new JBLabel("State: ",
                UIUtil.ComponentStyle.REGULAR, UIUtil.FontColor.NORMAL);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        getState.add(label);
        DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>(States.getArray());
        ComboBox<String> comboBox = new ComboBox<>(comboBoxModel);
        comboBox.setFont(label.getFont());
        comboBox.setSelectedIndex(1);
        getState.add(comboBox);
        form.add(getState);

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
                    LocalDateTime deadline = LocalDateTime.from(getDeadline.getDate().toInstant());
                    int state = Integer.parseInt((String) comboBox.getSelectedItem());
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
        buttons.add(createButton);
        buttons.add(cancelButton);
        form.add(buttons);
        createProject.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        createProject.setContentPane(form);
        createProject.pack();
        createProject.setVisible(true);
    }

    public void addTaskByUser(int milestoneId) {
        JFrame createProject = new JFrame("Add new task");
        JBPanel form = new JBPanel();
        form.setLayout(new GridLayoutManager(6, 1,
                JBUI.insets(12, 20), 20, 20));

        JBPanel title = new JBPanel(new BorderLayout());
        JBLabel label = new JBLabel("Title: ",
                UIUtil.ComponentStyle.REGULAR, UIUtil.FontColor.NORMAL);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        title.add(label);
        JBTextField getTitle = new JBTextField("");
        getTitle.setFont(label.getFont());
        title.add(getTitle);
        form.add(title);

        JBPanel description = new JBPanel(new BorderLayout());
        label = new JBLabel("Description: ",
                UIUtil.ComponentStyle.REGULAR, UIUtil.FontColor.NORMAL);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        description.add(label);
        JBTextArea getDescription = new JBTextArea("", 0, 30);
        getDescription.setLineWrap(true);
        getDescription.setWrapStyleWord(true);
        getDescription.setFont(label.getFont());
        description.add(new JBScrollPane(getDescription));
        form.add(description);

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
        deadline.add(getDeadline);
        form.add(deadline);

        JBPanel getState = new JBPanel(new BorderLayout());
        label = new JBLabel("State: ",
                UIUtil.ComponentStyle.REGULAR, UIUtil.FontColor.NORMAL);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        getState.add(label);
        DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>(States.getArray());
        ComboBox<String> comboBox = new ComboBox<>(comboBoxModel);
        comboBox.setFont(label.getFont());
        comboBox.setSelectedIndex(1);
        getState.add(comboBox);
        form.add(getState);

        JBPanel getLabels = new JBPanel(new BorderLayout());
        label = new JBLabel("Labels: ",
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
        getLabels.add(labels);
        form.add(getLabels);

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
                    String title = getTitle.getText();
                    String description = getDescription.getText();
                    LocalDateTime deadline = LocalDateTime.from(getDeadline.getDate().toInstant());
                    int state = Integer.parseInt((String) comboBox.getSelectedItem());
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
        buttons.add(createButton);
        buttons.add(cancelButton);
        form.add(buttons);
        createProject.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        createProject.setContentPane(form);
        createProject.pack();
        createProject.setVisible(true);
    }

    public void addLabelByUser() {
        JFrame createProject = new JFrame("Add new label");
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
        title.add(getTitle);
        form.add(title);

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
        buttons.add(createButton);
        buttons.add(cancelButton);
        form.add(buttons);
        createProject.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        createProject.setContentPane(form);
        createProject.pack();
        createProject.setVisible(true);
    }
}

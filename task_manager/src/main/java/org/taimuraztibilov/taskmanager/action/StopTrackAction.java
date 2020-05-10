package org.taimuraztibilov.taskmanager.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.taimuraztibilov.taskmanager.base.TimeManager;
import org.taimuraztibilov.taskmanager.ui.AddDataFormBuilder;

import java.sql.SQLException;
import java.time.LocalTime;

public class StopTrackAction extends AnAction {
    @Override
    public void update(@NotNull AnActionEvent e) {
        Project project = e.getProject();
        e.getPresentation().setVisible(project != null);
        e.getPresentation().setEnabled(TimeManager.getInstance().getTaskId() != -1);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        try {
            int trackedId = TimeManager.getInstance().getTaskId();
            LocalTime time = TimeManager.getInstance().stopTracking();
            AddDataFormBuilder.addKeyPointByUser(time, trackedId);
        } catch (SQLException throwables) {
            // TODO: 10.05.2020 notification
        }
    }
}

package org.taimuraztibilov.taskmanager.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.taimuraztibilov.taskmanager.base.PluginManagerService;
import org.taimuraztibilov.taskmanager.ui.AddDataFormBuilder;

public class CreateReportAction extends AnAction {
    @Override
    public void update(@NotNull AnActionEvent e) {
        Project project = e.getProject();
        e.getPresentation().setVisible(project != null);
        e.getPresentation().setEnabled(PluginManagerService.getInstance().getTrackingProject() != -1);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        if (e.getProject() != null)
            AddDataFormBuilder.generateReport(e.getProject().getBasePath());
    }
}

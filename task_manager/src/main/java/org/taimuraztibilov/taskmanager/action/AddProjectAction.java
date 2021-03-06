package org.taimuraztibilov.taskmanager.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.taimuraztibilov.taskmanager.ui.AddDataFormBuilder;

public class AddProjectAction extends AnAction {
    @Override
    public void update(@NotNull AnActionEvent e) {
        Project project = e.getProject();
        e.getPresentation().setEnabledAndVisible(project != null);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        AddDataFormBuilder.addProjectByUser(e.getProject());
    }
}

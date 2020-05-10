package org.taimuraztibilov.taskmanager.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.taimuraztibilov.taskmanager.ui.AddDataFormBuilder;

public class AddLabelAction extends AnAction {
    @Override
    public void update(@NotNull AnActionEvent e) {
        Project project = e.getProject();
        e.getPresentation().setEnabled(project != null);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        AddDataFormBuilder.addLabelByUser();
    }
}

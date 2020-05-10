package org.taimuraztibilov.taskmanager.base;

import com.intellij.openapi.components.Service;
import com.intellij.openapi.externalSystem.autoimport.AutoImportProjectTracker;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.rt.coverage.data.ProjectData;

@Service
public final class PluginManagerService {
    private int trackingProject;
    private int trackingMilestone;
    private int trackingTask;
    private static PluginManagerService instance;

    private PluginManagerService() {
        trackingMilestone = -1;
        trackingProject = -1;
        trackingTask = -1;
    }

    public static synchronized PluginManagerService getInstance() {
        if (instance == null)
            instance = new PluginManagerService();
        return instance;
    }

    public synchronized int getTrackingProject() {
        return trackingProject;
    }

    public synchronized void setTrackingProject(int trackingProject) {
        this.trackingProject = trackingProject;
    }

    public synchronized int getTrackingMilestone() {
        return trackingMilestone;
    }

    public synchronized void setTrackingMilestone(int trackingMilestone) {
        this.trackingMilestone = trackingMilestone;
    }

    public synchronized int getTrackingTask() {
        return trackingTask;
    }

    public synchronized void setTrackingTask(int trackingTask) {
        this.trackingTask = trackingTask;
    }
}

package org.taimuraztibilov.taskmanager;

import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public class TaskManager {
    private static TaskManager instance;

    private TaskManager() {
    }

    public static synchronized TaskManager getInstance() {
        if (instance == null)
            instance = new TaskManager();
        return instance;
    }

    public void generateReport(String projectPath, LocalDate from, LocalDate to) throws IOException {
        String path = projectPath;
        if (projectPath.charAt(projectPath.length() - 1) != '\\' && projectPath.charAt(projectPath.length() - 1) != '/')
            path += '\\';
        path += "Report on " + LocalDate.now().toString() + ".csv";
        CSVWriter writer = new CSVWriter(new FileWriter(path));
    }
}

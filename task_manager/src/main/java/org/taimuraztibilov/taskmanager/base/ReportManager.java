package org.taimuraztibilov.taskmanager.base;

import com.intellij.openapi.components.Service;
import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public final class ReportManager {
    private static ReportManager instance;

    private ReportManager() {
    }

    public static synchronized ReportManager getInstance() {
        if (instance == null)
            instance = new ReportManager();
        return instance;
    }

    public String generateReport(String projectPath, LocalDate from, LocalDate to, int projectId,
                                 String organisation, String developerName) throws SQLException {
        try {
            String path = projectPath;
            if (projectPath.charAt(projectPath.length() - 1) != '\\' && projectPath.charAt(projectPath.length() - 1) != '/')
                path += "\\reports_taskmanager";
            path += "Report on " + LocalDateTime.now().toString() + ".csv";
            CSVWriter writer = new CSVWriter(new FileWriter(path));
            writer.writeNext(new String[]{"Отчет по проекту с " + from.toString() + " по " + to.toString()});
            writer.writeNext(new String[]{
                    "Наименование проекта: ", DataBaseManager.getInstance().getProject(projectId).getTitle()
            });
            writer.writeNext(new String[]{
                    "Разработчик: ", developerName
            });
            writer.writeNext(new String[]{
                    "Организация: ", organisation
            });
            writer.writeNext(new String[]{});
            writer.writeNext(new String[]{
                    "Этап", "Задача", "Дата", "Комментарий", "Затраченное время"
            });
            ResultSet data = DataBaseManager.getInstance().getReportData(from, to, projectId);
            while (data.next())
                writer.writeNext(new String[]{
                        data.getString(1),
                        data.getString(2),
                        data.getString(3),
                        data.getString(4),
                        data.getString(5)
                });
            writer.close();
            return "Отчет был успешно создан. Путь: " + path;
        } catch (IOException e) {
            return e.getLocalizedMessage();
        }
    }
}

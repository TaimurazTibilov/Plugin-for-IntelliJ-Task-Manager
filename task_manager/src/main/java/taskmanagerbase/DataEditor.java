package taskmanager;

import java.sql.SQLException;

public interface DataEditor {
    void editData(String table, String column, String value, int id) throws SQLException;
    void addLabelToTask(int labelId, int taskId) throws SQLException;
    void removeLabelFromTask(int labelId, int taskId) throws SQLException;
}

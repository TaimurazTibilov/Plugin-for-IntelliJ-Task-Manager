package org.taimuraztibilov.taskmanager.base;

import java.sql.SQLException;

public class Label {
    private final int id;
    private String color;
    private String title;
    private DataEditor listenerOnEdit;

    public Label(int id, String color, String title) {
        this.id = id;
        this.color = color;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getColor() {
        return color;
    }

    public String getTitle() {
        return title;
    }

    public Label setListenerOnEdit(DataEditor listenerOnEdit) {
        this.listenerOnEdit = listenerOnEdit;
        return this;
    }

    public void setColor(String color) throws SQLException {
        this.color = color;
        listenerOnEdit.editData("label", "color", color, id);
    }

    public void setTitle(String title) throws SQLException {
        this.title = title;
        listenerOnEdit.editData("label", "title", title, id);
    }
}

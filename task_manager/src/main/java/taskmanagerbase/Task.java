package taskmanager;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class Task {
    private final int id;
    private int milestoneId;
    private String title;
    private String description;
    private final LocalDateTime createdOn;
    private LocalDateTime deadline;
    private LocalTime timeEstimated;
    private LocalTime timeSpent;
    private int state;
    private ArrayList<Label> labels;
    private ArrayList<KeyPoint> keyPoints;
    private DataEditor listenerOnEdit;

    public Task(int id, int milestoneId, String title, String description, LocalDateTime createdOn,
                LocalDateTime deadline, LocalTime timeEstimated, LocalTime timeSpent, int state) {
        this.id = id;
        this.milestoneId = milestoneId;
        this.title = title;
        this.description = description;
        this.createdOn = createdOn;
        this.deadline = deadline;
        this.timeEstimated = timeEstimated;
        this.timeSpent = timeSpent;
        this.state = state;
        this.labels = new ArrayList<>();
        this.keyPoints = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public int getMilestoneId() {
        return milestoneId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public LocalTime getTimeEstimated() {
        return timeEstimated;
    }

    public LocalTime getTimeSpent() {
        return timeSpent;
    }

    public int getState() {
        return state;
    }

    public ArrayList<Label> getLabels() {
        return labels;
    }

    public ArrayList<KeyPoint> getKeyPoints() {
        return keyPoints;
    }

    public Task setListenerOnEdit(DataEditor listenerOnEdit) {
        this.listenerOnEdit = listenerOnEdit;
        return this;
    }

    public void setMilestoneId(int milestoneId) throws SQLException {
        this.milestoneId = milestoneId;
        listenerOnEdit.editData("task", "milestone_id", String.valueOf(milestoneId), id);
    }

    public void setTitle(String title) throws SQLException {
        this.title = title;
        listenerOnEdit.editData("task", "title", title, id);
    }

    public void setDescription(String description) throws SQLException {
        this.description = description;
        listenerOnEdit.editData("task", "description", description, id);
    }

    public void setDeadline(LocalDateTime deadline) throws SQLException {
        this.deadline = deadline;
        listenerOnEdit.editData("task", "deadline", deadline.toString(), id);
    }

    public void setTimeEstimated(LocalTime timeEstimated) throws SQLException {
        this.timeEstimated = timeEstimated;
        listenerOnEdit.editData("task", "time_estimated", timeEstimated.toString(), id);
    }

    public void setTimeSpent(LocalTime timeSpent) throws SQLException {
        this.timeSpent = timeSpent;
        listenerOnEdit.editData("task", "time_spent", timeSpent.toString(), id);
    }

    public void setState(int state) throws SQLException {
        this.state = state;
        listenerOnEdit.editData("task", "state", String.valueOf(state), id);
    }

    public void setLabels(ArrayList<Label> labels) {
        this.labels = labels;
    }

    public void setKeyPoints(ArrayList<KeyPoint> keyPoints) {
        this.keyPoints = keyPoints;
    }

    public void addLabel(Label label) throws SQLException {
        if (labels.contains(label))
            return;
        listenerOnEdit.addLabelToTask(label.getId(), id);
        labels.add(label);
    }

    public void removeLabel(Label label) throws SQLException {
        if (!labels.contains(label))
            return;
        listenerOnEdit.removeLabelFromTask(label.getId(), id);
        labels.remove(label);
    }

    public void addKeyPoint(KeyPoint keyPoint) {
        keyPoints.add(keyPoint);
    }

    public void removeKeyPoint(KeyPoint keyPoint) {
        keyPoints.remove(keyPoint);
    }
}

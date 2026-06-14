package com.example.gui1;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

/**
 * Data model for a single row of the Kanban table.
 */
public class Task {

    /** Priority level, displayed as a red / yellow / green bar. */
    public enum PriorityLevel {
        HIGH, MEDIUM, LOW
    }

    /** Workflow status, displayed as a coloured tag. */
    public enum TaskStatus {
        NOT_STARTED("Not Started"),
        IN_PROGRESS("In Progress"),
        DONE("Done");

        private final String displayName;

        TaskStatus(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }

        @Override
        public String toString() {
            return displayName;
        }
    }

    private final StringProperty name = new SimpleStringProperty(this, "name", "");
    private final StringProperty jobTitle = new SimpleStringProperty(this, "jobTitle", "");
    private final ObjectProperty<LocalDate> deadline = new SimpleObjectProperty<>(this, "deadline", null);
    private final ObjectProperty<PriorityLevel> priority = new SimpleObjectProperty<>(this, "priority", PriorityLevel.MEDIUM);
    private final ObjectProperty<TaskStatus> status = new SimpleObjectProperty<>(this, "status", TaskStatus.NOT_STARTED);
    private final StringProperty project = new SimpleStringProperty(this, "project", "");
    private final StringProperty comment = new SimpleStringProperty(this, "comment", "");

    public Task(String name, String jobTitle, LocalDate deadline, PriorityLevel priority,
                TaskStatus status, String project, String comment) {
        setName(name);
        setJobTitle(jobTitle);
        setDeadline(deadline);
        setPriority(priority);
        setStatus(status);
        setProject(project);
        setComment(comment);
    }

    public String getName() { return name.get(); }
    public void setName(String value) { name.set(value); }
    public StringProperty nameProperty() { return name; }

    public String getJobTitle() { return jobTitle.get(); }
    public void setJobTitle(String value) { jobTitle.set(value); }
    public StringProperty jobTitleProperty() { return jobTitle; }

    public LocalDate getDeadline() { return deadline.get(); }
    public void setDeadline(LocalDate value) { deadline.set(value); }
    public ObjectProperty<LocalDate> deadlineProperty() { return deadline; }

    public PriorityLevel getPriority() { return priority.get(); }
    public void setPriority(PriorityLevel value) { priority.set(value); }
    public ObjectProperty<PriorityLevel> priorityProperty() { return priority; }

    public TaskStatus getStatus() { return status.get(); }
    public void setStatus(TaskStatus value) { status.set(value); }
    public ObjectProperty<TaskStatus> statusProperty() { return status; }

    public String getProject() { return project.get(); }
    public void setProject(String value) { project.set(value); }
    public StringProperty projectProperty() { return project; }

    public String getComment() { return comment.get(); }
    public void setComment(String value) { comment.set(value); }
    public StringProperty commentProperty() { return comment; }
}
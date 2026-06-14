package com.example.gui1;

import com.example.gui1.Task.TaskStatus;
import com.example.gui1.Task.PriorityLevel;


import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Notion-style Kanban board built with a single JavaFX TableView.
 */
public class KanbanApp extends Application {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private final ObservableList<Task> tasks = FXCollections.observableArrayList();

    @Override
    public void start(Stage stage) {
        seedData();

        TableView<Task> table = createTable();

        FilteredList<Task> filtered = new FilteredList<>(tasks, t -> true);
        SortedList<Task> sorted = new SortedList<>(filtered);
        sorted.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sorted);

        TextField searchField = new TextField();
        searchField.setPromptText("Search...");
        searchField.getStyleClass().add("search-field");
        searchField.textProperty().addListener((obs, oldVal, newVal) ->
                filtered.setPredicate(task -> matches(task, newVal)));

        ToggleButton themeToggle = new ToggleButton("\uD83C\uDF19 Dark Mode");
        themeToggle.getStyleClass().add("theme-toggle");

        Label title = new Label("\uD83D\uDCCB Kanban Board");
        title.getStyleClass().add("toolbar-title");

        Region spacer = new Region();
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

        HBox toolbar = new HBox(12, title, spacer, searchField, themeToggle);
        toolbar.setAlignment(Pos.CENTER_LEFT);
        toolbar.getStyleClass().add("toolbar");

        Button addButton = new Button("+ New");
        addButton.getStyleClass().add("add-button");
        addButton.setOnAction(e -> tasks.add(new Task("", "", null,
                PriorityLevel.MEDIUM, TaskStatus.NOT_STARTED, "", "")));

        HBox footer = new HBox(addButton);
        footer.getStyleClass().add("footer");

        VBox layout = new VBox(toolbar, table, footer);
        layout.getStyleClass().add("root-layout");
        VBox.setVgrow(table, javafx.scene.layout.Priority.ALWAYS);

        StackPane root = new StackPane(layout);
        root.getStyleClass().add("light-mode");

        themeToggle.setOnAction(e -> {
            boolean dark = themeToggle.isSelected();
            root.getStyleClass().removeAll("light-mode", "dark-mode");
            root.getStyleClass().add(dark ? "dark-mode" : "light-mode");
            themeToggle.setText(dark ? "\u2600\uFE0F Light Mode" : "\uD83C\uDF19 Dark Mode");
        });

        Scene scene = new Scene(root, 1280, 760);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        stage.setTitle("Kanban Board");
        stage.setScene(scene);
        stage.show();
    }

    private boolean matches(Task task, String query) {
        if (query == null || query.isBlank()) return true;
        String q = query.toLowerCase();
        return contains(task.getName(), q)
                || contains(task.getJobTitle(), q)
                || contains(task.getProject(), q)
                || contains(task.getComment(), q)
                || (task.getStatus() != null && contains(task.getStatus().getDisplayName(), q))
                || (task.getPriority() != null && contains(task.getPriority().name(), q));
    }

    private boolean contains(String source, String query) {
        return source != null && source.toLowerCase().contains(query);
    }

    private TableView<Task> createTable() {
        TableView<Task> table = new TableView<>();
        table.setEditable(true);
        table.getStyleClass().add("kanban-table");
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setFixedCellSize(46);

        Label placeholder = new Label("No matching entries");
        placeholder.getStyleClass().add("table-placeholder");
        table.setPlaceholder(placeholder);

        TableColumn<Task, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(c -> c.getValue().nameProperty());
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        nameCol.setOnEditCommit(e -> e.getRowValue().setName(e.getNewValue()));
        nameCol.setPrefWidth(150);

        TableColumn<Task, String> jobTitleCol = new TableColumn<>("Job Title");
        jobTitleCol.setCellValueFactory(c -> c.getValue().jobTitleProperty());
        jobTitleCol.setCellFactory(TextFieldTableCell.forTableColumn());
        jobTitleCol.setOnEditCommit(e -> e.getRowValue().setJobTitle(e.getNewValue()));
        jobTitleCol.setPrefWidth(150);

        TableColumn<Task, LocalDate> deadlineCol = new TableColumn<>("Deadline");
        deadlineCol.setCellValueFactory(c -> c.getValue().deadlineProperty());
        deadlineCol.setCellFactory(c -> new DeadlineCell());
        deadlineCol.setPrefWidth(150);

        TableColumn<Task, PriorityLevel> priorityCol = new TableColumn<>("Priority");
        priorityCol.setCellValueFactory(c -> c.getValue().priorityProperty());
        priorityCol.setCellFactory(c -> new PriorityCell());
        priorityCol.setPrefWidth(130);

        TableColumn<Task, TaskStatus> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(c -> c.getValue().statusProperty());
        statusCol.setCellFactory(c -> new StatusCell());
        statusCol.setPrefWidth(150);

        TableColumn<Task, String> projectCol = new TableColumn<>("Project");
        projectCol.setCellValueFactory(c -> c.getValue().projectProperty());
        projectCol.setCellFactory(c -> new ProjectCell());
        projectCol.setPrefWidth(150);

        TableColumn<Task, String> commentCol = new TableColumn<>("Comment");
        commentCol.setCellValueFactory(c -> c.getValue().commentProperty());
        commentCol.setCellFactory(TextFieldTableCell.forTableColumn());
        commentCol.setOnEditCommit(e -> e.getRowValue().setComment(e.getNewValue()));
        commentCol.setPrefWidth(220);

        table.getColumns().addAll(nameCol, jobTitleCol, deadlineCol, priorityCol, statusCol, projectCol, commentCol);

        table.setRowFactory(tv -> {
            TableRow<Task> row = new TableRow<>();
            MenuItem delete = new MenuItem("Delete row");
            delete.setOnAction(e -> {
                Task item = row.getItem();
                if (item != null) tasks.remove(item);
            });
            row.setContextMenu(new ContextMenu(delete));
            return row;
        });

        return table;
    }

    private void seedData() {
        tasks.addAll(
                new Task("Mark Johnson", "Frontend Developer", LocalDate.of(2026, 7, 15),
                        PriorityLevel.HIGH, TaskStatus.IN_PROGRESS, "Programming", "Needs review by Friday"),
                new Task("Sarah Lee", "UX Designer", LocalDate.of(2026, 6, 30),
                        PriorityLevel.MEDIUM, TaskStatus.NOT_STARTED, "Design", "Waiting for assets"),
                new Task("Mark Twain", "Consultant", LocalDate.of(2026, 8, 1),
                        PriorityLevel.LOW, TaskStatus.DONE, "Consulting", "Client approved"),
                new Task("Anna Becker", "Backend Developer", LocalDate.of(2026, 7, 1),
                        PriorityLevel.HIGH, TaskStatus.IN_PROGRESS, "Programming", "API integration ongoing"),
                new Task("Tom Wright", "Project Manager", LocalDate.of(2026, 6, 25),
                        PriorityLevel.MEDIUM, TaskStatus.DONE, "Consulting", "Final report sent")
        );
    }

    public static void main(String[] args) {
        launch(args);
    }

    // ------------------------------------------------------------------
    // Custom cells
    // ------------------------------------------------------------------

    /** Deadline cell: an inline date picker formatted as dd.MM.yyyy. */
    private static class DeadlineCell extends TableCell<Task, LocalDate> {
        private final DatePicker datePicker = new DatePicker();

        DeadlineCell() {
            datePicker.getStyleClass().add("deadline-picker");
            datePicker.setConverter(new StringConverter<>() {
                @Override
                public String toString(LocalDate date) {
                    return date != null ? DATE_FORMAT.format(date) : "";
                }

                @Override
                public LocalDate fromString(String text) {
                    if (text == null || text.isBlank()) return null;
                    try {
                        return LocalDate.parse(text, DATE_FORMAT);
                    } catch (DateTimeParseException ex) {
                        return null;
                    }
                }
            });
            datePicker.setOnAction(e -> {
                Task task = getTableRow() != null ? getTableRow().getItem() : null;
                if (task != null) {
                    task.setDeadline(datePicker.getValue());
                }
            });
        }

        @Override
        protected void updateItem(LocalDate item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
            } else {
                datePicker.setValue(item);
                setGraphic(datePicker);
            }
        }
    }

    /** Priority cell: clickable red / yellow / green bars. */
    private static class PriorityCell extends TableCell<Task, PriorityLevel> {
        private final Region highBar = bar("priority-high");
        private final Region mediumBar = bar("priority-medium");
        private final Region lowBar = bar("priority-low");
        private final HBox bars = new HBox(6, highBar, mediumBar, lowBar);

        PriorityCell() {
            bars.setAlignment(Pos.CENTER_LEFT);
            highBar.setOnMouseClicked(e -> apply(PriorityLevel.HIGH));
            mediumBar.setOnMouseClicked(e -> apply(PriorityLevel.MEDIUM));
            lowBar.setOnMouseClicked(e -> apply(PriorityLevel.LOW));
        }

        private Region bar(String styleClass) {
            Region r = new Region();
            r.getStyleClass().addAll("priority-bar", styleClass);
            r.setPrefSize(32, 12);
            r.setMinSize(32, 12);
            return r;
        }

        private void apply(PriorityLevel level) {
            Task task = getTableRow() != null ? getTableRow().getItem() : null;
            if (task != null) {
                task.setPriority(level);
            }
        }

        @Override
        protected void updateItem(PriorityLevel item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
                return;
            }
            highBar.getStyleClass().remove("selected");
            mediumBar.getStyleClass().remove("selected");
            lowBar.getStyleClass().remove("selected");
            if (item != null) {
                switch (item) {
                    case HIGH -> highBar.getStyleClass().add("selected");
                    case MEDIUM -> mediumBar.getStyleClass().add("selected");
                    case LOW -> lowBar.getStyleClass().add("selected");
                }
            }
            setGraphic(bars);
        }
    }

    /** Status cell: dropdown rendered as a coloured tag. */
    private static class StatusCell extends TableCell<Task, TaskStatus> {
        private final ComboBox<TaskStatus> comboBox =
                new ComboBox<>(FXCollections.observableArrayList(TaskStatus.values()));

        StatusCell() {
            comboBox.getStyleClass().add("status-combo");
            comboBox.setCellFactory(lv -> new StatusListCell());
            comboBox.setButtonCell(new StatusListCell());
            comboBox.setMaxWidth(Double.MAX_VALUE);
            comboBox.setOnAction(e -> {
                Task task = getTableRow() != null ? getTableRow().getItem() : null;
                if (task != null && comboBox.getValue() != null) {
                    task.setStatus(comboBox.getValue());
                }
            });
        }

        @Override
        protected void updateItem(TaskStatus item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
            } else {
                comboBox.setValue(item);
                setGraphic(comboBox);
            }
        }
    }

    /** Renders a TaskStatus as a small coloured pill. */
    private static class StatusListCell extends ListCell<TaskStatus> {
        @Override
        protected void updateItem(TaskStatus item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setGraphic(null);
                setText(null);
            } else {
                Label tag = new Label(item.getDisplayName());
                tag.getStyleClass().add("status-tag");
                tag.getStyleClass().add(switch (item) {
                    case NOT_STARTED -> "status-not-started";
                    case IN_PROGRESS -> "status-in-progress";
                    case DONE -> "status-done";
                });
                setGraphic(tag);
                setText(null);
            }
        }
    }

    /** Project cell: editable dropdown with free-text categories. */
    private static class ProjectCell extends TableCell<Task, String> {
        private final ComboBox<String> comboBox = new ComboBox<>(FXCollections.observableArrayList(
                "Programming", "Consulting", "Design", "Marketing", "Research"));

        ProjectCell() {
            comboBox.setEditable(true);
            comboBox.getStyleClass().add("project-combo");
            comboBox.setMaxWidth(Double.MAX_VALUE);
            comboBox.setOnAction(e -> commit(comboBox.getValue()));
            comboBox.getEditor().focusedProperty().addListener((obs, was, isFocused) -> {
                if (!isFocused) commit(comboBox.getEditor().getText());
            });
        }

        private void commit(String value) {
            if (value == null) return;
            Task task = getTableRow() != null ? getTableRow().getItem() : null;
            if (task != null) {
                task.setProject(value);
                if (!value.isBlank() && !comboBox.getItems().contains(value)) {
                    comboBox.getItems().add(value);
                }
            }
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
            } else {
                if (item != null && !item.isBlank() && !comboBox.getItems().contains(item)) {
                    comboBox.getItems().add(item);
                }
                comboBox.setValue(item);
                setGraphic(comboBox);
            }
        }
    }
}
package com.tasktitan.gui.controllers;
import model.Task;
import model.Priority;
import model.TaskStatus;
import model.TaskType;
import model.TeamMember;
import java.time.LocalDate;



import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.StackPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;
import javafx.scene.Scene;
import database.TaskDAO;
import java.sql.SQLException;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.ComboBoxTableCell;


import java.io.IOException;
import java.util.Objects;

public class DashboardController {

    @FXML
    private StackPane contentPane;
    private javafx.scene.Node dashboardContent;

    @FXML
    private StackPane rootStack;

    @FXML
    private ComboBox<String> statusFilter;

    @FXML
    private ComboBox<String> priorityFilter;

    @FXML
    private TableView<Task> taskTable;

    @FXML
    private TableColumn<Task, String> taskColumn;

    @FXML
    private TableColumn<Task, TaskType> typeColumn;

    @FXML
    private TableColumn<Task, Priority> priorityColumn;

    @FXML
    private TableColumn<Task, TeamMember> assignedColumn;

    @FXML
    private TableColumn<Task, LocalDate> deadlineColumn;

    @FXML
    private TableColumn<Task, TaskStatus> statusColumn;



    @FXML
    public void initialize() {



        taskColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("taskType"));
        priorityColumn.setCellValueFactory(new PropertyValueFactory<>("priority"));
        assignedColumn.setCellValueFactory(new PropertyValueFactory<>("assignedMember"));
        deadlineColumn.setCellValueFactory(new PropertyValueFactory<>("deadline"));
        statusColumn.setCellFactory(
                ComboBoxTableCell.forTableColumn(
                        FXCollections.observableArrayList(
                                TaskStatus.TODO,
                                TaskStatus.IN_PROGRESS,
                                TaskStatus.COMPLETED
                        )
                )
        );
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        statusColumn.setOnEditCommit(event -> {

            Task task = event.getRowValue();
            TaskStatus newStatus = event.getNewValue();

            if (task.getStatus() == TaskStatus.TODO &&
                    newStatus == TaskStatus.COMPLETED) {

                System.out.println("You must choose In Progress first.");

                refreshTable();

                return;
            }

            task.SetStatus(newStatus);

            try {
                TaskDAO.updateStatus(task.getID(), newStatus);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        });

        taskTable.setEditable(true);

        refreshTable();

        statusFilter.getItems().addAll(
                "All Status",
                "To Do",
                "In Progress",
                "Completed"
        );

        priorityFilter.getItems().addAll(
                "All Priority",
                "Low",
                "Medium",
                "High"
        );



        statusFilter.setPromptText("Status");
        priorityFilter.setPromptText("Priority");

        dashboardContent = contentPane.getChildren().get(0);


    }

    //new
    public void refreshTable() {
        try {
            //
            System.out.println(TaskDAO.loadTasks().size());
            taskTable.getItems().setAll(TaskDAO.loadTasks());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteTask() {

        Task selectedTask = taskTable.getSelectionModel().getSelectedItem();

        if (selectedTask == null) {
            System.out.println("No task selected");
            return;
        }

        try {
            TaskDAO.deleteTask(selectedTask.getID());
            System.out.println("Task deleted");

            refreshTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void openAddTask() throws IOException {

        FXMLLoader loader = new FXMLLoader(
                Objects.requireNonNull(
                        getClass().getResource("/com.tasktitan.gui/fxml/AddTask.fxml")
                )
        );

        Parent addTaskView = loader.load();

        AddTaskController controller = loader.getController();
        controller.setDashboardController(this);

        StackPane overlay = new StackPane();
        overlay.setStyle("-fx-background-color: rgba(0,0,0,0.5);");

        StackPane.setAlignment(addTaskView, Pos.CENTER);
        overlay.getChildren().add(addTaskView);

        rootStack.getChildren().add(overlay);
    }

    @FXML
    private void openTaskDetails() throws IOException {

        Task selectedTask = taskTable.getSelectionModel().getSelectedItem();

        if (selectedTask == null) {
            System.out.println("Please select a task.");
            return;
        }

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com.tasktitan.gui/fxml/taskdetails.fxml")
        );

        Parent root = loader.load();

        TaskDetailsController controller = loader.getController();
        controller.setTask(selectedTask);

        Stage stage = new Stage();
        stage.setTitle("Task Details");
        stage.setScene(new Scene(root));
        stage.show();
    }


    @FXML
    private void openTeamMembers() {

        try {

            Parent membersView = FXMLLoader.load(
                    Objects.requireNonNull(
                            getClass().getResource(
                                    "/com.tasktitan.gui/fxml/members.fxml"
                            )
                    )
            );

            contentPane.getChildren().setAll(
                    membersView
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openStatistics() {

        try {

            Parent statisticsView = FXMLLoader.load(
                    Objects.requireNonNull(
                            getClass().getResource(
                                    "/com.tasktitan.gui/fxml/statistics.fxml"
                            )
                    )
            );

            contentPane.getChildren().setAll(
                    statisticsView
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openDashboard() {

        contentPane.getChildren().setAll(
                dashboardContent
        );
    }


}

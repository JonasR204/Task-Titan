package com.tasktitan.gui.controllers;

import database.TaskDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Task;

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
    private TableColumn<Task, String> typeColumn;

    @FXML
    private TableColumn<Task, String>priorityColumn;

    @FXML
    private TableColumn<Task, String> assignedColumn;

    @FXML
    private TableColumn<Task, String> deadlineColumn;

    @FXML
    private TableColumn<Task, String> statusColumn;




    @FXML
    public void initialize() {

        taskColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("taskType"));
        priorityColumn.setCellValueFactory(new PropertyValueFactory<>("priority"));
        assignedColumn.setCellValueFactory(new PropertyValueFactory<>("assignedMember"));
        deadlineColumn.setCellValueFactory(new PropertyValueFactory<>("deadline"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));


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

        refreshTable();
    }


    public void refreshTable() {
        try {
            taskTable.getItems().setAll(TaskDAO.loadTasks());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @FXML
    private void openAddTask() throws IOException {

        FXMLLoader loader = new FXMLLoader();
               loader.setLocation(Objects.requireNonNull(
                        getClass().getResource("/com.tasktitan.gui/fxml/AddTask.fxml")));

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






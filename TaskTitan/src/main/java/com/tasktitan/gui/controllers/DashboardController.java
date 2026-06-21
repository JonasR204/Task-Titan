package com.tasktitan.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.StackPane;


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
    public void initialize() {

        statusFilter.getItems().addAll(
                "All Status",
                "To-Do",
                "In Progress",
                "Done"
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

    @FXML
    private void openAddTask() throws IOException {

        Parent addTaskView = FXMLLoader.load(
                Objects.requireNonNull(
                        getClass().getResource("/com.tasktitan.gui/fxml/AddTask.fxml")
                )
        );

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
                                    "/com.tasktitan.gui/fxml/Members.fxml"
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
    private void openDashboard() {

        contentPane.getChildren().setAll(
                dashboardContent
        );
    }
}






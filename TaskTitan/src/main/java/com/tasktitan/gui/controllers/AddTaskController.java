package com.tasktitan.gui.controllers;

import database.Database;
import model.TaskType;
import model.Priority;
import model.TaskStatus;
import model.TeamMember;
import service.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.DatePicker;

import java.sql.Connection;
import java.time.LocalDate;

public class AddTaskController {

    @FXML
    private AnchorPane root;

    @FXML
    private TextField titleField;

    @FXML
    private TextArea descriptionField;

    @FXML
    private ComboBox<TaskType> taskTypeComboBox;

    @FXML
    private VBox featureTaskBox;

    @FXML
    private VBox bugTaskBox;

    @FXML
    private TextArea technicalSpecField;

    @FXML
    private TextField codePartField;

    @FXML
    private TextField errorMessageField;

    @FXML
    private TextArea fixPropositionField;

    @FXML
    private DatePicker deadlinePicker;

    @FXML
    private ComboBox<Priority> priorityComboBox;

    @FXML
    private ComboBox<TaskStatus> statusComboBox;

    @FXML
    private ComboBox<TeamMember> assignedNameField;

    // still not used
    //private GUI gui = new GUI();


    @FXML
    public void initialize() {

        // Initialize task type options
        taskTypeComboBox.getItems().addAll(
                TaskType.values()
        );

        // Hide type-specific fields until a task type is selected
        featureTaskBox.setVisible(false);
        featureTaskBox.setManaged(false);

        bugTaskBox.setVisible(false);
        bugTaskBox.setManaged(false);

        taskTypeComboBox.setOnAction(
                e -> updateTaskType()
        );

        priorityComboBox.getItems().addAll(
                Priority.values()
        );

        statusComboBox.getItems().addAll(
                TaskStatus.values()
        );
    }
    // Show different form sections depending on the selected task type
    private void updateTaskType() {

        TaskType selected =
                taskTypeComboBox.getValue();

        if (selected == TaskType.FEATURE) {

            featureTaskBox.setVisible(true);
            featureTaskBox.setManaged(true);

            bugTaskBox.setVisible(false);
            bugTaskBox.setManaged(false);

        }
        else if (selected == TaskType.BUG) {

            bugTaskBox.setVisible(true);
            bugTaskBox.setManaged(true);

            featureTaskBox.setVisible(false);
            featureTaskBox.setManaged(false);
        }
    }


    @FXML
    private void closeForm() {

        if (root.getParent() != null &&
                root.getParent().getParent() instanceof StackPane rootStack) {

            rootStack.getChildren().remove(root.getParent());
        }
    }

    @FXML
    private void createTask() {

        System.out.println("Create button clicked!");

        String title = titleField.getText();

        String description =
                descriptionField.getText();

        Priority priority =
                priorityComboBox.getValue();

        TaskStatus status =
                statusComboBox.getValue();

        // still not used
        LocalDate deadline =
                deadlinePicker.getValue();
        if (deadline == null) {
            System.out.println("Please select a deadline.");
            return;
        }
        int[] deadlineArray = {
                deadline.getDayOfMonth(),
                deadline.getMonthValue(),
                deadline.getYear()
        };

        TeamMember assignedMember =
                assignedNameField.getValue();

        System.out.println(title);
        System.out.println(description);
        System.out.println(priority);
        System.out.println(status);
        System.out.println(deadline);


    }
}

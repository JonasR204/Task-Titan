package com.tasktitan.gui.controllers;

import database.TaskDAO;
import model.TeamMember;
import model.TaskType;
import model.Priority;
import model.TaskStatus;
import java.sql.SQLException;
import database.MemberDAO;



import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.DatePicker;

import java.sql.SQLException;
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
    private ComboBox<TeamMember> assignedNameField;

    private DashboardController dashboardController;

    public void setDashboardController(DashboardController dashboardController){
        this.dashboardController = dashboardController;
    }


    /**
     * Sets up the task form when it is opened.
     */
    @FXML
    public void initialize() {

        // Initialize task type options
        taskTypeComboBox.getItems().addAll(TaskType.values()
        );



        // Hide type-specific fields until a task type is selected
        featureTaskBox.setVisible(false);
        featureTaskBox.setManaged(false);

        bugTaskBox.setVisible(false);
        bugTaskBox.setManaged(false);

        taskTypeComboBox.setOnAction(
                e -> updateTaskType()
        );

        // priority
        priorityComboBox.getItems().addAll(
                Priority.LOW,
                Priority.MEDIUM,
                Priority.HIGH,
                Priority.URGENT
        );

        try { assignedNameField.getItems().setAll(MemberDAO.loadMembers());

        } catch (SQLException exception){
            exception.printStackTrace();
        }


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

    /**
     * Closes the add task page.
     */
    @FXML
    private void closeForm() {

        if (root.getParent() != null &&
                root.getParent().getParent() instanceof StackPane rootStack) {

            rootStack.getChildren().remove(root.getParent());
        }
    }




    //create button
    @FXML
    private void AddTask() throws SQLException {

        String title = titleField.getText();
        String description = descriptionField.getText();
        Priority priority = priorityComboBox.getValue();
        TaskStatus status = TaskStatus.TODO;
        TaskType task_type = taskTypeComboBox.getValue();
        LocalDate deadline = deadlinePicker.getValue();
        TeamMember assigned_member = assignedNameField.getValue();


        //validation
        if (title.isBlank() ||
                priority == null ||
                taskTypeComboBox.getValue() == null ||
                assignedNameField.getValue() == null) {

            System.out.println("All fields are required");
            return;
        }



      try{
        if (taskTypeComboBox.getValue() == TaskType.FEATURE) {

            TaskDAO.addTask(
                    task_type,
                    title,
                    description,
                    priority,
                    status,
                    deadline,
                    assigned_member != null ? assigned_member.toString() : null,
                    technicalSpecField.getText(),
                    null,
                    null,
                    null
            );
            // Log message
            System.out.println("New Feature task created: " + title);
        }
        else if (taskTypeComboBox.getValue() == TaskType.BUG) {

            TaskDAO.addTask(
                    task_type,
                    title,
                    description,
                    priority,
                    status,
                    deadline,
                    assigned_member != null ? assigned_member.toString() : null,
                    null,
                    codePartField.getText(),
                    errorMessageField.getText(),
                    fixPropositionField.getText()
            );

            // Log message
            System.out.println("New Bug task created: " + title);
        }

        if(dashboardController != null){
            dashboardController.refreshTable();;
        }

        closeForm();

    } catch (SQLException exception){
          exception.printStackTrace();
          System.out.println("Datenbankfehler: " + exception.getMessage());
      }

      }
}

package com.tasktitan.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import model.Task;
import model.BugTask;
import model.FeatureTask;
import model.TaskType;


public class TaskDetailsController {

    @FXML
    private VBox bugBox;

    @FXML
    private VBox featureBox;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private TextArea codePartArea;

    @FXML
    private TextArea errorMessageArea;

    @FXML
    private TextArea fixPropositionArea;

    @FXML
    private TextArea technicalSpecificationArea;

    public void setTask(Task task) {

        descriptionArea.setText(task.getDescription());
        System.out.println("details button clicked");

        if (task.getTaskType() == TaskType.BUG) {

            BugTask bug = (BugTask) task;

            bugBox.setVisible(true);
            bugBox.setManaged(true);


            featureBox.setVisible(false);
            featureBox.setManaged(false);


            codePartArea.setText(bug.getCodePart());
            errorMessageArea.setText(bug.getErrorMessage());
            fixPropositionArea.setText(bug.getFixProposition());

        } else {

            FeatureTask feature = (FeatureTask) task;

            featureBox.setVisible(true);
            featureBox.setManaged(true);

            bugBox.setVisible(false);
            bugBox.setManaged(false);

            featureBox.setLayoutY(192);

            technicalSpecificationArea.setText(feature.getTechnicalSpecification());
        }
    }


}

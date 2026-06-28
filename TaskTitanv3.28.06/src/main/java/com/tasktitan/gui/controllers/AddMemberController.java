package com.tasktitan.gui.controllers;

import database.MemberDAO;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.control.TextField;

import java.sql.SQLException;


public class AddMemberController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField roleField;

    @FXML
    private AnchorPane root;


    private MembersController membersController;


    /**
     * Used to refresh the members table after adding a member.
     */
    public void setMembersController(MembersController membersController) {
        this.membersController = membersController;
    }






    @FXML
    private void AddMember() {
        String name = nameField.getText();
        String email = emailField.getText();
        String role = roleField.getText();

        //validation
        if(name.isBlank() || email.isBlank() || role.isBlank()) {
            System.out.println("All fields are required");
            return;
        }

       // Create a new team member
       try {
           MemberDAO.addMember(name, email, role);
           System.out.println("Member Added");

           membersController.refreshTable();
           closeForm();

       } catch (SQLException exception){
           exception.printStackTrace();
       }
    }

    @FXML
    private void closeForm() {

        if (root.getParent() != null &&
                root.getParent().getParent() instanceof StackPane rootStack) {

            rootStack.getChildren().remove(root.getParent());
        }
    }
}


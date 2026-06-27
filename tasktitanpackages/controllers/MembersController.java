package com.tasktitan.gui.controllers;

import database.MemberDAO;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import model.TeamMember;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;

public class MembersController {



    @FXML
    private TableView<TeamMember> membersTable;

    @FXML
    private TableColumn<TeamMember, String> nameColumn;

    @FXML
    private TableColumn<TeamMember, String> emailColumn;

    @FXML
    private TableColumn<TeamMember, String> roleColumn;

    @FXML
    private AnchorPane root;


    /**
     * Sets up the members table when the page is loaded.
     */
    @FXML
    public void initialize() {

        nameColumn.setCellValueFactory(
                new PropertyValueFactory<>("name"));

        emailColumn.setCellValueFactory(
                new PropertyValueFactory<>("email"));

        roleColumn.setCellValueFactory(
                new PropertyValueFactory<>("role"));

        refreshTable();
    }

    /**
     * Refreshes the table with the current members.
     */
    public void refreshTable() {
        try{
            membersTable.getItems().setAll(MemberDAO.loadMembers());
        } catch (SQLException exception){
            exception.printStackTrace();
        }
    }


    @FXML
    private void openAddMember() {
        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com.tasktitan.gui/fxml/AddMember.fxml")
            );

            Parent addMemberForm = loader.load();

            AddMemberController controller = loader.getController();
            controller.setMembersController(this);

            StackPane rootStack =
                    (StackPane) root.getScene().getRoot();

            rootStack.getChildren().add(addMemberForm);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void deleteMember() {

        TeamMember selectedMember = membersTable.getSelectionModel().getSelectedItem();

        if (selectedMember == null) {
            System.out.println("No member selected");
            return;
        }
        try {
            MemberDAO.deleteMember(selectedMember.GetID());
            System.out.println("Member deleted");

            refreshTable();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        }




}

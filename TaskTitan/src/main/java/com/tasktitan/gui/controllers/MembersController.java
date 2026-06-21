package com.tasktitan.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import model.TeamMember;

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

    @FXML
    private void openAddMember() {

        System.out.println("Add Member");
    }

    @FXML
    private void deleteMember() {

        System.out.println("Delete Member");
    }


}
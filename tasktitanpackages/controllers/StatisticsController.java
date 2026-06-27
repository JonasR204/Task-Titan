package com.tasktitan.gui.controllers;

import java.util.Arrays;
import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.BarChart;
import java.sql.*;
import database.TaskDAO;
import database.MemberDAO;
import database.Database;
import model.Task;
import model.TeamMember;
import javafx.scene.control.Label;

public class StatisticsController {

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private Label totalTasksLabel;

    @FXML
    private Label teamMembersLabel;

    @FXML
    public void initialize() {
        loadStatistics();
    }


    private void loadStatistics() {

        try {
            List<TeamMember> members = MemberDAO.loadMembers();
            List<Task> tasks = TaskDAO.loadTasks();

            totalTasksLabel.setText(String.valueOf(tasks.size()));
            teamMembersLabel.setText(String.valueOf(members.size()));


            XYChart.Series<String, Number> dataseries = new XYChart.Series<>();
            dataseries.setName("Statistics");

            for (TeamMember member : members) {

                int taskCount = 0;

                for(Task task : tasks){

                    if(task.getAssignedMember() != null){

                        String taskMemberName = task.getAssignedMember().GetName();
                        String currentMemberName = member.GetName();

                        if(taskMemberName.equals(currentMemberName)){

                            taskCount++;
                        }
                    }
                }

                String memberName= member.GetName();
                XYChart.Data<String, Number> data= new XYChart.Data<>(memberName, taskCount);
                dataseries.getData().add(data);
            }

            barChart.getData().add(dataseries);

        } catch (SQLException exception){
            exception.printStackTrace();
        }



    }








}

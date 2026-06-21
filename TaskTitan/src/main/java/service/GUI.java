package service;

import java.util.List;
import java.time.*;
import java.util.Map;
import java.util.HashMap;
import java.time.LocalDateTime;
import java.util.ArrayList;
import model.*;
import database.TaskDAO;
import model.Task;
import model.BugTask;
import model.FeatureTask;
import model.TeamMember;



public class GUI {

    //Task
    private List<Task> tasks;
    private int nextTaskId;

    //Date
    private int[] currentDate = new int[3];
    private LocalDateTime realDate = LocalDateTime.now();
    private Map<Priority, Integer> urgencyParameter = new HashMap<>();

    //Project
    private int currentProjectId;
    //private List<...> availableProjectIds

    //Colors
    //private Color[] priorityColors;

    //Team Members
    private List<TeamMember> teamMembers;
    private int nextTeamMemberId;

    //Roles
    private List<String> roles;

    //Constructor for TaskDAO
    public GUI() {

        tasks = new ArrayList<>();
        teamMembers = new ArrayList<>();
        roles = new ArrayList<>();

        nextTaskId = 0;
        nextTeamMemberId = 0;

        try {

            tasks = TaskDAO.loadTasks();

            for(Task t : tasks){

                if(t.GetID() > nextTaskId){
                    nextTaskId = t.GetID();
                }
            }

        } catch(Exception e){

            e.printStackTrace();
        }
    }

    //##################################################################################################################
    //Tasks Management
    public void AddTask(String title, String description, Priority priority,
                               TaskStatus status, int[] deadline, TeamMember assignedMember,
                               String technicalSpecification){
        nextTaskId++;
        FeatureTask newTask = new FeatureTask(nextTaskId, title, description, priority, status,
                                              deadline, assignedMember, technicalSpecification);
        tasks.add(newTask);
        try {
            TaskDAO.saveTask(newTask);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void AddTask(String title, String description, Priority priority,
                               TaskStatus status, int[] deadline, TeamMember assignedMember,
                               String codePart, String errorMessage, String fixProposition){
        nextTaskId++;
        BugTask newTask = new BugTask(nextTaskId, title, description, priority, status,
                deadline, assignedMember, codePart, errorMessage, fixProposition);
        tasks.add(newTask);
        try {
            TaskDAO.saveTask(newTask);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void RemoveTask(int id){
        for(int i = 0; i < tasks.size(); i++){
            if(tasks.get(i).GetID() == id){
                tasks.remove(tasks.get(i));
                return;
            }
        }
        System.out.println("This Tasks doesn't exist");
    }


    //##################################################################################################################
    //Deadline / Current Date
    public void SetIRLTime(){
        currentDate[0] = realDate.getDayOfMonth();
        currentDate[1] = realDate.getMonthValue();
        currentDate[2] = realDate.getYear();
    }

    public void SetManualTime(int day, int month, int year){
        currentDate[0] = realDate.getDayOfMonth();
        currentDate[1] = realDate.getMonthValue();
        currentDate[2] = realDate.getYear();
    }

    public Priority GetUrgency(int taskID){

        return Priority.HIGH;
    }

    public int DaysTillDeadline(int[] deadline){
        int daysInDeadline = 0;
        int daysInCurrentTime = 0;

        //for(int i = 0; i <= )
        return 0;
    }

    public void DefaultUrgencyLevel(){
        urgencyParameter.put(Priority.LOW, 14);
        urgencyParameter.put(Priority.MEDIUM, 7);
        urgencyParameter.put(Priority.HIGH, 3);
        urgencyParameter.put(Priority.URGENT, 1);
    }

    public void ChangeUrgencyLevel(Priority level, int triggerTime){
        if(urgencyParameter.containsKey(level)) {
            urgencyParameter.put(level, triggerTime);
        }
    }

    //##################################################################################################################
    //Team Member Management
    public void AddTeamMember(String name, String email, String role){
        for(int i = 0; i < teamMembers.size(); i++){
            if(teamMembers.get(i).GetEmail().equals(email)){
                System.out.println("This Team Member already has an account!");
                return;
            }
        }
        TeamMember newTM = new TeamMember(nextTeamMemberId, name, email, role);
        teamMembers.add (newTM);
        nextTeamMemberId++;
    }

    public void RemoveTeamMember(int id){
        for(int i = 0; i < teamMembers.size(); i++){
            if(teamMembers.get(i).GetID() == id){
                teamMembers.remove(teamMembers.get(i));
                return;
            }
        }
        System.out.println("Team Member with id: " + id + " doesn't Exist!");
    }


    //##################################################################################################################
    //Roles Management
    public void AddRole(String role){
        if(!roles.contains(role)) {
            roles.add(role);
        }
    }

    public void RemoveRole(String role){
        if(roles.contains(role)) {
            roles.remove(role);
        }
    }

    public static void main(String[] args) {
        GUI g = new GUI();
        g.SetIRLTime();
        System.out.println(g.currentDate[0] + "-" + g.currentDate[1] + "-" + g.currentDate[2]);

    }
}


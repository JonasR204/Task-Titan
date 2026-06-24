import java.util.List;
import java.time.*;
import java.util.Map;
import java.util.HashMap;
import java.time.LocalDateTime;


enum Priority {LOW, MEDIUM, HIGH, URGENT, PAST_DEADLINE}
enum TaskStatus {TODO, IN_PROGRESS, COMPLETED}
enum TaskType {FEATURE, BUG}

public class GUI {

    //Task
    private List<Task> tasks;
    private int nextTaskId;

    //Date
    private LocalDate currentDate;
    private LocalDate realDate = LocalDate.now();
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

    //Filter



    //##################################################################################################################
    //Initialising
    public GUI (){
        SetDefaultUrgencyLevel();
        SetIRLTime();
    }

    //##################################################################################################################
    //Tasks Management
    public void AddTask(String title, String description, Priority priority,
                               TaskStatus status, LocalDate deadline, TeamMember assignedMember,
                               String technicalSpecification){
        nextTaskId++;
        FeatureTask newTask = new FeatureTask(nextTaskId, title, description, priority, status,
                                              deadline, assignedMember, technicalSpecification);
        tasks.add(newTask);
    }

    public void AddTask(String title, String description, Priority priority,
                               TaskStatus status, LocalDate deadline, TeamMember assignedMember,
                               String codePart, String errorMessage, String fixProposition){
        nextTaskId++;
        BugTask newTask = new BugTask(nextTaskId, title, description, priority, status,
                deadline, assignedMember, codePart, errorMessage, fixProposition);
        tasks.add(newTask);
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

    public Task GetTask(int id){
        for(int i = 0; i < tasks.size(); i++){
            if(tasks.get(i).GetID() == id){
                return tasks.get(i);
            }
        }
        return null;
    }

    public void EditTaskDeadline(int id, LocalDate newDeadline){
        GetTask(id).SetDeadline(newDeadline);
        UpdateTaskUrgency(id);
    }

    //##################################################################################################################
    //Deadline / Current Date / Urgency
    public void SetIRLTime(){
        currentDate = realDate;
        UpdateAllTasksUrgency();
    }

    public void SetManualTime(LocalDate newDate){
        currentDate = newDate;
        UpdateAllTasksUrgency();
    }

    public void UpdateAllTasksUrgency(){
        if(tasks != null) {
            for (int i = 0; i < tasks.size(); i++) {
                Task currentTask = tasks.get(i);
                LocalDate deadline = currentTask.GetDeadline();
                int remainingTime = Utility.GetDaysBetweenDates(currentDate, deadline);

                if (remainingTime >= urgencyParameter.get(Priority.LOW)) {
                    currentTask.SetPriority(Priority.LOW);
                } else if (remainingTime >= urgencyParameter.get(Priority.MEDIUM)) {
                    currentTask.SetPriority(Priority.MEDIUM);
                } else if (remainingTime >= urgencyParameter.get(Priority.HIGH)) {
                    currentTask.SetPriority(Priority.HIGH);
                } else if (remainingTime >= urgencyParameter.get(Priority.URGENT)) {
                    currentTask.SetPriority(Priority.URGENT);
                } else {
                    currentTask.SetPriority(Priority.PAST_DEADLINE);
                }
            }
        }
    }

    public void UpdateTaskUrgency(int taskID){
        Task currentTask = GetTask(taskID);
        if(currentTask != null) {
            LocalDate deadline = currentTask.GetDeadline();
            int remainingTime = Utility.GetDaysBetweenDates(currentDate, deadline);

            if (remainingTime >= urgencyParameter.get(Priority.LOW)) {
                currentTask.SetPriority(Priority.LOW);
            } else if (remainingTime >= urgencyParameter.get(Priority.MEDIUM)) {
                currentTask.SetPriority(Priority.MEDIUM);
            } else if (remainingTime >= urgencyParameter.get(Priority.HIGH)) {
                currentTask.SetPriority(Priority.HIGH);
            } else if (remainingTime >= urgencyParameter.get(Priority.URGENT)) {
                currentTask.SetPriority(Priority.URGENT);
            }else{
                currentTask.SetPriority(Priority.PAST_DEADLINE);
            }
        }
    }

    public void SetDefaultUrgencyLevel(){
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

    public TeamMember GetTeamMember(int id){
        for(int i = 0; i < teamMembers.size(); i++){
            if(teamMembers.get(i).GetID() == id){
                return teamMembers.get(i);
            }
        }
        return null;
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
        LocalDate lc = LocalDate.of(2026, 6, 30);

        System.out.println(Utility.GetDaysBetweenDates(g.currentDate, lc));
    }
}


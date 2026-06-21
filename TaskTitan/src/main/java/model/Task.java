package model;

public class Task {

    protected int id;
    protected String title;
    protected String description;
    protected Priority priority;
    protected TaskStatus status;
    protected int[] deadline = new int [3];
    protected TeamMember assignedMember;
    protected TaskType taskType;


    public Task(int id, String title,String description, Priority priority,
                TaskStatus status, int[]deadline, TeamMember assignedMEmber){
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = status;
        if(Utility.CheckDateForValidity(deadline)) {
            Utility.CopyArray(this.deadline, deadline);
        }
        this.assignedMember = assignedMEmber;
    }

    public int GetID(){ return id;}

    public String GetTitle() {return title;}
    public void SetTitle(String title) {this.title = title;}

    public String GetDescription(){return description;}
    public void SetDescription(String description) {this.description = description;}

    public Priority GetPriority() {return priority;}
    public void SetPriority(Priority priority) {this.priority = priority;}

    public TaskStatus GetStatus() {return status;}
    public void SetStatus(TaskStatus status) {this.status = status;}

    public int[] GetDeadline() {return deadline;}
    public void SetDeadline(int[] deadline) {
        if(Utility.CheckDateForValidity(deadline)) {
            Utility.CopyArray(this.deadline, deadline);
        }
    }

    public TeamMember GetAssignedMember() {return assignedMember;}
    public void SetAssignedMember(TeamMember assignedMember) {this.assignedMember = assignedMember;}

    public TaskType GetTaskType() {return taskType;}
}

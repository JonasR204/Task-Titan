import java.time.LocalDate;

public abstract class Task {

    protected int id;
    protected String title;
    protected String description;
    protected Priority priority;
    protected TaskStatus status;
    protected LocalDate deadline;
    protected TeamMember assignedMember;
    protected TaskType taskType;

    public boolean filterParameter = true;


    public Task(int id, String title,String description, Priority priority,
                TaskStatus status, LocalDate deadline, TeamMember assignedMEmber){
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.deadline = deadline;
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

    public LocalDate GetDeadline() {return deadline;}
    public void SetDeadline(LocalDate deadline) {this.deadline = deadline;}

    public TeamMember GetAssignedMember() {return assignedMember;}
    public void SetAssignedMember(TeamMember assignedMember) {this.assignedMember = assignedMember;}

    public TaskType GetTaskType() {return taskType;}
}

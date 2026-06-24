import java.time.LocalDate;

public class BugTask extends Task{
    private String codePart;
    private String errorMessage;
    private String fixProposition;


    BugTask(int id, String title,String description, Priority priority,
                TaskStatus status, LocalDate deadline, TeamMember assignedMEmber,
                String codePart, String errorMessage, String fixProposition){
        super(id, title, description, priority, status, deadline, assignedMEmber);

        taskType = TaskType.BUG;
        this.codePart = codePart;
        this.errorMessage = errorMessage;
        this.fixProposition = fixProposition;
    }

    public String GetCodePart() {return codePart;}
    public void SetCodePart(String codePart) {this.codePart = codePart;}

    public String GetErrorMessage() {return errorMessage;}
    public void SetErrorMessage(String errorMessage) {this.errorMessage = errorMessage;}

    public String GetFixProposition() {return fixProposition;}
    public void SetFixProposition(String fixProposition) {this.fixProposition = fixProposition;}
}

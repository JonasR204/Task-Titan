package model;

public class FeatureTask extends Task{
    private String technicalSpecification;

    public FeatureTask(int id, String title,String description, Priority priority,
                TaskStatus status, int[]deadline, TeamMember assignedMEmber, String technicalSpecification){
        super(id, title, description, priority, status, deadline, assignedMEmber);

        taskType = TaskType.FEATURE;
        this.technicalSpecification = technicalSpecification;
    }

    public String GetTechnicalSpecification() {return technicalSpecification;}
    public void SetTechnicalSpecification(String tS) {this.technicalSpecification = tS;}
}

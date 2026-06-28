package model;

import model.Priority;
import model.TaskStatus;
import model.TaskType;
import java.time.LocalDate;

public class FeatureTask extends Task {
    private String technicalSpecification;

    public FeatureTask(int id, String title, String description, Priority priority,
                TaskStatus status, LocalDate deadline, TeamMember assignedMEmber, String technicalSpecification){
        super(id, title, description, priority, status, deadline, assignedMEmber);

        taskType = TaskType.FEATURE;
        this.technicalSpecification = technicalSpecification;
    }

    public String getTechnicalSpecification() {return technicalSpecification;}
    public void SetTechnicalSpecification(String tS) {this.technicalSpecification = tS;}
}

package model;

public enum TaskStatus {
    TODO("To-Do"),
    IN_PROGRESS("In Progress"),
    COMPLETED("Completed");


    private final String displayName2;

    TaskStatus(String displayName2){
        this.displayName2 = displayName2;
    }


    @Override
    public String toString(){
        return displayName2;
    }
}

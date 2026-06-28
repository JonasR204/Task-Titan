package model;

public enum Priority {
    LOW("Low"),
    MEDIUM("Medium"),
    HIGH("High"),
    URGENT("Urgent"),
    PAST_DEADLINE("Past-Deadline");

    private final String displayName;

    Priority(String displayName){
        this.displayName = displayName;
    }


    @Override
    public String toString(){
        return displayName;
    }
}

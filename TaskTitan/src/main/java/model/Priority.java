package model;

public enum Priority {
    LOW("Low"),
    MEDIUM("Medium"),
    HIGH("High"),
    URGENT("Urgent");

    private final String displayName2;

    Priority(String displayName2){
    this.displayName2 = displayName2;
    }

    @Override
    public String toString(){
        return displayName2;
    }


}

package model;

public enum TaskType {
    FEATURE("Feature"),
    BUG("Bug");


    private final String displayName3;

    TaskType(String displayName3){
        this.displayName3 = displayName3;
    }


    @Override
    public String toString(){
        return displayName3;
    }
}

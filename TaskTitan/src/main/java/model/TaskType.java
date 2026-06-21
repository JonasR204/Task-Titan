package model;

public enum TaskType {
    FEATURE("Feature"),
    BUG("Bug");

    private final String displayname3;


    TaskType(String displayname3){
        this.displayname3 = displayname3;
    }

    @Override
    public String toString(){
        return displayname3;
    }
}

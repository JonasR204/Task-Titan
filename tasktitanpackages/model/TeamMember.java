package model;

public class TeamMember {

    private int id;
    private String name;
    private String email;
    private String role;


    public TeamMember(int id, String name, String email, String role){
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public int GetID() {return id;}

    public String GetName() {return name;}
    public void SetName(String name) {this.name = name;}

    public String GetEmail() {return email;}
    public void SetEmail(String email) {this.email = email;}

    public String GetRole() {return role;}
    public void SetRole(String role) {this.role = role;}

    //Moe
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    //used for the add task assigned member
    @Override
    public String toString() {
        return getName();
    }
}

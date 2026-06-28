package database;

import model.Task;
import model.TeamMember;

import java.sql.*;
import java.util.ArrayList;

public class MemberDAO {
    public static void addMember(String name, String email, String role) throws SQLException {

        String sql = "INSERT INTO members(name,email,role) VALUES (?,?,?)";


        try (
                Connection con = Database.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);

            ps.setString(2, email);

            ps.setString(3, role);

            ps.executeUpdate();
        }
    }

    public static ArrayList<TeamMember> loadMembers() throws SQLException {
        ArrayList<TeamMember> members = new ArrayList<>();
        String sql = "SELECT * FROM members";

        try (Connection con = Database.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                TeamMember tm = new TeamMember(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("role")
                );
                members.add(tm);
            }
        }
        return members;
    }

    public static void deleteMember(int id) throws SQLException {
        String sql = "DELETE FROM members WHERE id = ?";

        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}


package database;

import model.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;


public class TaskDAO {



    public static void addTask(TaskType task_type, String title, String description, Priority priority,
                               TaskStatus status, LocalDate deadline, String assigned_member, String technical_specification, String code_part, String error_message, String fix_proposition) throws SQLException {
        String sql = "INSERT INTO task(task_type, title, description, priority, status, deadline, assigned_member, technical_specification, code_part, error_message, fix_proposition) VALUES(?,?,?,?,?,?,?,?,?,?,?)";

        try (
                Connection con = Database.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, String.valueOf(task_type));
            ps.setString(2, title);
            ps.setString(3, description);
            ps.setString(4, priority.name());
            ps.setString(5, status.name());
            ps.setObject(6, deadline);
            ps.setString(7, assigned_member);
            ps.setString(8, technical_specification);
            ps.setString(9, code_part);
            ps.setString(10, error_message);
            ps.setString(11, fix_proposition);

            ps.executeUpdate();


        }

    }

    public static ArrayList<Task> loadTasks() throws SQLException {
        ArrayList<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM task";

        try (Connection con = Database.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                String type = rs.getString("task_type");

                java.sql.Date sqlDeadline = rs.getDate("deadline");
                LocalDate deadline = (sqlDeadline != null) ? sqlDeadline.toLocalDate() : LocalDate.now();

                // member Variable definieren
                TeamMember member = new TeamMember(
                        rs.getInt("id"),
                        rs.getString("assigned_member"),
                        "",
                        ""
                );

                if ("FEATURE".equalsIgnoreCase(type)) {
                    FeatureTask ft = new FeatureTask(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("description"),
                            //Priority.valueOf(rs.getString("priority")),
                            Priority.valueOf(rs.getString("priority").toUpperCase()),
                            //TaskStatus.valueOf(rs.getString("status")),
                            TaskStatus.valueOf(rs.getString("status").toUpperCase()),
                            deadline,
                            member,
                            rs.getString("technical_specification")
                    );
                    tasks.add(ft);

                } else if ("BUG".equalsIgnoreCase(type)) {
                    BugTask bt = new BugTask(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("description"),
                            Priority.valueOf(rs.getString("priority").toUpperCase()),
                            TaskStatus.valueOf(rs.getString("status").toUpperCase()),
                            deadline,
                            member,
                            rs.getString("code_part"),
                            rs.getString("error_message"),
                            rs.getString("fix_proposition")
                    );
                    tasks.add(bt);
                }
            }
        }
        return tasks;
    }

    public static void deleteTask(int id) throws SQLException {
        String sql = "DELETE FROM task WHERE id = ?";
        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    //
    public static void updateStatus(int id, TaskStatus status) throws SQLException {

        String sql = "UPDATE task SET status = ? WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status.name());
            ps.setInt(2, id);

            ps.executeUpdate();
        }
    }


}

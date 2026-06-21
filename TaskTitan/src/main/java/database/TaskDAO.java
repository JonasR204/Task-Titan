package database;

import model.*;
import java.sql.*;
import java.util.ArrayList;
import java.sql.SQLException;
import java.sql.Connection;

public class TaskDAO {

    public static void saveTask(Task task) throws SQLException {

        String sql =
                "INSERT INTO tasks VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        Connection con = Database.getConnection();

        PreparedStatement ps =
                con.prepareStatement(sql);

        ps.setInt(1, task.GetID());

        ps.setString(
                2,
                task.GetTaskType().toString());

        ps.setString(
                3,
                task.GetTitle());

        ps.setString(
                4,
                task.GetDescription());

        ps.setString(
                5,
                task.GetPriority().toString());

        ps.setString(
                6,
                task.GetStatus().toString());

        int[] d = task.GetDeadline();

        ps.setInt(7, d[0]);
        ps.setInt(8, d[1]);
        ps.setInt(9, d[2]);

        ps.setString(
                10,
                task.GetAssignedMember().GetName());

        if(task instanceof FeatureTask ft){

            ps.setString(
                    11,
                    ft.GetTechnicalSpecification());

            ps.setNull(12, Types.VARCHAR);
            ps.setNull(13, Types.VARCHAR);
            ps.setNull(14, Types.VARCHAR);
        }

        else if(task instanceof BugTask bt){

            ps.setNull(11, Types.VARCHAR);

            ps.setString(
                    12,
                    bt.GetCodePart());

            ps.setString(
                    13,
                    bt.GetErrorMessage());

            ps.setString(
                    14,
                    bt.GetFixProposition());
        }

        ps.executeUpdate();

        ps.close();
        con.close();
    }

    public static ArrayList<Task> loadTasks()
            throws SQLException {

        ArrayList<Task> tasks =
                new ArrayList<>();

        Connection con =
                Database.getConnection();

        Statement st =
                con.createStatement();

        ResultSet rs =
                st.executeQuery(
                        "SELECT * FROM tasks");

        while(rs.next()) {

            String type =
                    rs.getString("task_type");

            int[] deadline = {
                    rs.getInt("deadline_day"),
                    rs.getInt("deadline_month"),
                    rs.getInt("deadline_year")
            };

            TeamMember member =
                    new TeamMember(
                            0,
                            rs.getString(
                                    "assigned_member"),
                            "",
                            ""
                    );

            if(type.equals("FEATURE")) {

                FeatureTask ft =
                        new FeatureTask(
                                rs.getInt("id"),
                                rs.getString("title"),
                                rs.getString("description"),
                                Priority.valueOf(
                                        rs.getString(
                                                "priority")),
                                TaskStatus.valueOf(
                                        rs.getString(
                                                "status")),
                                deadline,
                                member,
                                rs.getString(
                                        "technical_specification")
                        );

                tasks.add(ft);
            }

            else if(type.equals("BUG")) {

                BugTask bt =
                        new BugTask(
                                rs.getInt("id"),
                                rs.getString("title"),
                                rs.getString("description"),
                                Priority.valueOf(
                                        rs.getString(
                                                "priority")),
                                TaskStatus.valueOf(
                                        rs.getString(
                                                "status")),
                                deadline,
                                member,
                                rs.getString(
                                        "code_part"),
                                rs.getString(
                                        "error_message"),
                                rs.getString(
                                        "fix_proposition")
                        );

                tasks.add(bt);
            }
        }

        rs.close();
        st.close();
        con.close();

        return tasks;
    }

    public static void deleteTask(int id)
            throws SQLException {

        String sql =
                "DELETE FROM tasks WHERE id = ?";

        Connection con =
                Database.getConnection();

        PreparedStatement ps =
                con.prepareStatement(sql);

        ps.setInt(1, id);

        ps.executeUpdate();

        ps.close();
        con.close();
    }
}
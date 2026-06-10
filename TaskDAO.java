import java.sql.*;

public class TaskDAO {

    public static void saveTask(Task task)
            throws SQLException {

        String sql =
                "INSERT INTO tasks VALUES " +
                        "(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        Connection con =
                Database.getConnection();

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
}
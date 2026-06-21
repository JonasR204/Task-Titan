package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;




public class Database {

    public static Connection getConnection() throws SQLException {

        String URL = "jdbc:mysql://127.0.0.1:3306/tasktitan_app";

        String USER = "root";

        String PASSWORD = "IhrNeuesPasswort";

        return DriverManager.getConnection(URL, USER, PASSWORD);

    }

}
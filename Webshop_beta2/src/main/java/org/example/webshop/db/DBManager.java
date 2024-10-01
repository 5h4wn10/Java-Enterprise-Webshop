package org.example.webshop.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {


    private static final String URL = "jdbc:mysql://127.0.0.1:3306/webshop_db?useSSL=false&serverTimezone=UTC";
    private static final String USER = "ShanSaifi";
    private static final String PASSWORD = "Shan2003+TS";

    // Ladda JDBC-drivrutinen
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Använd rätt JDBC-drivrutin för din MySQL-version
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Hämta en anslutning till databasen
    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    // Stäng en databasanslutning
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

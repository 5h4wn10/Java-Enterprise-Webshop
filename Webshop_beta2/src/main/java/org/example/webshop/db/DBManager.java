package org.example.webshop.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
    private static String url = "jdbc:mysql://127.0.0.1:3306/webshop_db";
    private static String user = "ShanSaifi";
    private static String password = "Shan2003+TS";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Öppna en databasanslutning och ställ in auto-commit till false för att hantera transaktioner manuellt
    public static Connection getConnection() throws SQLException {
        Connection con = DriverManager.getConnection(url, user, password);
        con.setAutoCommit(false);  // Ställ in auto-commit till false för manuell hantering
        return con;
    }

    // Begå en transaktion
    public static void commitTransaction(Connection con) throws SQLException {
        if (con != null) {
            con.commit();  // Genomför alla förändringar i databasen
        }
    }

    // Gör rollback på en transaktion vid fel
    public static void rollbackTransaction(Connection con) throws SQLException {
        if (con != null) {
            con.rollback();  // Återställ alla förändringar som gjorts under transaktionen
        }
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

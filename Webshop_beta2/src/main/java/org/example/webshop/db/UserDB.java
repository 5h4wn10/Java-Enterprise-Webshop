package org.example.webshop.db;

import org.example.webshop.bo.User;
import java.sql.*;

public class UserDB {

    // Metod för att hämta en användare från databasen med userId
    public static User getUserByUsername(String username) throws SQLException {
        User user = null;
        Connection con = DBManager.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String query = "SELECT user_id, username, password, email FROM users WHERE username = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, username);

            rs = ps.executeQuery();
            if (rs.next()) {
                int userId = rs.getInt("user_id");
                String dbUsername = rs.getString("username");
                String dbPassword = rs.getString("password");
                String email = rs.getString("email");

                // Skapa en User om användaren finns i databasen
                user = new User(userId, dbUsername, email, dbPassword);
            }
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        }

        return user; // Returnera användaren om den hittas, annars null
    }

    // Skapa en ny användare i databasen
    public static void createUser(User user) throws SQLException {
        Connection con = DBManager.getConnection();
        String query = "INSERT INTO users (username, email, password, created_at) VALUES (?, ?, ?, NOW())";
        PreparedStatement ps = con.prepareStatement(query);

        ps.setString(1, user.getUsername());
        ps.setString(2, user.getEmail());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();

        ps.close();
        con.close();
    }


    // Metod för att kontrollera om ett användarnamn eller e-post redan finns i databasen
    public static boolean userExists(String username, String email) throws SQLException {
        Connection con = DBManager.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // SQL-fråga för att kolla om användarnamnet eller e-posten redan finns
            String query = "SELECT COUNT(*) FROM users WHERE username = ? OR email = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, email);

            rs = ps.executeQuery();
            rs.next();

            // Om COUNT(*) > 0, så finns användaren redan
            return rs.getInt(1) > 0;
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        }
    }
}

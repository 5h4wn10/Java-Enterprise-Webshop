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
            // SQL-fråga som hämtar användarinfo tillsammans med rollnamnet genom en JOIN
            String query = "SELECT u.user_id, u.username, u.password, u.email, u.role_id, r.role_name " +
                    "FROM users u " +
                    "JOIN roles r ON u.role_id = r.role_id " +
                    "WHERE u.username = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, username);

            rs = ps.executeQuery();
            if (rs.next()) {
                int userId = rs.getInt("user_id");
                String dbUsername = rs.getString("username");
                String dbPassword = rs.getString("password");
                String email = rs.getString("email");
                int roleId = rs.getInt("role_id");
                String roleName = rs.getString("role_name");  // Hämtar rollnamnet

                // Skapa en User med rollen om användaren finns i databasen
                user = new User(userId, dbUsername, email, dbPassword, roleId, roleName);
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
        String query = "INSERT INTO users (username, email, password, role_id, created_at) VALUES (?, ?, ?, ?, NOW())";
        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement(query);

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setInt(4, user.getRoleId());

            // Försök att köra SQL-frågan
            try {
                ps.executeUpdate();
                System.out.println("User successfully saved!");
            } catch (SQLException e) {
                e.printStackTrace();  // Printar detaljer om SQL-felet till loggen
            }

        } finally {
            if (ps != null) ps.close();
            if (con != null) con.close();
        }
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

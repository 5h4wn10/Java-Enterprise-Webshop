package org.example.webshop.bo;

import java.sql.SQLException;

public class UserHandler {

    // Authenticate a user based on username and password
    public User authenticateUser(String username, String password) throws SQLException {
        // Använd User-klassens metod för att hämta användaren och autentisera
        User user = User.getUserByUsername(username); // Hämta användare via User-klassen
        if (user != null && user.authenticate(password)) {
            return user; // Return authenticated user
        }
        return null; // Return null if authentication fails
    }

    public void createUser(String username, String email, String password, int roleId) throws SQLException {
        User newUser = new User(username, email, password, roleId);
        newUser.save();
    }

    // Fetch user by username
    public User getUserByUsername(String username) throws SQLException {
        return User.getUserByUsername(username); // Använd User-klassens metod för att hämta användare
    }

    // Metod för att registrera en ny användare
    public void registerUser(String username, String email, String password, int roleId) throws SQLException {
        // Check if username or email already exists
        if (User.userExists(username, email)) {
            throw new SQLException("Username or email already exists.");
        }

        // Create a new user and save it to the database
        User newUser = new User(username, email, password, roleId); // Pass roleId here
        newUser.save();  // Use User class method to save the user
    }


}

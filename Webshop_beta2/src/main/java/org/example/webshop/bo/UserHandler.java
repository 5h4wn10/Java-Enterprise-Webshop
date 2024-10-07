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

    // Create a new user in the database
    public void createUser(String username, String email, String password) throws SQLException {
        User newUser = new User(username, email, password); // Skapa ett nytt User-objekt
        newUser.save();  // Använd User-klassens metod för att spara användaren
    }

    // Fetch user by username
    public User getUserByUsername(String username) throws SQLException {
        return User.getUserByUsername(username); // Använd User-klassens metod för att hämta användare
    }

    // Metod för att registrera en ny användare
    public void registerUser(String username, String email, String password) throws SQLException {
        // Kontrollera om användarnamnet eller e-posten redan finns
        if (User.userExists(username, email)) {
            throw new SQLException("Username or email already exists.");
        }

        // Skapa en ny användare och spara den i databasen
        User newUser = new User(username, email, password);
        newUser.save();  // Använd User-klassens metod för att spara användaren
    }

    // Ytterligare hantering av användarroller eller behörigheter kan också läggas till här
}

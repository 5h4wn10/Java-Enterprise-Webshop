package org.example.webshop.bo;

import org.example.webshop.db.UserDB;
import java.sql.SQLException;

public class User {
    private int userId;  // Nytt fält för user ID
    private String username;
    private String password;
    private String email;
    private int roleId;

    // Konstruktorer
    public User(int userId, String username, String email, String password) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User(int userId, String username, String email, String password, int roleId) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.roleId = roleId;
    }


    public User(int userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getter-metoder
    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getRoleId() { return roleId; }
    public void setRoleId(int roleId) { this.roleId = roleId; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // En enkel metod för autentisering
    public boolean authenticate(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    // Statisk metod för att hämta en användare från databasen via UserDB
    public static User getUserByUsername(String username) throws SQLException {
        return UserDB.getUserByUsername(username); // Delegerar till UserDB
    }

    // Metod för att spara en ny användare i databasen
    public void save() throws SQLException {
        UserDB.createUser(this);  // Anropar UserDB för att spara användaren
    }

    public static boolean userExists(String username, String email) throws SQLException {
        return UserDB.userExists(username, email);  // Delegerar till UserDB för att utföra kontrollen
    }
}

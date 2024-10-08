package org.example.webshop.ui;

import org.example.webshop.bo.User;

public class UserInfoDTO {
    private int userId;       // Nytt fält för userId
    private String username;

    private String email;

    public UserInfoDTO(String username) {
        this.username = username;
    }

    // Konstruktor med userId
    public UserInfoDTO(int userId, String username, String email) {
        this.userId = userId;
        this.username = username;
        this.email = email;
    }

    public UserInfoDTO(int userId, String username) {
        this.userId = userId;
        this.username = username;
    }



    // Getter för userId
    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    // Static method to create a UserInfoDTO from a User object
    public static UserInfoDTO fromUser(User user) {
        return new UserInfoDTO(user.getUsername());
    }
}

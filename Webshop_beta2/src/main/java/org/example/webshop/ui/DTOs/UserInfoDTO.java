package org.example.webshop.ui.DTOs;

import org.example.webshop.bo.User;

public class UserInfoDTO {
    private int userId;
    private String username;
    private String email;
    private int roleId;
    private String roleName;

    public UserInfoDTO(int userId, String username, String email, int roleId, String roleName) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public UserInfoDTO(int userId, String username, String email, int roleId) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.roleId = roleId;
    }

    // Getter methods for userId, roleId, roleName
    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public int getRoleId() {
        return roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    // Display friendly role name (optional)
    public String getRoleDisplayName() {
        switch (roleId) {
            case 1: return "Customer";
            case 2: return "Admin";
            case 3: return "Warehouse Staff";
            default: return "Unknown Role";
        }
    }
}


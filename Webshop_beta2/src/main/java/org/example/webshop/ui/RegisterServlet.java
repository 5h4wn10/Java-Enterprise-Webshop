package org.example.webshop.ui;

import jakarta.servlet.annotation.WebServlet;
import org.example.webshop.bo.UserHandler;
import org.example.webshop.ui.UserInfoDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/registerServlet")
public class RegisterServlet extends HttpServlet {

    private UserHandler userHandler = new UserHandler();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        int roleId = Integer.parseInt(request.getParameter("role")); // Fetch roleId from the registration form

        try {
            // Register user with role
            userHandler.registerUser(username, email, password, roleId);

            // Create UserInfoDTO with role data and set it in the session
            UserInfoDTO userDTO = new UserInfoDTO(0, username, email, roleId);  // Update constructor to match available data
            request.getSession().setAttribute("user", userDTO);

            response.sendRedirect("login.jsp");

        } catch (SQLException e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }
}

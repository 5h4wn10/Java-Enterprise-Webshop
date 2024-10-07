package org.example.webshop.ui;

import jakarta.servlet.annotation.WebServlet;
import org.example.webshop.bo.UserHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/registerServlet")
public class RegisterServlet extends HttpServlet {

    private UserHandler userHandler = new UserHandler();  // Instans av UserHandler

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            // Använd UserHandler för att registrera användaren
            userHandler.registerUser(username, email, password);

            // Omdirigera till login-sidan efter framgångsrik registrering
            response.sendRedirect("login.jsp");

        } catch (SQLException e) {
            // Hantera eventuella fel, t.ex. användarnamn eller e-post finns redan
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }
}

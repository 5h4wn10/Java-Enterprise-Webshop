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

    private UserHandler userHandler = new UserHandler();  // Instans av UserHandler

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            // Använd UserHandler (BO) för att registrera användaren
            userHandler.registerUser(username, email, password);

            // När användaren är registrerad, skapa ett UserInfoDTO och sätt i sessionen för att skicka till UI
            UserInfoDTO userDTO = new UserInfoDTO(username);  // Endast användarnamnet för DTO
            request.getSession().setAttribute("user", userDTO);

            // Omdirigera till login-sidan efter framgångsrik registrering
            response.sendRedirect("login.jsp");

        } catch (SQLException e) {
            // Hantera eventuella fel, t.ex. användarnamn eller e-post finns redan
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }
}

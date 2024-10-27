package org.example.webshop.ui.Servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/logoutServlet")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Hämta sessionen
        HttpSession session = request.getSession(false); // Falskt för att undvika att skapa en ny session om den inte finns
        if (session != null) {
            // Invalidera sessionen för att logga ut användaren
            session.invalidate();
        }

        // Skicka tillbaka användaren till login-sidan
        response.sendRedirect("login.jsp");
    }
}

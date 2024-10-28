package org.example.webshop.ui.Servlets;

import org.example.webshop.bo.User;
import org.example.webshop.bo.UserHandler;
import org.example.webshop.ui.DTOs.UserInfoDTO;
import org.example.webshop.bo.ShoppingCart;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/userServlet")
public class UserServlet extends HttpServlet {
    private UserHandler userHandler = new UserHandler();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("login".equals(action)) {
            loginUser(request, response);
        } else if ("register".equals(action)) {
            registerUser(request, response);
        } else if ("logout".equals(action)) {
            logoutUser(request, response);
        }
    }

    private void loginUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            User user = userHandler.authenticateUser(username, password);
            if (user != null) {
                // Skapa UserInfoDTO för session
                UserInfoDTO userDTO = new UserInfoDTO(user.getUserId(), user.getUsername(), user.getEmail(), user.getRoleId(), user.getRoleName());
                HttpSession session = request.getSession();
                session.setAttribute("user", userDTO);

                // Skapa eller hämta shopping cart
                ShoppingCart cart = (ShoppingCart) session.getAttribute(userDTO.getUsername() + "_cart");
                if (cart == null) {
                    cart = new ShoppingCart(user.getUserId());
                    session.setAttribute(userDTO.getUsername() + "_cart", cart);
                }

                // Kontrollera användarrollen och dirigera till rätt sida
                if (userDTO.getRoleId() == 2 || userDTO.getRoleId() == 3) {
                    // Om admin (2) eller lagerpersonal (3), skicka till admin-sidan
                    response.sendRedirect("admin.jsp");
                } else {
                    // Om vanlig customer, skicka till index-sidan
                    response.sendRedirect("index.jsp");
                }
            } else {
                request.setAttribute("errorMessage", "Invalid login credentials.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void registerUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        int roleId = Integer.parseInt(request.getParameter("role"));

        try {
            userHandler.registerUser(username, email, password, roleId);

            UserInfoDTO userDTO = new UserInfoDTO(0, username, email, roleId);
            request.getSession().setAttribute("user", userDTO);

            response.sendRedirect("login.jsp");

        } catch (SQLException e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }

    private void logoutUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        response.sendRedirect("login.jsp");
    }
}

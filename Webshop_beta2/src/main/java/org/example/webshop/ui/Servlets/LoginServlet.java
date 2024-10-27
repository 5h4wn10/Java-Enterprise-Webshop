package org.example.webshop.ui.Servlets;

import org.example.webshop.bo.User;
import org.example.webshop.bo.ShoppingCart;
import org.example.webshop.ui.UserInfoDTO;
import org.example.webshop.bo.UserHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    private UserHandler userHandler = new UserHandler();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            User user = userHandler.authenticateUser(username, password);
            if (user != null) {
                // Fetch roleId and roleName from user
                UserInfoDTO userDTO = new UserInfoDTO(user.getUserId(), user.getUsername(), user.getEmail(), user.getRoleId(), user.getRoleName());
                HttpSession session = request.getSession();
                session.setAttribute("user", userDTO);

                // Create new shopping cart if it does not exist
                ShoppingCart cart = (ShoppingCart) session.getAttribute(userDTO.getUsername() + "_cart");
                if (cart == null) {
                    cart = new ShoppingCart(user.getUserId());  // Create a new, empty cart
                    session.setAttribute(userDTO.getUsername() + "_cart", cart);
                }

                // Redirect based on role: admin goes to admin page, customer goes to index
                if (userDTO.getRoleId() != 1) {
                    response.sendRedirect("admin.jsp");  // Admin page
                } else {
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
}


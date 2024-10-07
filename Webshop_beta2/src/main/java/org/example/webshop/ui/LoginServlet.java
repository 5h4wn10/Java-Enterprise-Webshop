package org.example.webshop.ui;

import org.example.webshop.bo.User;
import org.example.webshop.bo.ShoppingCart;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.annotation.WebServlet;
import org.example.webshop.bo.UserHandler;

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
                // Konvertera User till UserDTO och sätt i sessionen
                UserInfoDTO userDTO = new UserInfoDTO(user.getUsername());
                HttpSession session = request.getSession();
                session.setAttribute("user", userDTO);

                // Skapa en ny tom kundkorg om det inte redan finns
                ShoppingCart cart = (ShoppingCart) session.getAttribute(userDTO.getUsername() + "_cart");
                if (cart == null) {
                    cart = new ShoppingCart(user.getUserId());  // Skapa en ny, tom varukorg
                    session.setAttribute(userDTO.getUsername() + "_cart", cart);  // Lägg till den nya varukorgen i sessionen
                }

                // Omdirigera till indexsidan
                response.sendRedirect("index.jsp");
            } else {
                // Felaktig inloggning, skicka tillbaka med felmeddelande
                request.setAttribute("errorMessage", "Invalid login credentials.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}

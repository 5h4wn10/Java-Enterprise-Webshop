package org.example.webshop.ui;

import org.example.webshop.bo.ShoppingCart;
import org.example.webshop.bo.ShoppingCartHandler;
import org.example.webshop.ui.ItemInfoDTO;
import org.example.webshop.ui.ShoppingCartDTO;
import org.example.webshop.ui.UserInfoDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/shoppingServlet")
public class ShoppingServlet extends HttpServlet {
    private ShoppingCartHandler cartHandler = new ShoppingCartHandler();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Hämta parametrarna från formuläret
        String itemId = request.getParameter("itemId");
        String itemName = request.getParameter("itemName");
        String itemPrice = request.getParameter("itemPrice");
        String itemGroup = request.getParameter("itemGroup");

        // Skapa ett ItemInfoDTO-objekt baserat på parametrarna
        ItemInfoDTO item = new ItemInfoDTO(
                Integer.parseInt(itemId),
                itemName,
                "", // description (om det behövs)
                Integer.parseInt(itemPrice),
                itemGroup
        );

        // Hämta session och användarens unika kundkorg (BO-klass)
        HttpSession session = request.getSession();
        UserInfoDTO user = (UserInfoDTO) session.getAttribute("user");

        // Hämta eller skapa en ny ShoppingCart (BO-klass)
        ShoppingCart cart = (ShoppingCart) session.getAttribute(user.getUsername() + "_cart");
        if (cart == null) {
            cart = new ShoppingCart(user.getUserId());
            session.setAttribute(user.getUsername() + "_cart", cart);
        }

        // Använd ShoppingCartHandler för att lägga till varan i korgen
        try {
            cartHandler.addItemToCart(cart, item);  // Uppdatera BO-korgen
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Konvertera till ShoppingCartDTO för användning i UI (DTO-klass)
        ShoppingCartDTO cartDTO = new ShoppingCartDTO(user.getUserId(), cart.getItems());
        session.setAttribute("cartDTO", cartDTO);  // Uppdatera sessionen med den senaste DTO-korgen

        // Omdirigera tillbaka till en sida för att visa kundkorgen, till exempel cart.jsp
        response.sendRedirect("cart.jsp");
    }
}

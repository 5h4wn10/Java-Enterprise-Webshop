package org.example.webshop.ui.Servlets;

import org.example.webshop.bo.OrderItem;
import org.example.webshop.bo.ShoppingCart;
import org.example.webshop.bo.Item;
import org.example.webshop.ui.DTOs.ShoppingCartDTO;
import org.example.webshop.ui.DTOs.UserInfoDTO;
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Hämta parametrarna från formuläret
        String itemId = request.getParameter("itemId");
        int quantityRequested = Integer.parseInt(request.getParameter("quantity")); // Anta att du skickar antalet som användaren vill köpa
        String itemName = request.getParameter("itemName");
        String itemPrice = request.getParameter("itemPrice");
        String itemGroup = request.getParameter("itemGroup");

        try {
            // Hämta produkten från databasen
            Item item = Item.getItemById(Integer.parseInt(itemId));

            // Kontrollera lagersaldot
            if (item.getStock_quantity() < quantityRequested) {
                // Om lagret är mindre än det begärda, skicka ett felmeddelande
                request.setAttribute("errorMessage", "Sorry, only " + item.getStock_quantity() + " of " + itemName + " available.");
                request.getRequestDispatcher("index.jsp").forward(request, response);
                return;
            }

            // Om det finns tillräckligt i lager, lägg till produkten i kundvagnen
            OrderItem orderItem = new OrderItem(
                    item.getId(),
                    item.getName(),
                    item.getDescription(),
                    item.getPrice(),
                    item.getGroup(),
                    item.getStock_quantity(),
                    quantityRequested // Lägg till den begärda kvantiteten
            );

            // Hämta session och användarens kundvagn (ShoppingCart BO-klassen)
            HttpSession session = request.getSession();
            UserInfoDTO user = (UserInfoDTO) session.getAttribute("user");

            // Retrieve the backend cart
            ShoppingCart cart = (ShoppingCart) session.getAttribute(user.getUsername() + "_cart_bo");

            // If no cart exists, initialize a new one
            if (cart == null) {
                cart = new ShoppingCart(user.getUserId());
            }

            // Add the order item to the cart
            cart.addItem(orderItem);

            // Update the session attributes with the new cart
            ShoppingCartDTO cartDTO = new ShoppingCartDTO(user.getUserId(), cart.getItems());
            session.setAttribute(user.getUsername() + "_cart_bo", cart);  // Save BO class for backend operations
            session.setAttribute(user.getUsername() + "_cart", cartDTO);  // Save DTO class for frontend operations

            // Redirect to cart page
            response.sendRedirect("cart.jsp");


        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching item data.");
        }
    }
}

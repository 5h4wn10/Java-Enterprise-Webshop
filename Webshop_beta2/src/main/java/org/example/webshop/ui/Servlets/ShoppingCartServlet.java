package org.example.webshop.ui.Servlets;

import org.example.webshop.bo.Item;
import org.example.webshop.bo.OrderItem;
import org.example.webshop.bo.ShoppingCart;
import org.example.webshop.ui.DTOs.OrderItemDTO;
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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/cartServlet")
public class ShoppingCartServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("view".equals(action)) {
            viewCart(request, response);
        } else if ("checkout".equals(action)) {
            checkout(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            addToCart(request, response);
        }
    }

    /**
     * Lägger till en vara i kundvagnen och hanterar både BO och DTO
     */
    private void addToCart(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String itemId = request.getParameter("itemId");
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        try {
            // Hämta varan från databasen via BO
            Item item = Item.getItemById(Integer.parseInt(itemId));

            if (item.getStock_quantity() < quantity) {
                request.setAttribute("errorMessage", "Not enough stock available.");
                request.getRequestDispatcher("index.jsp").forward(request, response);
                return;
            }

            // Skapa en OrderItem (BO) och lägga till i backend-cart (BO)
            OrderItem orderItem = new OrderItem(item.getId(), item.getName(), item.getDescription(), item.getPrice(), item.getGroup(), item.getStock_quantity(), quantity);

            HttpSession session = request.getSession();
            UserInfoDTO user = (UserInfoDTO) session.getAttribute("user");

            // Hämta backend cart (BO)
            ShoppingCart cart = (ShoppingCart) session.getAttribute(user.getUsername() + "_cart_bo");
            if (cart == null) {
                cart = new ShoppingCart(user.getUserId());
            }

            cart.addItem(orderItem);
            session.setAttribute(user.getUsername() + "_cart_bo", cart);

            // Konvertera cart (BO) till DTO för presentation i JSP
            updateCartToDTO(session, user, cart);

            response.sendRedirect("cart.jsp");

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error adding item to cart.");
        }
    }

    /**
     * Visar kundvagnen i cart.jsp och konverterar BO till DTO
     */
    private void viewCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserInfoDTO user = (UserInfoDTO) session.getAttribute("user");

        ShoppingCart cart = (ShoppingCart) session.getAttribute(user.getUsername() + "_cart_bo");
        if (cart == null) {
            cart = new ShoppingCart(user.getUserId());
        }

        // Uppdatera sessionen med DTO
        updateCartToDTO(session, user, cart);

        request.setAttribute("cartItems", ((ShoppingCartDTO) session.getAttribute(user.getUsername() + "_cart")).getItems());
        request.setAttribute("totalPrice", ((ShoppingCartDTO) session.getAttribute(user.getUsername() + "_cart")).getTotalPrice());
        request.getRequestDispatcher("cart.jsp").forward(request, response);
    }

    /**
     * Skickar användaren till checkout.jsp och hanterar konvertering till DTO för frontend-användning.
     */
    private void checkout(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        UserInfoDTO user = (UserInfoDTO) session.getAttribute("user");

        ShoppingCart cart = (ShoppingCart) session.getAttribute(user.getUsername() + "_cart_bo");

        if (cart == null || cart.getItems().isEmpty()) {
            request.setAttribute("errorMessage", "Your cart is empty.");
            request.getRequestDispatcher("cart.jsp").forward(request, response);
            return;
        }

        // Uppdatera sessionen med DTO
        updateCartToDTO(session, user, cart);

        request.setAttribute("cartItems", ((ShoppingCartDTO) session.getAttribute(user.getUsername() + "_cart")).getItems());
        request.setAttribute("totalPrice", ((ShoppingCartDTO) session.getAttribute(user.getUsername() + "_cart")).getTotalPrice());
        request.getRequestDispatcher("checkout.jsp").forward(request, response);
    }

    /**
     * Hjälpmetod för att konvertera BO-cart till DTO och uppdatera sessionen
     */
    private void updateCartToDTO(HttpSession session, UserInfoDTO user, ShoppingCart cart) {
        // Konvertera BO-cart till DTO
        List<OrderItemDTO> cartItemsDTO = new ArrayList<>();
        for (OrderItem itemInCart : cart.getItems()) {
            cartItemsDTO.add(new OrderItemDTO(
                    itemInCart.getId(),
                    itemInCart.getName(),
                    itemInCart.getDescription(),
                    itemInCart.getPrice(),
                    itemInCart.getGroup(),
                    itemInCart.getOrderedQuantity())
            );
        }

        // Skapa ShoppingCartDTO och spara i sessionen
        ShoppingCartDTO cartDTO = new ShoppingCartDTO(cart.getUserId(), cart.getItems());  // Använd DTO för items här
        session.setAttribute(user.getUsername() + "_cart", cartDTO);
    }
}

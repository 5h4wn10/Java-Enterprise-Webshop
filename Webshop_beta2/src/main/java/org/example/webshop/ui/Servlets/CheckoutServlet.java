
package org.example.webshop.ui.Servlets;

import org.example.webshop.bo.Order;
import org.example.webshop.bo.OrderHandler;
import org.example.webshop.bo.OrderItem;
import org.example.webshop.bo.ShoppingCart;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.webshop.db.DBManager;
import org.example.webshop.ui.OrderItemDTO;
import org.example.webshop.ui.ShoppingCartDTO;
import org.example.webshop.ui.UserInfoDTO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/checkoutServlet")
public class CheckoutServlet extends HttpServlet {

    private OrderHandler orderHandler = new OrderHandler();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserInfoDTO user = (UserInfoDTO) session.getAttribute("user");

        // Kontrollera om användaren är inloggad
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Hämta kundvagnen från sessionen
        ShoppingCart cart = (ShoppingCart) session.getAttribute(user.getUsername() + "_cart");

        // Kontrollera om kundvagnen är tom
        if (cart == null || cart.getItems().isEmpty()) {
            request.setAttribute("errorMessage", "Your cart is empty. Please add items before checking out.");
            request.getRequestDispatcher("cart.jsp").forward(request, response);
            return;
        }

        // Konvertera ShoppingCart till OrderItemDTO-lista (för UI-lagret)
        List<OrderItemDTO> orderItemsDTO = new ArrayList<>();
        for (OrderItem item : cart.getItems()) {
            OrderItemDTO orderItemDTO = new OrderItemDTO(
                    item.getId(),
                    item.getName(),
                    item.getDescription(),
                    item.getPrice(),
                    item.getGroup(),
                    item.getOrderedQuantity()
            );
            orderItemsDTO.add(orderItemDTO);
        }

        // Skicka varorna till checkout.jsp via DTO
        request.setAttribute("cartItems", orderItemsDTO);
        request.setAttribute("totalPrice", cart.getTotalPrice());
        request.getRequestDispatcher("checkout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserInfoDTO user = (UserInfoDTO) session.getAttribute("user");

        // Check if the user is logged in
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Retrieve the cart from the session
        ShoppingCartDTO cartDTO = (ShoppingCartDTO) session.getAttribute(user.getUsername() + "_cart");

        // Check if the cart is empty
        if (cartDTO == null || cartDTO.getItems().isEmpty()) {
            request.setAttribute("errorMessage", "Your cart is empty. Please add items before checking out.");
            request.getRequestDispatcher("cart.jsp").forward(request, response);
            return;
        }

        // Create Order object and add items from the cart
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemDTO itemDTO : cartDTO.getItems()) {
            orderItems.add(new OrderItem(
                    itemDTO.getItemId(),
                    itemDTO.getName(),
                    itemDTO.getDescription(),
                    itemDTO.getPrice(),
                    itemDTO.getGroup(),
                    itemDTO.getOrderedQuantity()
            ));
        }

        Order order = new Order(user.getUserId(), orderItems);

        try {
            OrderHandler orderHandler = new OrderHandler();
            // Create the order and save it to the database
            orderHandler.createOrder(user.getUserId(), order);

            // Clear or remove the cart from the session after the order has been placed
            session.removeAttribute(user.getUsername() + "_cart");   // Remove the frontend cart
            session.removeAttribute(user.getUsername() + "_cart_bo"); // Remove the backend cart (BO class)

            // After the order has been processed, pass data to the JSP
            List<OrderItemDTO> orderItemsDTO = new ArrayList<>();
            for (OrderItem item : order.getItems()) {
                OrderItemDTO orderItemDTO = new OrderItemDTO(
                        item.getId(),
                        item.getName(),
                        item.getDescription(),
                        item.getPrice(),
                        item.getGroup(),
                        item.getOrderedQuantity()
                );
                orderItemsDTO.add(orderItemDTO);
            }

            // Pass the orderItems list as an attribute to the JSP
            request.setAttribute("orderItems", orderItemsDTO);  // Send the list of OrderItemDTO to the JSP
            request.setAttribute("totalPrice", order.getTotalPrice());  // Send total price to the JSP

            // Forward to order confirmation page
            request.getRequestDispatcher("orderConfirmation.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Order processing failed", e);
        }
    }
}
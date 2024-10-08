package org.example.webshop.ui;

import org.example.webshop.bo.Order;
import org.example.webshop.bo.OrderHandler;
import org.example.webshop.bo.OrderItem;
import org.example.webshop.bo.ShoppingCart;
import org.example.webshop.ui.OrderDTO;
import org.example.webshop.ui.OrderItemDTO;
import org.example.webshop.ui.UserInfoDTO;
import org.example.webshop.ui.ItemInfoDTO;

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

        // Konvertera kundvagnens varor till OrderItems för att skapa en ny order
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItem item : cart.getItems()) {
            orderItems.add(item);
        }

        try {
            Order order = new Order(user.getUserId(), orderItems);
            orderHandler.createOrder(user.getUserId(), order);

            // Töm kundvagnen efter beställning
            cart.clear();
            session.setAttribute(user.getUsername() + "_cart", cart);

            // Konvertera ordern till OrderDTO
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

            // Skapa en OrderDTO för att skicka till JSP
            OrderDTO orderDTO = new OrderDTO(order.getOrderId(), user.getUserId(), orderItemsDTO, order.getTotalPrice());

            // Skicka OrderDTO till orderConfirmation.jsp
            request.setAttribute("orderDTO", orderDTO);
            request.getRequestDispatcher("orderConfirmation.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Order processing failed", e);
        }
    }
}

package org.example.webshop.ui.Servlets;

import org.example.webshop.bo.OrderHandler;
import org.example.webshop.bo.Order;
import org.example.webshop.bo.OrderItem;
import org.example.webshop.bo.ShoppingCart;
import org.example.webshop.ui.DTOs.OrderDTO;
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

@WebServlet("/orderServlet")
public class OrderServlet extends HttpServlet {
    private OrderHandler orderHandler = new OrderHandler();

    /*@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("view".equals(action)) {
            viewOrders(request, response);
        } else if ("pack".equals(action)) {
            packOrder(request, response);
        } else if ("create".equals(action)) {
            createOrder(request, response);
        }
    }*/

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("view".equals(action)) {
            viewOrders(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if("packOrder".equals(action)) {
            packOrder(request, response);
        } else if ("create".equals(action)) {
            createOrder(request, response);
        }
    }

    private void viewOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserInfoDTO user = (UserInfoDTO) session.getAttribute("user");

        if (user == null || user.getRoleId() != 3) {
            response.sendRedirect("accessDenied.jsp");
            return;
        }

        try {
            List<Order> pendingOrders = orderHandler.getPendingOrders();
            List<OrderDTO> orderDTOs = new ArrayList<>();

            for (Order order : pendingOrders) {
                List<OrderItemDTO> orderItemsDTO = new ArrayList<>();
                for (OrderItem item : order.getItems()) {
                    orderItemsDTO.add(new OrderItemDTO(item.getId(), item.getName(), item.getDescription(), item.getPrice(), item.getOrderedQuantity()));
                }
                orderDTOs.add(new OrderDTO(order.getOrderId(), order.getUserId(), orderItemsDTO, order.getTotalpriceGeneral(), order.getOrderDate()));
            }

            request.setAttribute("pendingOrders", orderDTOs);
            request.getRequestDispatcher("viewOrders.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving orders.");
        }
    }

    private void packOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));

        try {
            orderHandler.markOrderAsPacked(orderId);
            response.sendRedirect("orderServlet?action=view");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error packing order.");
        }
    }

    private void createOrder(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        UserInfoDTO user = (UserInfoDTO) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Retrieve the cart from the session
        ShoppingCart cart = (ShoppingCart) session.getAttribute(user.getUsername() + "_cart_bo");

        // Check if the cart is empty
        if (cart == null || cart.getItems().isEmpty()) {
            request.setAttribute("errorMessage", "Your cart is empty. Please add items before checking out.");
            request.getRequestDispatcher("cart.jsp").forward(request, response);
            return;
        }

        // Create order from cart items
        List<OrderItem> orderItems = new ArrayList<>(cart.getItems());
        Order order = new Order(user.getUserId(), orderItems);

        try {
            orderHandler.createOrder(user.getUserId(), order);

            // Clear the cart after the order is placed
            session.removeAttribute(user.getUsername() + "_cart_bo");

            // **Convert order items to DTOs for frontend display**
            List<OrderItemDTO> orderItemsDTO = new ArrayList<>();
            for (OrderItem item : orderItems) {
                orderItemsDTO.add(new OrderItemDTO(item.getId(), item.getName(), item.getDescription(), item.getPrice(), item.getOrderedQuantity()));
            }

            // Pass order data to confirmation page
            request.setAttribute("orderItems", orderItemsDTO);
            request.setAttribute("totalPrice", order.getTotalPrice());
            request.getRequestDispatcher("orderConfirmation.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Order processing failed", e);
        }
    }
}

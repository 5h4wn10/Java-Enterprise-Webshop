package org.example.webshop.ui.Servlets;

import org.example.webshop.bo.Order;
import org.example.webshop.bo.OrderHandler;
import org.example.webshop.bo.OrderItem;
import org.example.webshop.ui.DTOs.OrderItemDTO;
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserInfoDTO user = (UserInfoDTO) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Only admins can access this section
        if (user.getRoleId() != 2) {
            response.sendRedirect("unauthorized.jsp");  // Show unauthorized access page
            return;
        }

        // Fetch and display the order for admins
        int orderId = Integer.parseInt(request.getParameter("orderId"));

        try {
            Order order = orderHandler.getOrderById(orderId);

            List<OrderItemDTO> orderItemsDTO = new ArrayList<>();
            for (OrderItem item : order.getItems()) {
                orderItemsDTO.add(new OrderItemDTO(item.getId(), item.getName(), item.getDescription(), item.getPrice(), item.getGroup(), item.getOrderedQuantity()));
            }

            request.setAttribute("orderItems", orderItemsDTO);
            request.setAttribute("totalPrice", order.getTotalPrice());
            request.getRequestDispatcher("orderConfirmation.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Error retrieving order", e);
        }
    }
}


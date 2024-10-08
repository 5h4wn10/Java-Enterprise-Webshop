package org.example.webshop.ui;

import org.example.webshop.bo.Order;
import org.example.webshop.bo.OrderHandler;
import org.example.webshop.bo.OrderItem;
import org.example.webshop.ui.OrderItemDTO;
import org.example.webshop.ui.UserInfoDTO;

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

        // Hämta order-id från förfrågan
        int orderId = Integer.parseInt(request.getParameter("orderId"));

        try {
            // Hämta ordern baserat på orderId
            Order order = orderHandler.getOrderById(orderId);

            // Konvertera OrderItems till OrderItemDTOs för att skicka till JSP
            List<OrderItemDTO> orderItemsDTO = new ArrayList<>();
            for (OrderItem item : order.getItems()) {
                OrderItemDTO dto = new OrderItemDTO(
                        item.getId(),
                        item.getName(),
                        item.getDescription(),
                        item.getPrice(),
                        item.getGroup(),
                        item.getOrderedQuantity()
                );
                orderItemsDTO.add(dto);
            }

            // Skicka orderinformation till JSP via DTO
            request.setAttribute("orderItems", orderItemsDTO);
            request.setAttribute("totalPrice", order.getTotalPrice());
            request.getRequestDispatcher("orderConfirmation.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Error retrieving order", e);
        }
    }
}

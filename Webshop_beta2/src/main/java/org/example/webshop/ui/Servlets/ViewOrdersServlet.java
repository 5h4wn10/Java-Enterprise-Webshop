package org.example.webshop.ui.Servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.webshop.bo.Order;
import org.example.webshop.bo.OrderHandler;
import org.example.webshop.bo.OrderItem;
import org.example.webshop.ui.OrderDTO;
import org.example.webshop.ui.OrderItemDTO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@WebServlet("/viewOrdersServlet")
public class ViewOrdersServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Hämta alla ordrar med status "Pending"
            List<Order> pendingOrders = OrderHandler.getPendingOrders();

            // Debug: Skriv ut antal pending orders som hittades
            System.out.println("Antal ordrar med status 'Pending': " + pendingOrders.size());

            // Konvertera till DTO
            List<OrderDTO> orderDTOs = new ArrayList<>();
            for (Order order : pendingOrders) {
                List<OrderItemDTO> orderItemsDTO = new ArrayList<>();
                for (OrderItem item : order.getItems()) {
                    orderItemsDTO.add(new OrderItemDTO(item.getId(), item.getName(), item.getDescription(), item.getPrice(), item.getOrderedQuantity()));
                }
                orderDTOs.add(new OrderDTO(order.getOrderId(), order.getUserId(), orderItemsDTO, order.getTotalPrice(), order.getOrderDate()));
            }

            // Debug: Skriv ut antal DTOs som skapades
            System.out.println("Antal konverterade DTOs: " + orderDTOs.size());

            // Lägg till DTOs i request attributet
            request.setAttribute("pendingOrders", orderDTOs);
            request.getRequestDispatcher("viewOrders.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving orders.");
        }
    }
}
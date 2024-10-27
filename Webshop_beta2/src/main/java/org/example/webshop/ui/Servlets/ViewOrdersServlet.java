package org.example.webshop.ui.Servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.webshop.bo.Order;
import org.example.webshop.bo.OrderHandler;
import org.example.webshop.bo.OrderItem;
import org.example.webshop.ui.DTOs.OrderDTO;
import org.example.webshop.ui.DTOs.OrderItemDTO;
import org.example.webshop.ui.DTOs.UserInfoDTO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/viewOrdersServlet")
public class ViewOrdersServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Kontrollera om användaren är inloggad och om de har rätt roll
        UserInfoDTO user = (UserInfoDTO) request.getSession().getAttribute("user");


        if (user.getRoleId() != 3) {
            // Omdirigera till accessDenied.jsp om användaren inte har rätt roll dvs man måste vara lagerpersonal för att kunna kolla på alla orders
            response.sendRedirect("accessDenied.jsp");
            return;
        }

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
                    System.out.println("Item Name: " + item.getName() + ", Price: " + item.getPrice() + ", Ordered Quantity: " + item.getOrderedQuantity());
                    orderItemsDTO.add(new OrderItemDTO(item.getId(), item.getName(), item.getDescription(), item.getPrice(), item.getOrderedQuantity()));
                }

                orderDTOs.add(new OrderDTO(order.getOrderId(), order.getUserId(), orderItemsDTO, order.getTotalpriceGeneral(), order.getOrderDate()));
            }

            System.out.println("Antal konverterade DTOs: " + orderDTOs.size());

            request.setAttribute("pendingOrders", orderDTOs);
            request.getRequestDispatcher("viewOrders.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving orders.");
        }
    }
}

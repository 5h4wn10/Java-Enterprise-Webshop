package org.example.webshop.ui.Servlets;

import org.example.webshop.bo.OrderHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/packOrderServlet")
public class PackOrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        System.out.println("PackOrderServlet: Försöker packa order med ID: " + orderId);

        try {
            OrderHandler.markOrderAsPacked(orderId);
            System.out.println("PackOrderServlet: lyckades anropa OrderHandler");
            response.sendRedirect("viewOrdersServlet");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error packing the order.");
        }

    }
}

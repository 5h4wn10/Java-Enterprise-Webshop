package org.example.webshop.ui.Servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.webshop.bo.ItemHandler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/updateItemServlet")
public class UpdateItemServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int itemId = Integer.parseInt(request.getParameter("itemId"));
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        int price = Integer.parseInt(request.getParameter("price"));
        int stock = Integer.parseInt(request.getParameter("stock_quantity"));

        try {
            ItemHandler.updateItem(itemId, name, description, price, stock);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        response.sendRedirect("adminItemServlet");  // Återgår till items-sidan efter uppdatering
    }
}

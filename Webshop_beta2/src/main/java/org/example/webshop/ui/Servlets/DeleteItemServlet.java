package org.example.webshop.ui.Servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.webshop.bo.ItemHandler;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/deleteProductServlet")
public class DeleteItemServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int itemId = Integer.parseInt(request.getParameter("itemId"));

        try {
            ItemHandler.deleteItem(itemId);
            response.sendRedirect("adminItemServlet");  // Redirect to the list of items after deletion
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

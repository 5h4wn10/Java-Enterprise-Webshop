package org.example.webshop.ui.Servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.webshop.bo.ItemHandler;
import org.example.webshop.ui.ItemInfoDTO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/adminItemServlet")
public class AdminItemServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<ItemInfoDTO> items = null;

        try {
            items = ItemHandler.getAllItems();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        request.setAttribute("items", items);
        request.getRequestDispatcher("admin.jsp").forward(request, response);
    }
}

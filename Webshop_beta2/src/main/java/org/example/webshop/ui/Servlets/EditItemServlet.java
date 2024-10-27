package org.example.webshop.ui.Servlets;

import org.example.webshop.bo.Item;
import org.example.webshop.bo.ItemHandler;
import org.example.webshop.ui.ItemInfoDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/editItem")
public class EditItemServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int itemId = Integer.parseInt(request.getParameter("itemId"));

        try {
            // Hämta objektet via ItemHandler
            Item item = ItemHandler.getItemById(itemId);
            System.out.println("Fetched item: " + (item != null ? item.getName() : "null"));

            if (item != null) {
                // Konvertera till DTO om nödvändigt
                ItemInfoDTO itemDTO = new ItemInfoDTO(item.getId(), item.getName(), item.getDescription(), item.getPrice(), item.getGroup(), item.getStock_quantity());
                request.setAttribute("item", itemDTO);
            } else {
                request.setAttribute("errorMessage", "Item not found.");
                System.out.println("Item not found in database.");
            }

            request.getRequestDispatcher("editItem.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error loading item for editing.");
        }
    }
}

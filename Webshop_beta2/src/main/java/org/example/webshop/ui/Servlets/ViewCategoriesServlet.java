package org.example.webshop.ui.Servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.webshop.bo.Category;
import org.example.webshop.ui.DTOs.CategoryDTO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/viewCategoriesServlet")
public class ViewCategoriesServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Hämta alla kategorier som Category-objekt
            List<Category> categories = Category.getAllCategories();

            // Konvertera varje Category till CategoryDTO
            List<CategoryDTO> categoryDTOs = new ArrayList<>();
            for (Category category : categories) {
                categoryDTOs.add(new CategoryDTO(category.getCategoryId(), category.getName()));
            }

            // Lägg till listan med CategoryDTO till request-attributet
            request.setAttribute("categories", categoryDTOs);

            // Skicka vidare till JSP-sidan
            request.getRequestDispatcher("viewCategories.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving categories.");
        }
    }
}

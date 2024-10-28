package org.example.webshop.ui.Servlets;

import org.example.webshop.bo.Category;
import org.example.webshop.bo.CategoryHandler;
import org.example.webshop.bo.Item;
import org.example.webshop.bo.ItemHandler;
import org.example.webshop.ui.DTOs.CategoryDTO;
import org.example.webshop.ui.DTOs.ItemInfoDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@WebServlet("/categoryServlet")
public class CategoryServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("createCategory".equals(action)) {
            createCategory(request, response);
        } else if ("editCategory".equals(action)) {
            editCategory(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("view".equals(action)) {
            viewCategories(request, response);
        }
    }

    private void createCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String categoryName = request.getParameter("categoryName");
        try {
            CategoryHandler.createCategory(categoryName);
            response.sendRedirect("viewCategories.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error adding category.");
        }
    }

    private void editCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));
        String newCategoryName = request.getParameter("categoryName");
        try {
            CategoryHandler.editCategory(categoryId, newCategoryName);
            response.sendRedirect("viewCategories.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error editing category.");
        }
    }

    private void viewCategories(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            // Hämta kategorier från BO-lagret
            List<Category> categories = CategoryHandler.getAllCategories();

            // Skapa en lista med CategoryDTO för att skicka till JSP
            List<CategoryDTO> categoryDTOs = new ArrayList<>();
            for (Category category : categories) {
                categoryDTOs.add(new CategoryDTO(category.getCategoryId(), category.getName()));
            }

            // Skicka DTO-listan till JSP-sidan
            request.setAttribute("categories", categoryDTOs);
            request.getRequestDispatcher("viewCategories.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving categories.");
        }
    }
}

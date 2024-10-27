package org.example.webshop.ui.Servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.webshop.bo.Category;
import org.example.webshop.bo.CategoryHandler;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/editCategoryServlet")
public class EditCategoryServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));
        String newCategoryName = request.getParameter("categoryName");

        try {
            // Anropa Category-klassens editCategory-metod
            CategoryHandler.editCategory(categoryId, newCategoryName);
            response.sendRedirect("viewCategoriesServlet");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error editing category.");
        }
    }
}

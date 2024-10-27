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

@WebServlet("/createCategoryServlet")
public class CreateCategoryServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String categoryName = request.getParameter("categoryName");


        try {
            CategoryHandler.createCategory(categoryName);
            System.out.println("Kategori som ska läggas till: " + categoryName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        response.sendRedirect("viewCategoriesServlet");
    }
}

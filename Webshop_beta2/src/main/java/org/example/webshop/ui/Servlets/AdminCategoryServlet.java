package org.example.webshop.ui.Servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.webshop.bo.Category;
import org.example.webshop.bo.ItemHandler;
import org.example.webshop.ui.ItemInfoDTO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


@WebServlet("/addCategory")
public class AdminCategoryServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String categoryName = request.getParameter("categoryName");

        Category.createCategory(categoryName);
        response.sendRedirect("adminCategory.jsp");
    }
}

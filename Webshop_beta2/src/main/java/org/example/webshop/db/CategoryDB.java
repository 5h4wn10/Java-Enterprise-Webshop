package org.example.webshop.db;

import org.example.webshop.bo.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDB {

    // Metod för att skapa en kategori i databasen
    public static void saveCategory(Category category) throws SQLException {
        Connection con = DBManager.getConnection();
        try {
            String query = "INSERT INTO categories (name) VALUES (?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, category.getName());
            ps.executeUpdate();
        } finally {
            con.close();
        }
    }

    // Metod för att uppdatera en kategori
    public static void updateCategory(int categoryId, String newName) throws SQLException {
        Connection con = DBManager.getConnection();
        try {
            String query = "UPDATE categories SET name = ? WHERE category_id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, newName);
            ps.setInt(2, categoryId);
            ps.executeUpdate();
        } finally {
            con.close();
        }
    }

    // Metod för att ta bort en kategori
    public static void deleteCategory(int categoryId) throws SQLException {
        Connection con = DBManager.getConnection();
        try {
            String query = "DELETE FROM categories WHERE category_id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, categoryId);
            ps.executeUpdate();
        } finally {
            con.close();
        }
    }

    // Metod för att hämta alla kategorier från databasen
    public static List<Category> getAllCategories() throws SQLException {
        List<Category> categories = new ArrayList<>();
        Connection con = DBManager.getConnection();
        try {
            String query = "SELECT * FROM categories";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int categoryId = rs.getInt("category_id");
                String name = rs.getString("name");
                Category category = new Category(categoryId, name);
                categories.add(category);
            }
        } finally {
            con.close();
        }
        return categories;
    }
}

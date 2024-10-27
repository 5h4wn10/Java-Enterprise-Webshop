package org.example.webshop.bo;

import java.sql.SQLException;
import java.util.List;

public class CategoryHandler {

    // Metod för att skapa en kategori
    public static void createCategory(String name) throws SQLException {
        Category.createCategory(name);
    }

    // Metod för att uppdatera en kategori
    public static void editCategory(int categoryId, String newName) throws SQLException {
        Category.editCategory(categoryId, newName);
    }

    // Metod för att ta bort en kategori
    public static void deleteCategory(int categoryId) throws SQLException {
        Category.deleteCategory(categoryId);
    }

    // Metod för att hämta alla kategorier
    public static List<Category> getAllCategories() throws SQLException {
        return Category.getAllCategories();
    }
}

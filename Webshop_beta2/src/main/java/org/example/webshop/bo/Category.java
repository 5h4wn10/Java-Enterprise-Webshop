package org.example.webshop.bo;

import org.example.webshop.db.CategoryDB;

import java.sql.SQLException;
import java.util.List;

public class Category {
    private int categoryId;
    private String name;

    // Konstruktor
    public Category(int categoryId, String name) {
        this.categoryId = categoryId;
        this.name = name;
    }

    // Getter och Setter metoder
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Metod för att skapa en kategori (anropar CategoryDB)
    public static void createCategory(String name) throws SQLException {
        Category newCategory = new Category(0, name);
        CategoryDB.saveCategory(newCategory);
    }

    // Metod för att uppdatera en kategori (anropar CategoryDB)
    public static void editCategory(int categoryId, String newName) throws SQLException {
        CategoryDB.updateCategory(categoryId, newName);
    }

    // Metod för att ta bort en kategori (anropar CategoryDB)
    public static void deleteCategory(int categoryId) throws SQLException {
        CategoryDB.deleteCategory(categoryId);
    }

    // Metod för att hämta alla kategorier (anropar CategoryDB)
    public static List<Category> getAllCategories() throws SQLException {
        return CategoryDB.getAllCategories();
    }
}

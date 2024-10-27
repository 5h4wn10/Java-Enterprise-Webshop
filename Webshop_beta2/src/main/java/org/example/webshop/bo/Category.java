package org.example.webshop.bo;

public class Category {
    private int categoryId;
    private String name;

    // Constructor
    public Category(int categoryId, String name) {
        this.categoryId = categoryId;
        this.name = name;
    }

    // Getter and Setter methods
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

    // Static methods to create, edit, and delete categories (implement DB interactions)
    public static void createCategory(String name) {
        // Logic for adding category to the database
    }

    public static void editCategory(int categoryId, String newName) {
        // Logic for editing a category in the database
    }

    public static void deleteCategory(int categoryId) {
        // Logic for deleting a category from the database
    }
}

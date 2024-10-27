package org.example.webshop.bo;

import org.example.webshop.db.ItemDB;

import java.sql.SQLException;
import java.util.Collection;

public class Item {
    private int id;
    private String name;
    private String description;
    private int price;
    private String group;
    private int categoryId;

    private int stock_quantity;

    // Skyddad konstruktor för att skapa ett Item-objekt (endast tillgänglig inom paketet eller av underklasser)
    protected Item(int id, String name, String description, int price, String group) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.group = group;
    }

    protected Item(int id, String name, String description, int price, String group, int stock_quantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.group = group;
        this.stock_quantity = stock_quantity;
    }

    protected Item(String name, int price, String description, int categoryId) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.categoryId = categoryId;
    }


    public int getStock_quantity() {
        return stock_quantity;
    }

    public void setStock_quantity(int stock_quantity) {
        this.stock_quantity = stock_quantity;
    }

    // Getter-metoder för att hämta egenskaperna
    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getGroup() {
        return group;
    }



    // Statisk metod för att skapa ett Item-objekt
    public static Item createItem(int id, String name, String description, int price, String group, int stock_quantity) {
        return new Item(id, name, description, price, group, stock_quantity);
    }


    static public Collection<Item> searchItems() throws SQLException {
        return ItemDB.searchItems(); // Hämtar alla objekt från ItemDB
    }
    static public Collection<Item> searchItems(String group) throws SQLException {
        return ItemDB.searchItems(group); // Hämtar från ItemDB
    }

    // skapar ett OrderItem från Item-objektet
    public static Item getItemById(int itemId) throws SQLException {
        // hämtat produkten från databasen genom ItemDB
        return ItemDB.getItemById(itemId);
    }

    // Metod för att uppdatera ett item genom ItemDB
    public static void updateItem(int id, String name, String description, int price, int stockQuantity) throws SQLException {
        ItemDB.updateItem(id, name, description, price, stockQuantity);
    }

    // Spara produkten i databasen genom att anropa ItemDB
    public void save() throws SQLException {
        // Kalla på ItemDB:s metod för att spara produkten
        ItemDB.createProduct(this);
    }
}

package org.example.webshop.bo;

import java.sql.SQLException;
import java.util.Collection;

public class Item {
    private int id;
    private String name;
    private String description;
    private int price;
    private String group; // Lägg till grupp

    // Skyddad konstruktor för att skapa ett Item-objekt (endast tillgänglig inom paketet eller av underklasser)
    protected Item(int id, String name, String description, int price, String group) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.group = group;
    }

    // Statisk metod för att skapa ett Item-objekt
    public static Item createItem(int id, String name, String description, int price, String group) {
        return new Item(id, name, description, price, group);
    }


    static public Collection<Item> searchItems() throws SQLException {
        return org.example.webshop.db.ItemDB.searchItems(); // Hämtar alla objekt från ItemDB
    }
    static public Collection<Item> searchItems(String group) throws SQLException {
        return org.example.webshop.db.ItemDB.searchItems(group); // Hämtar från ItemDB
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

    public String getGroup() {
        return group; // Getter för gruppen
    }
}

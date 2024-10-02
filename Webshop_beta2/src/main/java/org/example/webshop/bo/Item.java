package org.example.webshop.bo;

import org.example.webshop.db.ItemDB;

import java.sql.SQLException;
import java.util.Collection;

public class Item {
    private int id;
    private String name;
    private int price;
    private String group; // Lägg till grupp

    // Metod för att söka alla objekt
    static public Collection<Item> searchItems() throws SQLException {
        return ItemDB.searchItems(); // Hämtar från ItemDB
    }

    // Konstruktor för att skapa ett Item-objekt
    public Item(int id, String name, int price, String group) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.group = group;
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

    public String getGroup() {
        return group; // Getter för gruppen
    }
}

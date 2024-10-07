package org.example.webshop.bo;

import org.example.webshop.db.ShoppingCartDB;
import org.example.webshop.ui.ItemInfoDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private int userId;  // Koppla kundkorgen till en specifik användare
    private List<ItemInfoDTO> items;

    public ShoppingCart(int userId) {
        this.userId = userId;
        this.items = new ArrayList<>();
    }

    public int getUserId() {
        return userId;
    }

    // Ladda shoppingkorgen från databasen
    public void loadFromDB() throws SQLException {
        // Hämta data från ShoppingCartDB
        ShoppingCart loadedCart = ShoppingCartDB.loadCart(userId);
        this.items = loadedCart.getItems();  // Kopiera item-listan från den laddade korgen
    }

    // Spara shoppingkorgen till databasen
    public void saveToDB() throws SQLException {
        if (!items.isEmpty()) {  // Kolla om kundkorgen inte är tom
            ShoppingCartDB.saveCart(userId, this);
        }
    }

    // Lägg till ett objekt i korgen
    public void addItem(ItemInfoDTO item) {
        items.add(item);
    }

    // Ta bort ett objekt från korgen
    public void removeItem(ItemInfoDTO item) {
        items.remove(item);
    }

    // Hämta alla objekt i korgen
    public List<ItemInfoDTO> getItems() {
        return items;
    }

    public void clear() {
        items.clear();
    }

    // Hämta den totala summan av alla objekt i korgen
    public int getTotalPrice() {
        int total = 0;
        for (ItemInfoDTO item : items) {
            total += item.getPrice();
        }
        return total;
    }
}

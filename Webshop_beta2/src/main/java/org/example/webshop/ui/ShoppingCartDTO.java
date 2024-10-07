package org.example.webshop.ui;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartDTO {
    private int userId;  // Koppla kundkorgen till en specifik användare
    private List<ItemInfoDTO> items;
    private int totalPrice;

    // Konstruktorer
    public ShoppingCartDTO(int userId) {
        this.userId = userId;
        this.items = new ArrayList<>();
        this.totalPrice = 0;  // Initialisera totalpriset
    }

    public ShoppingCartDTO(int userId, List<ItemInfoDTO> items) {
        this.userId = userId;
        this.items = items;
        this.totalPrice = calculateTotalPrice();  // Beräkna totalpriset vid konstruktion
    }

    // Getter och setter för userId
    public int getUserId() {
        return userId;
    }



    // Lägg till ett objekt i korgen och uppdatera totalpriset
    public void addItem(ItemInfoDTO item) {
        items.add(item);
        totalPrice += item.getPrice();  // Uppdatera totalpriset
    }

    // Ta bort ett objekt från korgen och uppdatera totalpriset
    public void removeItem(ItemInfoDTO item) {
        items.remove(item);
        totalPrice -= item.getPrice();  // Uppdatera totalpriset
    }

    // Beräkna totalpriset för alla objekt i kundkorgen
    private int calculateTotalPrice() {
        int total = 0;
        for (ItemInfoDTO item : items) {
            total += item.getPrice();
        }
        return total;
    }

    // Getter för items
    public List<ItemInfoDTO> getItems() {
        return items;
    }

    // Getter för totalPrice
    public int getTotalPrice() {
        return totalPrice;
    }

    // Töm kundkorgen och nollställ totalpriset
    public void clear() {
        items.clear();
        totalPrice = 0;
    }
}

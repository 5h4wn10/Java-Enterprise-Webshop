package org.example.webshop.bo;

import org.example.webshop.ui.ItemInfoDTO;
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

    // Ingen databasladdning eller sparning behövs längre
    // Ta bort loadFromDB och saveToDB metoder

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

package org.example.webshop.bo;

import org.example.webshop.ui.ItemInfoDTO;
import java.util.List;

public class ShoppingCartHandler {

    // Lägg till ett objekt i shoppingkorgen (ingen databasuppdatering behövs)
    public void addItemToCart(ShoppingCart cart, ItemInfoDTO item) {
        cart.addItem(item);
        // Ingen spara till databas här
    }

    // Ta bort ett objekt från korgen (ingen databasuppdatering behövs)
    public void removeItemFromCart(ShoppingCart cart, ItemInfoDTO item) {
        cart.removeItem(item);
        // Ingen spara till databas här
    }

    // Hämta alla objekt från shoppingkorgen
    public List<ItemInfoDTO> getCartItems(ShoppingCart cart) {
        return cart.getItems();
    }

    // Räkna ut den totala summan av korgen
    public int calculateTotalPrice(ShoppingCart cart) {
        return cart.getTotalPrice();
    }

    // Ta bort funktionen för att ladda korgen från databasen
    // Ingen databasladdning behövs
    public ShoppingCart createCart(int userId) {
        return new ShoppingCart(userId);  // Skapa en ny korg utan att ladda från databasen
    }

    // Töm shoppingkorgen (ingen databasuppdatering behövs)
    public void clearCart(ShoppingCart cart) {
        cart.clear();  // Töm korgen
        // Ingen spara till databas här
    }
}

package org.example.webshop.bo;

import org.example.webshop.ui.ItemInfoDTO;
import org.example.webshop.ui.ShoppingCartDTO;

import java.sql.SQLException;
import java.util.List;

public class ShoppingCartHandler {

    // Lägg till ett objekt i shoppingkorgen och spara till DB
    public void addItemToCart(ShoppingCart cart, ItemInfoDTO item) throws SQLException {
        cart.addItem(item);
        cart.saveToDB();  // Spara korgen till databasen efter uppdatering
    }



    // Ta bort ett objekt från korgen och spara till DB
    public void removeItemFromCart(ShoppingCart cart, ItemInfoDTO item) throws SQLException {
        cart.removeItem(item);
        cart.saveToDB();  // Spara korgen till databasen efter uppdatering
    }

    // Hämta alla objekt från shoppingkorgen
    public List<ItemInfoDTO> getCartItems(ShoppingCart cart) {
        return cart.getItems();
    }

    // Räkna ut den totala summan av korgen
    public int calculateTotalPrice(ShoppingCart cart) {
        return cart.getTotalPrice();
    }

    // Ladda shoppingkorgen för en specifik användare
    public ShoppingCart loadCart(int userId) throws SQLException {
        ShoppingCart cart = new ShoppingCart(userId);
        cart.loadFromDB();  // Ladda korgen från databasen
        return cart;
    }

    // Töm shoppingkorgen och radera från databasen
    public void clearCart(ShoppingCart cart) throws SQLException {
        cart.clear();  // Töm korgen först
        cart.saveToDB();  // Spara den tömda korgen till databasen
    }

}

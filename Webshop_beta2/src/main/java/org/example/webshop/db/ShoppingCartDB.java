package org.example.webshop.db;

import org.example.webshop.bo.ShoppingCart;
import org.example.webshop.ui.ItemInfoDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCartDB {

    // Spara shoppingkorg till databasen
    public static void saveCart(int userId, ShoppingCart cart) throws SQLException {
        Connection con = DBManager.getConnection();
        try {
            // Radera gammal korg för användaren (om den finns) för att uppdatera med nya produkter
            String deleteQuery = "DELETE FROM shopping_cart WHERE user_id = ?";
            PreparedStatement deleteStmt = con.prepareStatement(deleteQuery);
            deleteStmt.setInt(1, userId);
            deleteStmt.executeUpdate();

            if (!cart.getItems().isEmpty()) {
                // Lägg till nya produkter i korgen om det finns några
                String insertQuery = "INSERT INTO shopping_cart (user_id, item_id, quantity, added_at) VALUES (?, ?, ?, NOW())";
                PreparedStatement insertStmt = con.prepareStatement(insertQuery);

                for (ItemInfoDTO item : cart.getItems()) {
                    insertStmt.setInt(1, userId);
                    insertStmt.setInt(2, item.getId());
                    insertStmt.setInt(3, 1); // Du kan ändra detta om du har stöd för kvantitet i din korg
                    insertStmt.addBatch();
                }
                insertStmt.executeBatch(); // Kör alla insättningar i ett batch
            }
        } finally {
            con.close();
        }
    }

    // Hämta användarens shoppingkorg från databasen
    public static ShoppingCart loadCart(int userId) throws SQLException {
        Connection con = DBManager.getConnection();
        ShoppingCart cart = new ShoppingCart(userId);

        try {
            String query = "SELECT item_id, quantity FROM shopping_cart WHERE user_id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int itemId = rs.getInt("item_id");
                int quantity = rs.getInt("quantity");

                // Hämta produkten från din ItemDB klass för att skapa ett ItemInfoDTO
                ItemInfoDTO item = ItemDB.getItemInfoById(itemId);

                // Lägg till produkten i shoppingkorgen
                for (int i = 0; i < quantity; i++) {
                    cart.addItem(item);
                }
            }
        } finally {
            con.close();
        }
        return cart; // Returnera shoppingkorgen
    }
}

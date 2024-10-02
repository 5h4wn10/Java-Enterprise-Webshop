package org.example.webshop.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import org.example.webshop.bo.Item;

public class ItemDB {

    // Metod som hämtar alla objekt oavsett grupp
    public static Collection<Item> searchItems() throws SQLException {
        Collection<Item> items = new ArrayList<>();
        Connection con = DBManager.getConnection();

        try {
            // Uppdaterad SQL-fråga för att hämta alla objekt med deras grupp
            String query = "SELECT item_id, name, price, item_group_id FROM item";
            PreparedStatement ps = con.prepareStatement(query);

            // Utför SQL-frågan
            ResultSet rs = ps.executeQuery();

            // Läser av resultatet
            while (rs.next()) {
                int id = rs.getInt("item_id");
                String name = rs.getString("name");
                int price = rs.getInt("price");
                String group = rs.getString("item_group_id"); // Lägg till gruppen

                // Skapar ett Item-objekt och lägger till i listan
                Item item = new Item(id, name, price, group);
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { con.close(); } catch (SQLException e) { e.printStackTrace(); }
        }

        return items;
    }
}

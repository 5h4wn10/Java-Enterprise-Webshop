package org.example.webshop.db;

import java.sql.*;
import java.util.Collection;
import java.util.Vector;
import org.example.webshop.bo.Item;
import org.example.webshop.ui.ItemInfoDTO;

public class ItemDB extends Item {

    // Konstruktor, använder skyddad konstruktor från Item-klassen
    private ItemDB(int id, String name, String description, int price, String group) {
        super(id, name, description, price, group);
    }

    // Metod som hämtar objekt baserat på grupp
    public static Collection<Item> searchItems(String item_group) throws SQLException {
        Vector<Item> v = new Vector<>();
        Connection con = DBManager.getConnection();

        try {
            String query = "SELECT item_id, name, description, price, item_group_id FROM item WHERE item_group_id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, item_group);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("item_id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                int price = rs.getInt("price");
                String group = rs.getString("item_group_id");

                v.add(new ItemDB(id, name, description, price, group));
            }
        } finally {
            con.close();
        }

        return v;
    }

    // Metod som hämtar alla objekt utan att filtrera på grupp
    public static Collection<Item> searchItems() throws SQLException {
        Vector<Item> v = new Vector<>();
        Connection con = DBManager.getConnection();

        try {
            // SQL-fråga utan filter för att hämta alla items
            String query = "SELECT item_id, name, description, price, item_group_id FROM item";
            PreparedStatement ps = con.prepareStatement(query);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("item_id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                int price = rs.getInt("price");
                String group = rs.getString("item_group_id");

                v.add(new ItemDB(id, name, description, price, group));
            }
        } finally {
            con.close();
        }

        return v;
    }


    // Metod för att hämta ett ItemInfoDTO baserat på item_id
    public static ItemInfoDTO getItemInfoById(int itemId) throws SQLException {
        Connection con = DBManager.getConnection();
        ItemInfoDTO item = null;

        try {
            String query = "SELECT item_id, name, description, price, item_group_id FROM item WHERE item_id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, itemId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("item_id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                int price = rs.getInt("price");
                String group = rs.getString("item_group_id");

                item = new ItemInfoDTO(id, name, description, price, group);
            }
        } finally {
            con.close();
        }

        return item;
    }
}

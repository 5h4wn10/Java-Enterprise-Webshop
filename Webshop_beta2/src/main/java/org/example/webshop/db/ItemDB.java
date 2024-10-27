package org.example.webshop.db;

import java.sql.*;
import java.util.Collection;
import java.util.Vector;
import org.example.webshop.bo.Item;
import org.example.webshop.ui.ItemInfoDTO;

public class ItemDB extends Item {

    // Konstruktor, använder skyddad konstruktor från Item-klassen
    private ItemDB(int id, String name, String description, int price, String group, int stockQuantity) {
        super(id, name, description, price, group, stockQuantity);
    }


    // Metod som hämtar objekt baserat på grupp
    public static Collection<Item> searchItems(String item_group) throws SQLException {
        Vector<Item> v = new Vector<>();
        Connection con = DBManager.getConnection();

        try {
            String query = "SELECT item_id, name, description, price, stock_quantity, item_group_id FROM item WHERE item_group_id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, item_group);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("item_id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                int price = rs.getInt("price");
                int stockQuantity = rs.getInt("stock_quantity");
                String group = rs.getString("item_group_id");

                v.add(new ItemDB(id, name, description, price, group, stockQuantity));
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
            String query = "SELECT item_id, name, description, price, stock_quantity, item_group_id FROM item";
            PreparedStatement ps = con.prepareStatement(query);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("item_id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                int price = rs.getInt("price");
                int stockQuantity = rs.getInt("stock_quantity");
                String group = rs.getString("item_group_id");

                v.add(new ItemDB(id, name, description, price, group, stockQuantity));
            }
        } finally {
            con.close();
        }

        return v;
    }


    // Metod för att hämta produkt från databasen baserat på itemId
    public static Item getItemById(int itemId) throws SQLException {
        Connection con = DBManager.getConnection();
        Item item = null;

        try {
            String query = "SELECT * FROM item WHERE item_id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, itemId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("item_id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                int price = rs.getInt("price");
                String group = rs.getString("item_group_id");
                int stockQuantity = rs.getInt("stock_quantity");

                // Skapa ett OrderItem med korrekt lagersaldo
                item = createItem(id,name,description,price,group,stockQuantity);
            }

        } finally {
            DBManager.closeConnection(con);
        }

        return item;
    }

    // Metod för att uppdatera lagersaldo för en specifik produkt
    public static void updateStockQuantity(int itemId, int quantity, Connection con) throws SQLException {
        String query = "UPDATE item SET stock_quantity = stock_quantity - ? WHERE item_id = ?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, quantity);
        ps.setInt(2, itemId);
        ps.executeUpdate();
        ps.close();
    }

    // Uppdaterar en vara i databasen
    public static void updateItem(int itemId, String name, String description, int price, int stockQuantity) throws SQLException {
        Connection con = null;
        try {
            con = DBManager.getConnection();
            con.setAutoCommit(false);  // Avstängd auto-commit

            String query = "UPDATE item SET name = ?, description = ?, price = ?, stock_quantity = ? WHERE item_id = ?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, name);
            ps.setString(2, description);
            ps.setInt(3, price);
            ps.setInt(4, stockQuantity);
            ps.setInt(5, itemId);

            int rowsAffected = ps.executeUpdate();
            System.out.println("Updaten gick bra, rows affected: " + rowsAffected);

            con.commit();  // Viktigt! Commit transaktionen
        } catch (SQLException e) {
            if (con != null) {
                con.rollback();  // Rollback vid fel
            }
            System.err.println("Failed to update item: " + e.getMessage());
            throw e;
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }

    public static void deleteItem(int itemId) throws SQLException {
        String query = "DELETE FROM item WHERE item_id = ?";

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, itemId);
            int rowsAffected = ps.executeUpdate();
            System.out.println("Item deleted, rows affected: " + rowsAffected);
        }
    }


    // Metod för att spara en produkt i databasen
    public static void saveItem(Item item) throws SQLException {
        Connection con = DBManager.getConnection();
        try {
            String query = "INSERT INTO products (name, price, description, category_id) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, item.getName());
            ps.setInt(2, item.getPrice());
            ps.setString(3, item.getDescription());
            ps.setInt(4, item.getCategoryId());
            ps.executeUpdate();
        } finally {
            con.close();
        }
    }


}

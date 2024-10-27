package org.example.webshop.db;

import org.example.webshop.bo.Order;
import org.example.webshop.bo.OrderItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDB {

    // Metod för att spara en order i databasen
    public static void saveOrder(Order order) throws SQLException {
        Connection con = null;
        try {
            con = DBManager.getConnection();
            con.setAutoCommit(false); // Stäng av autocommit för att hantera transaktionen manuellt

            // Lägg till ordern i orders-tabellen
            String query = "INSERT INTO orders (user_id, total_price) VALUES (?, ?)";
            PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, order.getUserId());
            ps.setInt(2, order.getTotalPrice()); // Beräkna totalpris

            ps.executeUpdate();

            // Få det genererade order-id:t
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                order.setOrderId(rs.getInt(1));  // Sätt det genererade order-id:t
            }

            ps.close();

            // Spara orderobjekten i order_items-tabellen
            for (OrderItem item : order.getItems()) {
                saveOrderItem(con, order.getOrderId(), item);
            }

            // Uppdatera lagersaldot för alla objekt i ordern
            updateStockAfterOrder(con, order.getItems());

            con.commit(); // Commit transaktionen

        } catch (SQLException e) {
            if (con != null) {
                con.rollback(); // Rulla tillbaka om något går fel
            }
            throw e;
        } finally {
            if (con != null) {
                con.setAutoCommit(true); // Återställ autocommit
                con.close();
            }
        }
    }

    // Metod för att spara varje OrderItem
    private static void saveOrderItem(Connection con, int orderId, OrderItem item) throws SQLException {
        String query = "INSERT INTO order_items (order_id, item_id, quantity, price) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, orderId);
        ps.setInt(2, item.getId());
        ps.setInt(3, item.getOrderedQuantity());
        ps.setInt(4, item.getPrice());
        ps.executeUpdate();
        ps.close();
    }

    // Metod för att uppdatera lagersaldot efter att en order sparats
    private static void updateStockAfterOrder(Connection con, List<OrderItem> items) throws SQLException {
        for (OrderItem item : items) {
            ItemDB.updateStockQuantity(item.getId(), item.getOrderedQuantity(), con);
        }
    }



    // Retrieve an order from the database by ID
    public static Order getOrderById(int orderId) throws SQLException {
        Connection con = DBManager.getConnection();
        Order order = null;

        try {
            String query = "SELECT * FROM orders WHERE order_id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("user_id");
                order = new Order(orderId, userId);

                // Load all items for the order
                loadOrderItems(con, order);
            }
        } finally {
            DBManager.closeConnection(con);
        }

        return order;
    }

    // Load all OrderItems for an order
    private static void loadOrderItems(Connection con, Order order) throws SQLException {
        String query = "SELECT * FROM order_items WHERE order_id = ?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, order.getOrderId());
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int itemId = rs.getInt("item_id");
            int quantity = rs.getInt("quantity");
            int price = rs.getInt("price");

            // Create an OrderItem and add it to the order
            OrderItem item = new OrderItem(itemId, "", "", price, "", 0, quantity);  // Use appropriate values for the fields
            order.addItem(item);
        }
    }

    // Hämta ordrar baserat på status (t.ex. Pending, Packed)
    public static List<Order> getOrdersByStatus(String status) throws SQLException {
        List<Order> orders = new ArrayList<>();
        Connection con = DBManager.getConnection();

        try {
            String query = "SELECT * FROM orders WHERE status = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, status);

            System.out.println("Executing query: " + query + " with status = " + status); // Lägg till utskrift för att se frågan

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                int userId = rs.getInt("user_id");
                String orderDate = rs.getString("order_date");
                int totalPrice = rs.getInt("total_price");

                // Skapa en Order och lägg till i listan
                Order order = new Order(orderId, userId, orderDate, new ArrayList<>(), totalPrice);
                orders.add(order);
            }

            System.out.println("Found " + orders.size() + " orders with status: " + status); // Lägg till utskrift för att se hur många ordrar som hämtades
        } finally {
            con.close();
        }

        return orders;
    }



    // Uppdatera status för en order (exempelvis från 'Pending' till 'Packed')
    public static void updateOrderStatus(int orderId, String status) throws SQLException {
        Connection con = DBManager.getConnection();

        try {
            con.setAutoCommit(false); // Om du vill stänga av autocommit
            String query = "UPDATE orders SET status = ? WHERE order_id = ?";
            System.out.println("OrderDB utförs och " + orderId + " ska ställas till " + status);
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, status);
            ps.setInt(2, orderId);
            ps.executeUpdate();
            con.commit();  // Viktigt om autocommit är avstängt
        } catch (SQLException e) {
            if (con != null) {
                con.rollback(); // Om något går fel, rulla tillbaka transaktionen
            }
            throw e;
        } finally {
            con.close();
        }
    }

    // Hämta order items (om du har en separat tabell för detta)
    private static List<OrderItem> getOrderItems(int orderId) throws SQLException {
        List<OrderItem> items = new ArrayList<>();
        Connection con = DBManager.getConnection();

        try {
            String query = "SELECT * FROM order_items WHERE order_id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int itemId = rs.getInt("item_id");
                int quantity = rs.getInt("quantity");
                String name = rs.getString("name");
                int price = rs.getInt("price");
                String group = rs.getString("group");

                OrderItem item = new OrderItem(itemId, name, null, price, group, 0, quantity); // OrderItem constructor
                items.add(item);
            }
        } finally {
            con.close();
        }

        return items;
    }


}

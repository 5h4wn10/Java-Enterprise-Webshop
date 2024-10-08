package org.example.webshop.db;

import org.example.webshop.bo.Order;
import org.example.webshop.bo.OrderItem;

import java.sql.*;

public class OrderDB {

    // Save order to the database
    public static void saveOrder(Order order) throws SQLException {
        Connection con = DBManager.getConnection();
        try {
            String query = "INSERT INTO orders (user_id, total_price) VALUES (?, ?)";
            PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, order.getUserId());
            ps.setInt(2, order.getTotalPrice());
            ps.executeUpdate();

            // Get generated order ID
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                order.setOrderId(rs.getInt(1));  // Set the generated orderId
            }

            // Save each OrderItem
            for (OrderItem item : order.getItems()) {
                saveOrderItem(con, order.getOrderId(), item);
            }

        } finally {
            DBManager.closeConnection(con);
        }
    }

    // Save an individual OrderItem to the database
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
}

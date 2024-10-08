package org.example.webshop.bo;

import org.example.webshop.db.OrderDB;
import org.example.webshop.db.ItemDB;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private int orderId;
    private int userId;
    private List<OrderItem> items;
    private String orderDate;

    // Constructor for creating a new order
    public Order(int orderId, int userId) {
        this.orderId = orderId;
        this.userId = userId;
        this.items = new ArrayList<>();
    }

    // Constructor for handling an existing order
    public Order(int orderId, int userId, String orderDate, List<OrderItem> items) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderDate = orderDate;
        this.items = items;
    }

    public Order(int userId) {
    }

    public Order(int userId, List<OrderItem> items) {
        this.userId = userId;
        this.items = items;
    }


    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getUserId() {
        return userId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    // Add an item to the order
    public void addItem(OrderItem item) {
        items.add(item);
    }

    // Get all items in the order
    public List<OrderItem> getItems() {
        return items;
    }

    // Calculate total price of the order
    public int getTotalPrice() {
        int total = 0;
        for (OrderItem item : items) {
            total += item.calculateTotalPrice();
        }
        return total;
    }

    // Save order to database
    public void saveOrder() throws SQLException {
        OrderDB.saveOrder(this);
    }

    // Update stock after order completion
    public void updateStockAfterOrder() throws SQLException {
        for (OrderItem item : items) {
            ItemDB.updateStockQuantity(item.getId(), item.getOrderedQuantity());
        }
    }

    // Retrieve an order by ID
    public static Order getOrderById(int orderId) throws SQLException {
        return OrderDB.getOrderById(orderId);
    }
}

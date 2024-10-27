package org.example.webshop.bo;

import org.example.webshop.db.OrderDB;
import org.example.webshop.db.ItemDB;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private int orderId;
    private int userId;
    private List<OrderItem> items;
    private String orderDate;
    private int totalprice;

    // Constructor for creating a new order
    public Order(int orderId, int userId) {
        this.orderId = orderId;
        this.userId = userId;
        this.items = new ArrayList<>();
    }

    // Constructor for handling an existing order
    public Order(int orderId, int userId, String orderDate, List<OrderItem> items, int totalprice) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderDate = orderDate;
        this.items = items;
        this.totalprice = totalprice;
    }



    public Order(int userId) {
    }

    public Order(int userId, List<OrderItem> items) {
        this.userId = userId;
        this.items = items;
    }


    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public int getTotalpriceGeneral() {
        return totalprice;
    }

    public void setTotalprice(int totalprice) {
        this.totalprice = totalprice;
    }

    // Calculate total price of the order
    public int getTotalPrice() {
        int total = 0;
        for (OrderItem item : items) {
            total += item.calculateTotalPrice();
        }
        return total;
    }


    // Add an item to the order
    public void addItem(OrderItem item) {
        items.add(item);
    }

    // Metod för att spara en order
    public void saveOrder() throws SQLException {
        OrderDB.saveOrder(this);
    }

    // Update stock efter order completion
    public void updateStockAfterOrder(Connection con) throws SQLException {
        for (OrderItem item : items) {
            // Uppdatera lagersaldot för varje produkt i ordern med hjälp av Connection-objektet
            ItemDB.updateStockQuantity(item.getId(), item.getOrderedQuantity(), con);
        }
    }

    // Retrievar en order efter ID
    public static Order getOrderById(int orderId) throws SQLException {
        return OrderDB.getOrderById(orderId);
    }

    // Hämta alla ordrar med status "Pending"
    // I OrderHandler, när du hämtar pending orders:
    public static List<Order> getPendingOrders() throws SQLException {
        return OrderDB.getOrdersByStatus("Pending");
    }


    // Markera en order som packad
    public static void markOrderAsPacked(int orderId) throws SQLException {
        System.out.println("Order utförs");
        OrderDB.updateOrderStatus(orderId, "Packed");
    }
}

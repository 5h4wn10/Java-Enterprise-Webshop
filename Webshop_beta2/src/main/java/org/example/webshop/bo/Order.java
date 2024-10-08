package org.example.webshop.bo;

import org.example.webshop.db.DBManager;
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

    // Metod för att spara en order och uppdatera lagersaldot
    public void saveOrder() throws SQLException {
        Connection con = null;
        try {
            // Hämta en databaskoppling och starta en transaktion
            con = DBManager.getConnection();
            con.setAutoCommit(false); // Stäng av autocommit för att hantera transaktionen manuellt

            // Spara ordern och uppdatera lagersaldot i en transaktion
            OrderDB.saveOrder(this, con);

            // Uppdatera lagersaldo för varje objekt i ordern
            updateStockAfterOrder(con);

            // Om allt går bra, commit transaktionen
            con.commit();
        } catch (SQLException e) {
            // Om något går fel, rulla tillbaka transaktionen
            if (con != null) {
                con.rollback();
            }
            throw e; // Vidarebefordra undantaget
        } finally {
            if (con != null) {
                con.setAutoCommit(true); // Återställ autocommit
                con.close();
            }
        }
    }


    // Update stock after order completion
    public void updateStockAfterOrder(Connection con) throws SQLException {
        for (OrderItem item : items) {
            // Uppdatera lagersaldot för varje produkt i ordern med hjälp av Connection-objektet
            ItemDB.updateStockQuantity(item.getId(), item.getOrderedQuantity(), con);
        }
    }

    // Retrieve an order by ID
    public static Order getOrderById(int orderId) throws SQLException {
        return OrderDB.getOrderById(orderId);
    }
}

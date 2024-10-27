package org.example.webshop.bo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OrderHandler {

    // Skapa en ny order och spara den via Order-klassen
    public void createOrder(int userId, Order order) throws SQLException {
        order.saveOrder(); // Delegerar till Order-klassens metod för att spara ordern och uppdatera lagret
    }

    // Lägg till ett objekt i ordern
    public void addItemToOrder(Order order, OrderItem item) {
        order.addItem(item); // Lägg till OrderItem via Order-klassens metod
    }

    // Beräkna totalpriset för ordern
    public int calculateTotalOrderPrice(Order order) {
        return order.getTotalPrice(); // Delegerar till Order-klassens metod för totalpris
    }

    // Hämta en order från databasen genom Order-klassen
    public Order getOrderById(int orderId) throws SQLException {
        return Order.getOrderById(orderId); // Delegerar till Order-klassens metod
    }

    // Hantera lagersaldo (detta kallas när du minskar lagret vid ordern)
    public void reduceStock(Order order, Connection con) throws SQLException {
        order.updateStockAfterOrder(con); // Låter Order-klassen hantera lagersaldot
    }

    // Hämta alla ordrar med status "Pending"
    public static List<Order> getPendingOrders() throws SQLException {
        return Order.getPendingOrders();
    }

    // Markera en order som packad
    public static void markOrderAsPacked(int orderId) throws SQLException {
        System.out.println("OrderHandler utförs");
        Order.markOrderAsPacked(orderId);
    }


}

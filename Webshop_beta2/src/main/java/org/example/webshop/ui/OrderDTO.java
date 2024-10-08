package org.example.webshop.ui;

import java.util.List;

public class OrderDTO {
    private int orderId;
    private int userId;
    private List<OrderItemDTO> items;
    private int totalPrice;
    private String orderDate;

    // Konstruktor
    public OrderDTO(int orderId, int userId, List<OrderItemDTO> items, int totalPrice, String orderDate) {
        this.orderId = orderId;
        this.userId = userId;
        this.items = items;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
    }

    public OrderDTO(int orderId, int userId, List<OrderItemDTO> items, int totalPrice) {
        this.orderId = orderId;
        this.userId = userId;
        this.items = items;
        this.totalPrice = totalPrice;
    }

    // Getter för orderId
    public int getOrderId() {
        return orderId;
    }

    // Getter för userId
    public int getUserId() {
        return userId;
    }

    // Getter för items
    public List<OrderItemDTO> getItems() {
        return items;
    }

    // Getter för totalPrice
    public int getTotalPrice() {
        return totalPrice;
    }

    // Getter för orderDate
    public String getOrderDate() {
        return orderDate;
    }
}

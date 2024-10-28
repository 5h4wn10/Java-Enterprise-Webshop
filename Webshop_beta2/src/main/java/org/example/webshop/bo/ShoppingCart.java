package org.example.webshop.bo;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private List<OrderItem> items;

    private int userId;

    public ShoppingCart() {
        this.items = new ArrayList<>();
    }

    public ShoppingCart(int userId) {
        this.userId = userId;
        this.items = new ArrayList<>();
    }

    public void addItem(OrderItem item) {
        items.add(item);
    }

    public void removeItem(OrderItem item) {
        items.remove(item);
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public int getTotalPrice() {
        int total = 0;
        for (OrderItem item : items) {
            total += item.calculateTotalPrice();
        }
        return total;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void clear() {
        items.clear();
    }
}

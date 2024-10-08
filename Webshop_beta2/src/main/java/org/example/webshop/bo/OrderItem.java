package org.example.webshop.bo;

import org.example.webshop.ui.ItemInfoDTO;

public class OrderItem extends Item {
    private int orderedQuantity;

    // Constructor extending Item class
    public OrderItem(int id, String name, String description, int price, String group, int stockQuantity, int orderedQuantity) {
        super(id, name, description, price, group, stockQuantity);
        this.orderedQuantity = orderedQuantity;
    }

    public int getOrderedQuantity() {
        return orderedQuantity;
    }

    public void setOrderedQuantity(int orderedQuantity) {
        this.orderedQuantity = orderedQuantity;
    }

    // Calculate total price for this order item
    public int calculateTotalPrice() {
        return getPrice() * orderedQuantity;
    }

}

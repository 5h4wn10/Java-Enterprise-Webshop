package org.example.webshop.bo;

import org.example.webshop.ui.ItemInfoDTO;

public class OrderItem extends Item {
    private int orderedQuantity;

    // Constructor extending Item class with stockQuantity and orderedQuantity
    public OrderItem(int id, String name, String description, int price, String group, int stockQuantity, int orderedQuantity) {
        super(id, name, description, price, group, stockQuantity); // This correctly sets stockQuantity in Item class
        this.orderedQuantity = orderedQuantity;
    }

    // Second constructor that does not require stockQuantity, if it's not relevant for some cases
    public OrderItem(int id, String name, String description, int price, String group, int orderedQuantity) {
        super(id, name, description, price, group, 0); // Set stockQuantity to 0 or another default value
        this.orderedQuantity = orderedQuantity; // Correctly set the ordered quantity
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

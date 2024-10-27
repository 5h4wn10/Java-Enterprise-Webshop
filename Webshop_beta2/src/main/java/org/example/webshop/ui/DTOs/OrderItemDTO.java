package org.example.webshop.ui.DTOs;

public class OrderItemDTO {
    private int itemId;
    private String name;
    private String description;
    private int price;
    private String group;
    private int orderedQuantity;

    // Constructor
    public OrderItemDTO(int itemId, String name, String description, int price, String group, int orderedQuantity) {
        this.itemId = itemId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.group = group;
        this.orderedQuantity = orderedQuantity;
    }

    public OrderItemDTO(int itemId, String name, String description, int price, int orderedQuantity) {
        this.itemId = itemId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.orderedQuantity = orderedQuantity;
    }

    // Getters and Setters
    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public int getOrderedQuantity() {
        return orderedQuantity;
    }

    public void setOrderedQuantity(int orderedQuantity) {
        this.orderedQuantity = orderedQuantity;
    }

    // Method to calculate total price for the order item
    public int calculateTotalPrice() {
        return price * orderedQuantity;
    }
}

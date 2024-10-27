package org.example.webshop.ui.DTOs;

import org.example.webshop.bo.OrderItem;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCartDTO {
    private List<OrderItemDTO> items;
    private int totalPrice;
    private int userId;

    // Konstruktor med en lista av OrderItem, konverterar OrderItem till OrderItemDTO
    public ShoppingCartDTO(int userId, List<OrderItem> orderItems) {
        this.userId = userId;
        this.items = convertToOrderItemDTO(orderItems);
        this.totalPrice = calculateTotalPrice(orderItems);
    }

    public ShoppingCartDTO(int userId) {
        this.userId = userId;
        this.items = new ArrayList<>();
        this.totalPrice = 0;
    }

    // Metod för att konvertera OrderItem till OrderItemDTO
    private List<OrderItemDTO> convertToOrderItemDTO(List<OrderItem> orderItems) {
        List<OrderItemDTO> itemDTOs = new ArrayList<>();
        for (OrderItem orderItem : orderItems) {
            OrderItemDTO dto = new OrderItemDTO(
                    orderItem.getId(),
                    orderItem.getName(),
                    orderItem.getDescription(),
                    orderItem.getPrice(),
                    orderItem.getGroup(),
                    orderItem.getOrderedQuantity()
            );
            itemDTOs.add(dto);
        }
        return itemDTOs;
    }

    // Beräkna det totala priset för OrderItems
    private int calculateTotalPrice(List<OrderItem> orderItems) {
        int total = 0;
        for (OrderItem item : orderItems) {
            total += item.calculateTotalPrice();
        }
        return total;
    }

    // Getters och setters
    public List<OrderItemDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDTO> items) {
        this.items = items;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void addItem(OrderItemDTO item) {
        this.items.add(item);
        this.totalPrice += item.getPrice() * item.getOrderedQuantity(); // Uppdatera totalpris
    }

    public void clear() {
        items.clear();
    }
}

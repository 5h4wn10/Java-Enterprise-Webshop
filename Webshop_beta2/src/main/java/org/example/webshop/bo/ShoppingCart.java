package org.example.webshop.bo;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private List<Item> items;

    public ShoppingCart() {
        this.items = new ArrayList<>();
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public List<Item> getItems() {
        return items;
    }

    public void clear() {
        items.clear();
    }

    public int getTotalPrice() {
        return items.stream().mapToInt(Item::getPrice).sum();
    }
}

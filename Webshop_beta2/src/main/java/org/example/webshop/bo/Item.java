package org.example.webshop.bo;

import org.example.webshop.db.ItemDB;
import java.util.Collection;

public class Item {
    private int id;
    private String name;
    private int price;

    static public Collection searchItems(String group) {
        return ItemDB.searchItems(group);
    }

    public Item(int id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}

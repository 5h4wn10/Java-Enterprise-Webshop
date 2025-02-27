package org.example.webshop.ui;

public class ItemInfoDTO {
    private int id;
    private String name;
    private String description;
    private int price;
    private String group;
    private int stock_quantity;

    // Konstruktor för ItemInfo
    public ItemInfoDTO(int id, String name, String description, int price, String group) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.group = group;
    }

    public ItemInfoDTO(int id, String name, String description, int price, String group, int stock_quantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.group = group;
        this.stock_quantity = stock_quantity;
    }

    // Getter och Setter metoder
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getStock_quantity() {
        return stock_quantity;
    }

    public void setStock_quantity(int stock_quantity) {
        this.stock_quantity = stock_quantity;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}

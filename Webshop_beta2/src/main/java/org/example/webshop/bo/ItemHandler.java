package org.example.webshop.bo;

import org.example.webshop.ui.DTOs.ItemInfoDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class ItemHandler {

    // Metod som hämtar objekt baserat på en grupp och returnerar en lista av ItemInfoDTO
    public static List<ItemInfoDTO> getItemsWithGroup(String group) throws SQLException {
        // Hämta produkterna från Item-klassen baserat på grupp
        Collection<Item> items = Item.searchItems(group);

        // Skapa en lista för att lagra resultaten
        List<ItemInfoDTO> itemList = new ArrayList<>();

        // Loopa igenom och lägg till varje objekt i listan som ItemInfoDTO
        Iterator<Item> it = items.iterator();
        while (it.hasNext()) {
            Item item = it.next();
            // Skapa ett ItemInfoDTO-objekt och lägg till i listan
            itemList.add(new ItemInfoDTO(item.getId(), item.getName(), item.getDescription(), item.getPrice(), item.getGroup()));
        }

        return itemList;  // Returnerar hela listan
    }

    public static List<ItemInfoDTO> getAllItems() throws SQLException {
        // Hämtar alla produkter från Item-klassen
        Collection<Item> items = Item.searchItems();

        // Skapa en lista för att lagra resultaten
        List<ItemInfoDTO> itemList = new ArrayList<>();

        // den loopar igenom och lägger till varje objekt i listan som ItemInfoDTO
        for (Item item : items) {
            itemList.add(new ItemInfoDTO(item.getId(), item.getName(), item.getDescription(), item.getPrice(), item.getGroup(), item.getStock_quantity()));
        }

        return itemList;  // Returnerar hela listan
    }

    // Metod för att hämta produkt baserat på itemId genom Item-klassen
    public static Item getItemById(int itemId) throws SQLException {
        System.out.println("Fetching item with ID: " + itemId);
        // Använd Item-klassen för att hämta produktdata
        return Item.getItemById(itemId);
    }



    //uppdaterar en vara //admin eller warehouse-staff kan göra det
    public static void updateItem(int id, String name, String description, int price, int stock) throws SQLException {
        Item.updateItem(id, name, description, price, stock);
    }


    public static void createProduct(String name, int price, String description, int categoryId) throws SQLException {
        Item item = new Item(name, price, description, categoryId);
        item.save();
    }

}

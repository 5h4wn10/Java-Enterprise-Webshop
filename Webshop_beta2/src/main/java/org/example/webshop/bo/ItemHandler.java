package org.example.webshop.bo;

import org.example.webshop.bo.Item;
import org.example.webshop.ui.ItemInfoDTO;

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
        // Hämta alla produkter från Item-klassen
        Collection<Item> items = Item.searchItems();  // Inget gruppfilter, så det hämtar allt

        // Skapa en lista för att lagra resultaten
        List<ItemInfoDTO> itemList = new ArrayList<>();

        // Loopa igenom och lägg till varje objekt i listan som ItemInfoDTO
        for (Item item : items) {
            itemList.add(new ItemInfoDTO(item.getId(), item.getName(), item.getDescription(), item.getPrice(), item.getGroup(), item.getStock_quantity()));
        }

        return itemList;  // Returnerar hela listan
    }

    // Metod för att hämta produkt baserat på itemId genom Item-klassen
    public Item getItemById(int itemId) throws SQLException {
        // Använd Item-klassen för att hämta produktdata
        return Item.getItemById(itemId);
    }
}

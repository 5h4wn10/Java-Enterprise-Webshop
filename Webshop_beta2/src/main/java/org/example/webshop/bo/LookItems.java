package org.example.webshop.bo;

import org.example.webshop.bo.Item;

import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Collection;
import java.util.Iterator;

public class LookItems {

    // Metod som hämtar alla objekt och returnerar en Hashtable
    public Hashtable<String, Object> getItems() throws SQLException {
        // Hämta alla produkterna från Item-klassen (som hämtar från databasen)
        Collection<Item> items = Item.searchItems();

        // Skapa en Hashtable för att lagra resultaten
        Hashtable<String, Object> resultTable = new Hashtable<>();
        resultTable.put("size", items.size());  // Lägger in storleken på kollektionen

        // Loopa igenom och sätt in varje objekt i Hashtable
        Iterator<Item> it = items.iterator();
        int i = 0;
        while (it.hasNext()) {
            Item item = it.next();
            Hashtable<String, Object> itemData = new Hashtable<>();
            itemData.put("name", item.getName());
            itemData.put("price", item.getPrice());
            itemData.put("group", item.getGroup()); // Lägg till gruppen
            resultTable.put("Item" + i, itemData);
            i++;
        }

        return resultTable;
    }
}

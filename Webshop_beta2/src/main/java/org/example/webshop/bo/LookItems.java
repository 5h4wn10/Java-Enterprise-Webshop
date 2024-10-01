package org.example.webshop.bo;

import org.example.webshop.bo.Item;
import java.util.Hashtable;
import java.util.Collection;
import java.util.Iterator;

public class LookItems {
    public Hashtable getItemsWithGroup(String s) {
        Collection c = Item.searchItems(s);
        Hashtable t = new Hashtable();
        t.put("size", c.size());

        Iterator it = c.iterator();
        for (int i = 0; it.hasNext(); i++) {
            Item item = (Item) it.next();
            Hashtable itemData = new Hashtable();
            itemData.put("name", item.getName());
            itemData.put("price", item.getPrice());
            t.put("Item" + i, itemData);
        }
        return t;
    }
}

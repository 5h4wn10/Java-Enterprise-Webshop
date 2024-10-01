package org.example.webshop.db;

import org.example.webshop.bo.Item;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collection;
import java.util.Vector;

public class ItemDB {
    public static Collection searchItems(String group) {
        Vector items = new Vector();
        try {
            Connection con = DBManager.getConnection();
            Statement st = con.createStatement();
            String query = "SELECT item_id, name, price FROM Item WHERE item_group = '" + group + "'";
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("item_id");
                String name = rs.getString("name");
                int price = rs.getInt("price");
                items.add(new Item(id, name, price));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }
}

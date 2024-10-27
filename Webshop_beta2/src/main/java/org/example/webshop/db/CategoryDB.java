package org.example.webshop.db;

import org.example.webshop.bo.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDB {

    // Hämta alla kategorier från item_group-tabellen
    public static List<Category> getAllCategories() throws SQLException {
        List<Category> categories = new ArrayList<>();
        Connection con = DBManager.getConnection();

        try {
            String query = "SELECT * FROM item_group";  // Använd rätt tabellnamn
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int categoryId = rs.getInt("group_id");  // Använd rätt kolumnnamn
                String name = rs.getString("group_name");  // Använd rätt kolumnnamn

                Category category = new Category(categoryId, name);
                categories.add(category);
            }
        } finally {
            con.close();
        }

        return categories;
    }

    // Lägg till en ny kategori i item_group-tabellen
    public static void createCategory(String name) throws SQLException {
        Connection con = DBManager.getConnection();
        try {
            con.setAutoCommit(false); // Om auto-commit är avstängt, hantera det manuellt
            String query = "INSERT INTO item_group (group_name) VALUES (?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, name);

            System.out.println("Executing query: " + ps); // Debug-logg

            ps.executeUpdate();
            con.commit(); // Se till att committa ändringarna
        } catch (SQLException e) {
            if (con != null) {
                con.rollback(); // Rulla tillbaka om något går fel
            }
            throw e;
        } finally {
            con.close();
        }
    }



    // Uppdatera en kategori i item_group-tabellen
    public static void editCategory(int categoryId, String newCategoryName) throws SQLException {
        Connection con = DBManager.getConnection();
        try {
            // Stäng av autocommit så att vi kan kontrollera transaktionen
            con.setAutoCommit(false);

            String query = "UPDATE item_group SET group_name = ? WHERE group_id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, newCategoryName);
            ps.setInt(2, categoryId);

            System.out.println("Executing query: " + ps); // Debug-logg
            ps.executeUpdate();

            // Commit transaktionen för att bekräfta ändringarna
            con.commit();
        } catch (SQLException e) {
            if (con != null) {
                con.rollback(); // Återställ transaktionen vid fel
            }
            throw e;
        } finally {
            con.setAutoCommit(true); // Återställ autocommit efter att transaktionen har avslutats
            con.close();
        }
    }


}

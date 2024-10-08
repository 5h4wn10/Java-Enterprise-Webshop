package org.example.webshop.ui.Servlets;

import org.example.webshop.bo.OrderItem;
import org.example.webshop.bo.ShoppingCart;
import org.example.webshop.bo.Item;
import org.example.webshop.ui.ItemInfoDTO;
import org.example.webshop.ui.OrderItemDTO;
import org.example.webshop.ui.ShoppingCartDTO;
import org.example.webshop.ui.UserInfoDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
@WebServlet("/shoppingServlet")
public class ShoppingServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Hämta parametrarna från formuläret
        String itemId = request.getParameter("itemId");
        String itemName = request.getParameter("itemName");
        String itemPrice = request.getParameter("itemPrice");
        String itemGroup = request.getParameter("itemGroup");

        // Använd fabriksmönstret för att skapa ett BO Item-objekt baserat på parametrarna
        Item item = Item.createItem(
                Integer.parseInt(itemId),
                itemName,
                "", // description om det behövs
                Integer.parseInt(itemPrice),
                itemGroup,
                0 // stockQuantity, kan sättas till 0 eller en korrekt värde
        );

        // Konvertera Item till OrderItem innan den läggs till i kundvagnen
        OrderItem orderItem = new OrderItem(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getPrice(),
                item.getGroup(),
                item.getStock_quantity(),
                1 // Förutsätter att kvantiteten är 1. Ändra detta om du har stöd för olika kvantiteter.
        );

        // Hämta session och användarens unika kundvagn (BO-klassen)
        HttpSession session = request.getSession();
        UserInfoDTO user = (UserInfoDTO) session.getAttribute("user");

        // Kolla om en ShoppingCart (BO-klassen) redan finns i sessionen
        ShoppingCart cart = (ShoppingCart) session.getAttribute(user.getUsername() + "_cart_bo");

        // Om ingen BO-klass finns, skapa en ny
        if (cart == null) {
            cart = new ShoppingCart(user.getUserId());
        }

        // Lägg till OrderItem i kundvagnen (ShoppingCart BO-klassen)
        cart.addItem(orderItem);

        // Nu konvertera BO-klassen (ShoppingCart) till DTO innan du sparar i sessionen
        ShoppingCartDTO cartDTO = new ShoppingCartDTO(user.getUserId(), cart.getItems());
        session.setAttribute(user.getUsername() + "_cart_bo", cart);  // Spara BO-klass för backend-lagret
        session.setAttribute(user.getUsername() + "_cart", cartDTO);  // Spara DTO för UI-lagret

        // Omdirigera tillbaka till en sida för att visa kundvagnen, till exempel cart.jsp
        response.sendRedirect("cart.jsp");
    }
}


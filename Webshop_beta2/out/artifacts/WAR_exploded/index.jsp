<%@page import="org.example.webshop.ui.ItemInfoDTO"%>
<%@page import="org.example.webshop.bo.ItemHandler"%>
<%@page import="java.util.List"%>

<%@page import="org.example.webshop.ui.UserInfoDTO"%>
<%@page session="true" %>

<%
    // Kontrollera om användaren är inloggad
    UserInfoDTO user = (UserInfoDTO) session.getAttribute("user");
    if (user == null) {
        // Om användaren inte är inloggad, omdirigera till login-sidan
        response.sendRedirect("login.jsp");
        return;
    }
%>

<html>
<head>
    <title>Webshop Items</title>
</head>
<body>

<body>
<h1>Welcome, <%= user.getUsername() %>!</h1>
<p>This is your webshop homepage.</p>
<a href="login.jsp">Log out</a>


<!-- Resten av sidan -->
</body>
<h1>Available Items</h1>

<%
    // Skapa ett ItemHandler-objekt och hämta alla produkter
    List<ItemInfoDTO> items = ItemHandler.getAllItems(); // Ange den grupp du vill visa
%>

<table border="1">
    <tr>
        <th>Name</th>
        <th>Price</th>
        <th>Item Description</th>
        <th>Group</th>
        <th>Add to Cart</th> <!-- Ny kolumn för att lägga till i kundkorgen -->
    </tr>

    <%
        for (ItemInfoDTO item : items) {
    %>
    <tr>
        <td><%= item.getName() %></td>
        <td><%= item.getPrice() %></td>
        <td><%= item.getDescription() %></td>
        <td><%= item.getGroup() %></td>
        <td>
            <!-- Formulär för att lägga till item i kundkorgen -->
            <form action="shoppingServlet" method="post">
                <input type="hidden" name="itemId" value="<%= item.getId() %>">
                <input type="hidden" name="itemName" value="<%= item.getName() %>">
                <input type="hidden" name="itemDescription" value="<%= item.getDescription() %>">
                <input type="hidden" name="itemPrice" value="<%= item.getPrice() %>">
                <input type="hidden" name="itemGroup" value="<%= item.getGroup() %>">
                <input type="submit" value="Add to Cart">
            </form>
        </td>
    </tr>
    <%
        }
    %>
</table>

<!-- Länk för att visa kundkorgen -->
<a href="cart.jsp">View Cart</a>

</body>
</html>

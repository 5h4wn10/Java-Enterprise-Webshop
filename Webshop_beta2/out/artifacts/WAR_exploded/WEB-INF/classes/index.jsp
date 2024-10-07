<%@page import="org.example.webshop.bo.Item"%>
<%@page import="org.example.webshop.bo.ItemHandler"%>
<%@page import="java.util.Collection"%>

<html>
<head>
    <title>Available Items</title>
</head>
<body>

<h1>Available Items</h1>

<%
    Collection<Item> items = ItemHandler.getAllItems();
%>

<table border="1">
    <tr>
        <th>Name</th>
        <th>Price</th>
        <th>Description</th>
        <th>Group</th>
        <th>Stock Quantity</th> <!-- Ny kolumn för lagersaldo -->
        <th>Add to Cart</th>
    </tr>

    <%
        for (Item item : items) {
    %>
    <tr>
        <td><%= item.getName() %></td>
        <td><%= item.getPrice() %></td>
        <td><%= item.getDescription() %></td>
        <td><%= item.getItemGroup() %></td>
        <td><%= item.getStockQuantity() %></td> <!-- Visa lagersaldo -->
        <td>
            <form action="shoppingServlet" method="post">
                <input type="hidden" name="itemId" value="<%= item.getId() %>">
                <input type="submit" value="Add to Cart">
            </form>
        </td>
    </tr>
    <%
        }
    %>
</table>

</body>
</html>

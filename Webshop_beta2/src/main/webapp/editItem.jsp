<%@ page import="org.example.webshop.ui.DTOs.ItemInfoDTO" %>
<%
    ItemInfoDTO item = (ItemInfoDTO) request.getAttribute("item");
    String errorMessage = (String) request.getAttribute("errorMessage");

    if (errorMessage != null) {
%>
<p style="color: red;"><%= errorMessage %></p>
<%
} else if (item != null) {
%>
<h1>Edit Item: <%= item.getName() %></h1>
<form action="itemServlet" method="post">
    <input type="hidden" name="action" value="update">
    <input type="hidden" name="itemId" value="<%= item.getId() %>">
    <label>Name: </label><input type="text" name="name" value="<%= item.getName() %>"><br>
    <label>Description: </label><input type="text" name="description" value="<%= item.getDescription() %>"><br>
    <label>Price: </label><input type="number" name="price" value="<%= item.getPrice() %>"><br>
    <label>Stock: </label><input type="number" name="stock_quantity" value="<%= item.getStock_quantity() %>"><br>
    <input type="submit" value="Update Item">
</form>
<%
} else {
%>
<p>Error: Item not found.</p>
<%
    }
%>

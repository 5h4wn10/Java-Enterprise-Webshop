<%@page import="org.example.webshop.bo.LookItems" %>
<%@page import="java.util.Hashtable" %>
<%@page import="java.util.Enumeration" %>

<html>
<head>
    <title>Webshop Items</title>
</head>
<body>
<h1>Available Items</h1>

<%
    // Skapa ett LookItems-objekt och hämta alla produkter
    LookItems lookItems = new LookItems();
    Hashtable<String, Object> items = lookItems.getItems();

    int size = (int) items.get("size");  // Hämta antal objekt i Hashtable
%>

<table border="1">
    <tr>
        <th>Name</th>
        <th>Price</th>
        <th>Group</th> <!-- Lägg till kolumn för grupp -->
    </tr>

    <%
        for (int i = 0; i < size; i++) {
            Hashtable<String, Object> item = (Hashtable<String, Object>) items.get("Item" + i);
    %>
    <tr>
        <td><%= item.get("name") %></td>
        <td><%= item.get("price") %></td>
        <td><%= item.get("group") %></td> <!-- Visa gruppen -->
    </tr>
    <%
        }
    %>
</table>
</body>
</html>

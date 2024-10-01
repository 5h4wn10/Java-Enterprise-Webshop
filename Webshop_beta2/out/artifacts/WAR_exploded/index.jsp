<%@page import="org.example.webshop.bo.LookItems" %>
<%@page import="java.util.Hashtable" %>
<%
    String group = request.getParameter("itemGroup");
    LookItems look = new LookItems();
    Hashtable table = look.getItemsWithGroup(group);
    int size = (int) table.get("size");
%>
<table>
    <%
        for (int i = 0 ; i < size ; i++) {
            Hashtable item = (Hashtable) table.get("Item" + i);
    %>
    <tr>
        <td>Name:</td> <td><%= item.get("name") %></td>
        <td>Price:</td> <td><%= item.get("price") %></td>
    </tr>
    <%
        }
    %>
</table>

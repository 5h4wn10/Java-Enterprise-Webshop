<%@page import="java.util.HashMap"%>
<%@page import="org.example.webshop.ui.DTOs.OrderItemDTO"%>
<%@page import="org.example.webshop.ui.DTOs.ShoppingCartDTO"%>
<%@page import="java.util.Map"%>
<%@page import="org.example.webshop.ui.DTOs.UserInfoDTO"%>
<html>
<head>
    <title>Your Shopping Cart</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f8f9fa;
            margin: 0;
            padding: 0;
        }
        .container {
            width: 60%;
            margin: 50px auto;
            background-color: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
        }
        h1 {
            text-align: center;
            color: #333;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }
        table, th, td {
            border: 1px solid #ddd;
        }
        th, td {
            padding: 12px;
            text-align: center;
        }
        th {
            background-color: #343a40;
            color: white;
        }
        .button {
            background-color: #007bff;
            color: white;
            padding: 10px 15px;
            text-decoration: none;
            border-radius: 5px;
        }
        .button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Your Shopping Cart</h1>

    <%
        UserInfoDTO user = (UserInfoDTO) session.getAttribute("user");
        ShoppingCartDTO cart = (ShoppingCartDTO) session.getAttribute(user.getUsername() + "_cart");

        if (cart == null || cart.getItems().isEmpty()) {
    %>
    <p>Your cart is empty.</p>
    <a class="button" href="index.jsp">Continue Shopping</a>
    <%
    } else {
        Map<String, OrderItemDTO> groupedItems = new HashMap<>();

        for (OrderItemDTO item : cart.getItems()) {
            if (groupedItems.containsKey(item.getName())) {
                OrderItemDTO existingItem = groupedItems.get(item.getName());
                existingItem.setOrderedQuantity(existingItem.getOrderedQuantity() + item.getOrderedQuantity());
            } else {
                groupedItems.put(item.getName(), item);
            }
        }
    %>
    <table>
        <tr>
            <th>Name</th>
            <th>Price</th>
            <th>Group</th>
            <th>Quantity</th>
        </tr>

        <%
            for (OrderItemDTO item : groupedItems.values()) {
        %>
        <tr>
            <td><%= item.getName() %></td>
            <td><%= item.getPrice() %> SEK</td>
            <td><%= item.getGroup() %></td>
            <td><%= item.getOrderedQuantity() %></td>
        </tr>
        <%
            }
        %>
    </table>

    <p>Total Items: <%= groupedItems.size() %></p>
    <p>Total Price: <%= cart.getTotalPrice() %> SEK</p>

    <a class="button" href="index.jsp">Continue Shopping</a>
    <a class="button" href="cartServlet?action=checkout">Proceed to Checkout</a>

    <%
        }
    %>
</div>
</body>
</html>

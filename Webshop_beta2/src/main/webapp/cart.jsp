<%@page import="java.util.HashMap"%>
<%@page import="org.example.webshop.bo.OrderItem"%>
<%@page import="org.example.webshop.ui.OrderItemDTO"%>
<%@page import="java.util.List"%>
<%@page import="org.example.webshop.ui.ShoppingCartDTO"%>
<%@page import="java.util.Map"%>
<%@page import="org.example.webshop.ui.UserInfoDTO"%>
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
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
            border-radius: 10px;
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

        tr:nth-child(even) {
            background-color: #f2f2f2;
        }

        tr:hover {
            background-color: #e9ecef;
        }

        .button {
            background-color: #007bff;
            color: white;
            border: none;
            padding: 10px 15px;
            cursor: pointer;
            border-radius: 5px;
            text-decoration: none;
        }

        .button-sec {
            background-color: #51c729;
            color: white;
            border: none;
            padding: 10px 15px;
            cursor: pointer;
            border-radius: 5px;
            text-decoration: none;
        }

        .button:hover {
            background-color: #0056b3;
        }

        .button-sec:hover {
            background-color: #2c591e;

        }

        .footer {
            text-align: center;
            padding: 10px;
            margin-top: 20px;
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

        // Gruppera varorna i kundvagnen efter namn och uppdatera kvantiteten
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

    <a class="button" href="index.jsp">Continue Shopping</a>  <a class="button-sec" href="checkout.jsp">Proceed to Checkout</a>

    <!--<form action="checkoutServlet" method="get">
        <input type="submit" value="Proceed to Checkout">
    </form>-->

    <%
        }
    %>
</div>
</body>
</html>

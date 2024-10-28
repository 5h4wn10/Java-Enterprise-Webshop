<%@page import="org.example.webshop.ui.DTOs.OrderItemDTO"%>
<%@page import="org.example.webshop.ui.DTOs.ShoppingCartDTO"%>
<%@page import="org.example.webshop.ui.DTOs.UserInfoDTO"%>
<%@ page import="java.util.List" %>
<%@page session="true" %>

<html>
<head>
    <title>Checkout</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
            text-align: center;
        }

        h1 {
            color: #007bff;
        }

        .container {
            width: 70%;
            margin: 50px auto;
            background-color: #fff;
            padding: 20px;
            border-radius: 10px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 10px;
            text-align: center;
        }

        .btn {
            padding: 10px 20px;
            margin: 20px;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
        }

        .btn-primary {
            background-color: #007bff;
            color: white;
        }

        .btn-secondary {
            background-color: #6c757d;
            color: white;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Checkout</h1>

    <%
        UserInfoDTO user = (UserInfoDTO) session.getAttribute("user");
        ShoppingCartDTO cartDTO = (ShoppingCartDTO) session.getAttribute(user.getUsername() + "_cart");

        if (cartDTO == null || cartDTO.getItems().isEmpty()) {
    %>
    <p>Your cart is empty.</p>
    <a href="index.jsp" class="btn btn-secondary">Continue Shopping</a>
    <%
    } else {
        List<OrderItemDTO> cartItems = cartDTO.getItems();
        int totalPrice = cartDTO.getTotalPrice();
    %>

    <table>
        <tr>
            <th>Item Name</th>
            <th>Price</th>
            <th>Quantity</th>
            <th>Total</th>
        </tr>

        <%
            for (OrderItemDTO item : cartItems) {
                int itemTotalPrice = item.calculateTotalPrice();
        %>
        <tr>
            <td><%= item.getName() %></td>
            <td><%= item.getPrice() %> SEK</td>
            <td><%= item.getOrderedQuantity() %></td>
            <td><%= itemTotalPrice %> SEK</td>
        </tr>
        <%
            }
        %>

        <tr>
            <td colspan="3">Total Price</td>
            <td><%= totalPrice %> SEK</td>
        </tr>
    </table>

    <form action="orderServlet" method="post">
        <input type="hidden" name="action" value="create">
        <button type="submit" class="btn btn-primary">Confirm Checkout</button>
    </form>
    <a href="cart.jsp" class="btn btn-secondary">Go Back to Cart</a>
    <%
        }
    %>
</div>
</body>
</html>

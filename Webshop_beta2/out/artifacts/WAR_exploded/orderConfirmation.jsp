<%@ page import="java.util.List" %>
<%@ page import="org.example.webshop.ui.OrderItemDTO" %>

<html>
<head>
    <title>Order Confirmation</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
            text-align: center;
        }

        h1 {
            color: #28a745;
        }

        .container {
            margin-top: 50px;
        }

        table {
            width: 80%;
            margin: 0 auto;
            border-collapse: collapse;
        }

        table, th, td {
            border: 1px solid #ddd;
            padding: 10px;
        }

        th {
            background-color: #f2f2f2;
        }

        .total-price {
            font-size: 18px;
            font-weight: bold;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Order Confirmation</h1>

    <table>
        <tr>
            <th>Item Name</th>
            <th>Price</th>
            <th>Quantity</th>
            <th>Total</th>
        </tr>

        <%
            List<OrderItemDTO> orderItems = (List<OrderItemDTO>) request.getAttribute("orderItems");
            int totalPrice = 0;

            for (OrderItemDTO item : orderItems) {
                int itemTotalPrice = item.calculateTotalPrice();
                totalPrice += itemTotalPrice;
        %>
        <tr>
            <td><%= item.getName() %></td>
            <td>$<%= item.getPrice() %></td>
            <td><%= item.getOrderedQuantity() %></td>
            <td>$<%= itemTotalPrice %></td>
        </tr>
        <%
            }
        %>
        <tr>
            <td colspan="3" class="total-price">Total Price</td>
            <td class="total-price">$<%= totalPrice %></td>
        </tr>
    </table>

    <br>
    <a href="index.jsp">Continue Shopping</a>
</div>
</body>
</html>

<%@ page import="java.util.List" %>
<%@ page import="org.example.webshop.ui.ItemInfoDTO" %>
<%@ page import="org.example.webshop.bo.ShoppingCart" %>
<%@ page import="org.example.webshop.ui.UserInfoDTO" %>
<%@ page import="org.example.webshop.ui.OrderItemDTO" %>
<%@ page import="org.example.webshop.bo.OrderItem" %>

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
            margin-top: 50px;
            width: 70%;
            margin: 50px auto;
            background-color: #fff;
            padding: 20px;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
            border-radius: 10px;
        }

        table {
            width: 100%;
            margin: 0 auto;
            border-collapse: collapse;
            margin-bottom: 20px;
        }

        table, th, td {
            border: 1px solid #ddd;
            padding: 10px;
            text-align: center;
        }

        th {
            background-color: #f2f2f2;
        }

        .total-price {
            font-size: 18px;
            font-weight: bold;
        }

        .btn {
            padding: 10px 20px;
            margin: 20px;
            border: none;
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

        .btn-primary:hover {
            background-color: #0056b3;
        }

        .btn-secondary:hover {
            background-color: #5a6268;
        }
    </style>
</head>
<body>

<div class="container">
    <h1>Checkout</h1>

    <%
        // Hämta användaren och kundvagnen från sessionen
        UserInfoDTO user = (UserInfoDTO) session.getAttribute("user");
        ShoppingCart cart = (ShoppingCart) session.getAttribute(user.getUsername() + "_cart");

        // Kontrollera om kundvagnen är tom
        if (cart == null || cart.getItems().isEmpty()) {
    %>
    <p>Your cart is empty.</p>
    <a href="index.jsp" class="btn btn-secondary">Continue Shopping</a>
    <%
    } else {
        // Hämta alla varor från kundvagnen
        List<OrderItem> cartItems = cart.getItems();
        int totalPrice = cart.getTotalPrice();  // Beräkna totalpris
    %>

    <table>
        <tr>
            <th>Item Name</th>
            <th>Price</th>
            <th>Quantity</th>
            <th>Total</th>
        </tr>

        <%
            for (OrderItem item : cartItems) {
                int itemTotalPrice = item.getPrice();  // Enkelt antagande att kvantitet alltid är 1
        %>
        <tr>
            <td><%= item.getName() %></td>
            <td>$<%= item.getPrice() %></td>
            <td>1</td> <!-- Du kan justera för kvantitet här om du hanterar fler än 1 -->
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

    <!-- Formulär för att bekräfta utcheckning -->
    <form action="checkoutServlet" method="post">
        <button type="submit" class="btn btn-primary">Confirm Checkout</button>
        <a href="cart.jsp" class="btn btn-secondary">Go Back to Cart</a>
    </form>
    <%
        }
    %>
</div>

</body>
</html>

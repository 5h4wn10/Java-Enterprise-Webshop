<%@ page import="java.util.List" %>
<%@ page import="org.example.webshop.ui.DTOs.OrderItemDTO" %>

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

        .buttons {
            margin-top: 30px;
        }

        .btn {
            padding: 10px 20px;
            margin: 10px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
        }

        .btn-primary {
            background-color: #007bff;
            color: white;
        }

        .btn-primary:hover {
            background-color: #0056b3;
        }

        .btn-secondary {
            background-color: #dc3545;
            color: white;
        }

        .btn-secondary:hover {
            background-color: #c82333;
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
            // Hämta orderItems från request-attributen
            List<OrderItemDTO> orderItems = (List<OrderItemDTO>) request.getAttribute("orderItems");
            int totalPrice = 0;
            if (orderItems == null) {
                // Hantera fallet där orderItems är null
                System.out.println("<p>Order could not be processed. Please try again.</p>");
            } else {
                totalPrice = 0;
                for (OrderItemDTO item : orderItems) {
                    int itemTotalPrice = item.calculateTotalPrice();
                    totalPrice += itemTotalPrice;
        %>
        <tr>
            <td><%=item.getName()%></td>
            <td>$<%=item.getPrice()%></td>
            <td><%=item.getOrderedQuantity()%></td>
            <td>$<%=itemTotalPrice%></td>
        </tr>
        <%
            }
        %>
        <tr>
            <td colspan="3" class="total-price">Total Price</td>
            <td class="total-price">$<%=totalPrice%></td>
        </tr>
        <%
            }
        %>
    </table>

    <div class="buttons">
        <!-- Knapp för att fortsätta shoppa -->
        <a href="index.jsp" class="btn btn-primary">Continue Shopping</a>
    </div>

    <div class="buttons">
        <!-- Knapp för att logga ut -->
        <a href="logoutServlet" class="btn btn-secondary">Log out</a>
    </div>
</div>
</body>
</html>

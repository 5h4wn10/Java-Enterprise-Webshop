<%@ page import="org.example.webshop.ui.DTOs.OrderDTO" %>
<%@ page import="org.example.webshop.ui.DTOs.OrderItemDTO" %>
<%@ page import="java.util.List" %>

<%
    List<OrderDTO> pendingOrders = (List<OrderDTO>) request.getAttribute("pendingOrders");
%>

<html>
<head>
    <title>View Orders</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f8f9fa;
            margin: 0;
            padding: 0;
        }

        h1 {
            text-align: center;
            margin-top: 20px;
            color: #333;
        }

        .container {
            width: 70%;
            margin: 30px auto;
            background-color: #fff;
            padding: 25px;
            border-radius: 10px;
            box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
        }

        .header {
            text-align: center;
            padding: 20px;
            background-color: #007bff;
            color: white;
            margin-bottom: 20px;
            border-radius: 10px 10px 0 0;
        }

        .header a {
            color: white;
            text-decoration: none;
            font-weight: bold;
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
            padding: 14px;
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

        .btn {
            background-color: #28a745;
            color: white;
            border: none;
            padding: 10px 15px;
            cursor: pointer;
            border-radius: 5px;
            transition: background-color 0.3s;
        }

        .btn:hover:enabled {
            background-color: #218838;
        }

        .back-link {
            display: inline-block;
            margin-top: 20px;
            padding: 10px 20px;
            background-color: #007bff;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            transition: background-color 0.3s;
        }

        .back-link:hover {
            background-color: #0056b3;
        }

        .footer {
            text-align: center;
            padding: 20px;
            background-color: #f8f9fa;
            margin-top: 30px;
        }

        .footer p {
            color: #6c757d;
        }
    </style>
</head>
<body>

<div class="header">
    <h1>Pending Orders</h1>
</div>

<div class="container">
    <%
        if (pendingOrders == null || pendingOrders.isEmpty()) {
    %>
    <p>No pending orders.</p>
    <%
    } else {
    %>
    <table>
        <thead>
        <tr>
            <th>Order ID</th>
            <th>Order Date</th>
            <th>Total Price (SEK)</th>
            <th>Items</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <% for (OrderDTO order : pendingOrders) { %>
        <tr>
            <td><%= order.getOrderId() %></td>
            <td><%= order.getOrderDate() %></td>
            <td><%= order.getTotalPrice() %> SEK</td>
            <td>
                <ul>
                    <% for (OrderItemDTO item : order.getItems()) { %>
                    <li><%= item.getName() %> - <%= item.getOrderedQuantity() %> x <%= item.getPrice() %> SEK</li>
                    <% } %>
                </ul>
            </td>
            <td>
                <form action="orderServlet" method="post">
                    <input type="hidden" name="action" value="packOrder">
                    <input type="hidden" name="orderId" value="<%= order.getOrderId() %>">
                    <input type="submit" class="btn" value="Mark as Packed">
                </form>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>
    <%
        }
    %>

    <a href="admin.jsp" class="back-link">Back to Admin Page</a>
</div>

<div class="footer">
    <p>&copy; Webshop för admin och lagerpersonal</p>
</div>

</body>
</html>

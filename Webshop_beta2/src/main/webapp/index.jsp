<%@page import="org.example.webshop.ui.DTOs.ItemInfoDTO"%>
<%@page import="org.example.webshop.bo.ItemHandler"%>
<%@page import="java.util.List"%>
<%@page import="org.example.webshop.ui.DTOs.UserInfoDTO"%>
<%@page session="true" %>

<%
    UserInfoDTO user = (UserInfoDTO) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    List<ItemInfoDTO> items = ItemHandler.getAllItems();
%>
<html>
<head>
    <title>Webshop Items</title>
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

        .add-to-cart {
            background-color: #28a745;
            color: white;
            border: none;
            padding: 10px 15px;
            cursor: pointer;
            border-radius: 5px;
            transition: background-color 0.3s;
        }

        .add-to-cart:disabled {
            background-color: #ccc;
            cursor: not-allowed;
        }

        .add-to-cart:hover:enabled {
            background-color: #218838;
        }

        .view-cart {
            display: inline-block;
            padding: 10px 20px;
            background-color: #007bff;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            transition: background-color 0.3s;
        }

        .view-cart:hover {
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
    <h1>Welcome, <%= user.getUsername() %>!</h1>
    <a href="login.jsp">Log out</a>
</div>

<div class="container">
    <h1>Available Items</h1>

    <table>
        <tr>
            <th>Name</th>
            <th>Price</th>
            <th>Item Description</th>
            <th>Group</th>
            <th>Stock</th>
            <th>Add to Cart</th>
        </tr>

        <%
            for (ItemInfoDTO item : items) {
        %>
        <tr>
            <td><%= item.getName() %></td>
            <td><%= item.getPrice() %> SEK</td>
            <td><%= item.getDescription() %></td>
            <td><%= item.getGroup() %></td>
            <td>
                <%= item.getStock_quantity() > 0 ? " in stock" : "Out of stock" %>
            </td>
            <td>
                <form action="cartServlet" method="post">
                    <input type="hidden" name="action" value="add">
                    <input type="hidden" name="itemId" value="<%= item.getId() %>">
                    <input type="number" name="quantity" value="1" min="1" max="<%= item.getStock_quantity() %>">
                    <button class="add-to-cart" type="submit" <%= item.getStock_quantity() <= 0 ? "disabled" : "" %>>Add to Cart</button>
                </form>
            </td>
        </tr>
        <%
            }
        %>
    </table>

    <a class="view-cart" href="cart.jsp">View Cart</a>
</div>
</body>
</html>

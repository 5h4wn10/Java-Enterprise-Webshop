<%@page import="org.example.webshop.ui.ItemInfoDTO"%>
<%@page import="org.example.webshop.bo.ItemHandler"%>
<%@page import="java.util.List"%>
<%@page import="org.example.webshop.ui.UserInfoDTO"%>
<%@page session="true" %>

<%
    // Kontrollera om användaren är inloggad och om de är en admin
    UserInfoDTO user = (UserInfoDTO) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    if (user.getRoleId() == 1) { // 2 is assumed to be the Admin role
        response.sendRedirect("index.jsp"); // Redirect non-admins back to the index page
        return;
    }

    // Hämta alla produkter från ItemHandler
    List<ItemInfoDTO> items = ItemHandler.getAllItems();
%>
<html>
<head>
    <title>Admin Dashboard</title>
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

        .btn:disabled {
            background-color: #ccc;
            cursor: not-allowed;
        }

        .btn:hover:enabled {
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

        /* Additional styles for admin actions */
        .admin-actions {
            margin-bottom: 20px;
        }

        .admin-actions a {
            display: inline-block;
            margin-right: 15px;
            padding: 10px 20px;
            background-color: #28a745;
            color: white;
            text-decoration: none;
            border-radius: 5px;
        }

        .admin-actions a:hover {
            background-color: #218838;
        }
    </style>
</head>
<body>

<div class="header">
    <h1>Welcome, Admin <%= user.getUsername() %>!</h1>
    <p>This is your Admin Dashboard.</p>
    <a href="login.jsp">Log out</a>
</div>

<div class="container">
    <h1>Manage Products</h1>

    <div class="admin-actions">
        <a href="addProduct.jsp">Add New Product</a>
        <a href="manageUsers.jsp">Manage Users</a>
        <a href="viewOrders.jsp">View Orders</a>
    </div>

    <table>
        <tr>
            <th>Name</th>
            <th>Price</th>
            <th>Item Description</th>
            <th>Group</th>
            <th>Stock</th>
            <th>Actions</th>
        </tr>

        <%
            for (ItemInfoDTO item : items) {
        %>
        <tr>
            <td><%= item.getName() %></td>
            <td><%= item.getPrice() %> SEK</td>
            <td><%= item.getDescription() %></td>
            <td><%= item.getGroup() %></td>
            <td><%= item.getStock_quantity() %></td>
            <td>
                <!-- Buttons for admin actions -->
                <a class="btn" href="editProduct.jsp?itemId=<%= item.getId() %>">Edit</a>
                <a class="btn" href="deleteProductServlet?itemId=<%= item.getId() %>">Delete</a>
            </td>
        </tr>
        <%
            }
        %>
    </table>

</div>

<div class="footer">
    <p>&copy; 2024 Webshop Admin Panel. All Rights Reserved.</p>
</div>

</body>
</html>

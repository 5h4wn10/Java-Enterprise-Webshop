<%@page import="org.example.webshop.ui.ItemInfoDTO"%>
<%@page import="org.example.webshop.bo.ItemHandler"%>
<%@page import="java.util.List"%>
<%@page import="org.example.webshop.ui.UserInfoDTO"%>
<%@page session="true" %>

<%
    // Kontrollera om användaren är inloggad
    UserInfoDTO user = (UserInfoDTO) session.getAttribute("user");
    if (user == null) {
        // Om användaren inte är inloggad, omdirigera till login-sidan
        response.sendRedirect("login.jsp");
        return;
    }
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

        .add-to-cart:hover {
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
    <p>This is your webshop homepage.</p>
    <a href="login.jsp">Log out</a>
</div>

<div class="container">
    <h1>Available Items</h1>

    <%
        // Skapa ett ItemHandler-objekt och hämta alla produkter
        List<ItemInfoDTO> items = ItemHandler.getAllItems(); // Ange den grupp du vill visa
    %>

    <table>
        <tr>
            <th>Name</th>
            <th>Price</th>
            <th>Item Description</th>
            <th>Group</th>
            <th>Add to Cart</th>
        </tr>

        <%
            for (ItemInfoDTO item : items) {
        %>
        <tr>
            <td><%= item.getName() %></td>
            <td><%= item.getPrice() %></td>
            <td><%= item.getDescription() %></td>
            <td><%= item.getGroup() %></td>
            <td>
                <!-- Formulär för att lägga till item i kundkorgen -->
                <form action="shoppingServlet" method="post">
                    <input type="hidden" name="itemId" value="<%= item.getId() %>">
                    <input type="hidden" name="itemName" value="<%= item.getName() %>">
                    <input type="hidden" name="itemDescription" value="<%= item.getDescription() %>">
                    <input type="hidden" name="itemPrice" value="<%= item.getPrice() %>">
                    <input type="hidden" name="itemGroup" value="<%= item.getGroup() %>">
                    <input class="add-to-cart" type="submit" value="Add to Cart">
                </form>
            </td>
        </tr>
        <%
            }
        %>
    </table>

    <!-- Länk för att visa kundkorgen -->
    <a class="view-cart" href="cart.jsp">View Cart</a>
</div>

<div class="footer">
    <p>&copy; 2024 Webshop. All Rights Reserved.</p>
</div>

</body>
</html>

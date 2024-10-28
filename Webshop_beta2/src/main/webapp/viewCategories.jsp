<%@ page import="org.example.webshop.ui.DTOs.CategoryDTO" %>
<%@ page import="java.util.List" %>

<%
    List<CategoryDTO> categories = (List<CategoryDTO>) request.getAttribute("categories");
%>

<html>
<head>
    <title>Categories</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }

        h1 {
            text-align: center;
        }

        ul {
            list-style-type: none;
            padding: 0;
        }

        li {
            padding: 8px;
            background-color: #f2f2f2;
            margin-bottom: 5px;
            border: 1px solid #ccc;
            text-align: center;
        }

        a {
            display: inline-block;
            padding: 8px 12px;
            background-color: #007bff;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            margin-top: 10px;
        }

        a:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
<h1>Categories</h1>
<ul>
    <% for (CategoryDTO category : categories) { %>
    <li>
        <%= category.getName() %>
        <a href="editCategory.jsp?categoryId=<%= category.getCategoryId() %>&categoryName=<%= category.getName() %>">Edit</a>
    </li>
    <% } %>
</ul>

<a href="admin.jsp">Back to admin page</a> <a href="addCategory.jsp">Add New Category</a>
</body>
</html>

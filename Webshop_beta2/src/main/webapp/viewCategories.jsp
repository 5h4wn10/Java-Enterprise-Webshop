<%@ page import="org.example.webshop.bo.Category" %>
<%@ page import="java.util.List" %>

<%
    List<Category> categories = (List<Category>) request.getAttribute("categories");
%>

<html>
<head>
    <title>Categories</title>
</head>
<body>
<h1>Categories</h1>
<ul>
    <% for (Category category : categories) { %>
    <li><%= category.getName() %></li>
    <% } %>
</ul>
<a href="addCategory.jsp">Add New Category</a>
</body>
</html>

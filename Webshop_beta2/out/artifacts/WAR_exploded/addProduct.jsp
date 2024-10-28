<%@ page import="java.util.List" %>
<%@ page import="org.example.webshop.bo.CategoryHandler" %>
<%@ page import="org.example.webshop.bo.Category" %>
<html>
<head>
    <title>Add New Product</title>
</head>
<body>
<h1>Add New Product</h1>

<form action="itemServlet" method="post">
    <input type="hidden" name="action" value="createProduct">
    <label for="productName">Product Name:</label>
    <input type="text" name="productName" id="productName" required><br><br>

    <label for="price">Price:</label>
    <input type="number" name="price" id="price" required><br><br>

    <label for="description">Description:</label>
    <textarea name="description" id="description" required></textarea><br><br>

    <label for="category">Category:</label>
    <select name="categoryId" id="category">
        <%-- Loop through all categories and show them --%>
        <% List<Category> categories = CategoryHandler.getAllCategories(); %>
        <% for (Category category : categories) { %>
        <option value="<%= category.getCategoryId() %>"><%= category.getName() %></option>
        <% } %>
    </select><br><br>

    <input type="submit" value="Add Product">
</form>

<a href="admin.jsp">Back to Admin Page</a>
</body>
</html>

<%@ page import="java.util.List" %>
<%@ page import="org.example.webshop.bo.Category" %>
<%@ page import="org.example.webshop.bo.CategoryHandler" %>
<html>
<head>
    <title>Add New Product</title>
</head>
<body>
<h1>Add New Product</h1>

<form action="createProductServlet" method="post">
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

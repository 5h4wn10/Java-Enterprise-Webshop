<%@ page import="java.util.List" %>
<html>
<head>
    <title>Add New Category</title>
</head>
<body>
<h1>Add New Category</h1>

<form action="categoryServlet" method="post">
    <input type="hidden" name="action" value="createCategory">
    <label for="categoryName">Category Name:</label>
    <input type="text" name="categoryName" id="categoryName" required><br><br>

    <input type="submit" value="Add Category">
</form>

<a href="admin.jsp">Back to Admin Page</a>
</body>
</html>

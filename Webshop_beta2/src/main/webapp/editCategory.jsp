<%@ page import="org.example.webshop.bo.Category" %>
<html>
<head>
    <title>Edit Category</title>
</head>
<body>
<h1>Edit Category</h1>

<form action="categoryServlet" method="post">
    <input type="hidden" name="action" value="editCategory">
    <input type="hidden" name="categoryId" value="<%= request.getParameter("categoryId") %>">
    <label for="categoryName">New Category Name:</label>
    <input type="text" name="categoryName" value="<%= request.getParameter("categoryName") %>" required><br><br>

    <input type="submit" value="Update Category">
</form>

<a href="admin.jsp">Back to Admin Page</a>
</body>
</html>

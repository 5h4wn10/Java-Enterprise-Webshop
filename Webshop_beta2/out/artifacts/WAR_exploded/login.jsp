<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
    <title>Login</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }

        .container {
            width: 30%;
            margin: 50px auto;
            background-color: #fff;
            padding: 20px;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
            border-radius: 10px;
        }

        h1 {
            text-align: center;
            color: #333;
        }

        form {
            margin: 0;
        }

        label {
            display: block;
            margin-bottom: 10px;
            color: #555;
        }

        input[type="text"], input[type="password"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 20px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }

        input[type="submit"], .register-btn {
            background-color: #28a745;
            color: white;
            border: none;
            padding: 10px 20px;
            cursor: pointer;
            border-radius: 5px;
            width: 100%;
            transition: background-color 0.3s;
        }

        input[type="submit"]:hover {
            background-color: #218838;
        }

        .register-btn {
            background-color: #007bff;
            margin-top: 15px;
        }

        .register-btn:hover {
            background-color: #0056b3;
        }

        p {
            text-align: center;
        }

        p a {
            color: #007bff;
            text-decoration: none;
        }

        p a:hover {
            text-decoration: underline;
        }

        .error-message {
            color: red;
            text-align: center;
        }
    </style>
</head>
<body>

<div class="container">
    <h1>Login</h1>

    <%
        String errorMessage = (String) request.getAttribute("errorMessage");
        if (errorMessage != null) {
    %>
    <p class="error-message"><%= errorMessage %></p>
    <%
        }
    %>

    <form action="loginServlet" method="post">
        <label for="username">USERNAME:</label>
        <input type="text" id="username" name="username" value="lager" required><br><br> <!-- Autofyll med användarnamn -->

        <label for="password">PASSWORD:</label>
        <input type="password" id="password" name="password" value="lager" required><br><br> <!-- Autofyll med lösenord -->

        <input type="submit" value="Login">
    </form>

    <form action="register.jsp" method="get">
        <input type="submit" class="register-btn" value="Register New Account">
    </form>
</div>

</body>
</html>

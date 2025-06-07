<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <title>Đăng nhập hệ thống</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body class="login-body">
    <div class="login-container">
        <h2>Đăng nhập hệ thống</h2>
        
        <% if (request.getAttribute("ERROR") != null) { %>
            <p><%= request.getAttribute("ERROR") %></p>
        <% } %>
        
        <form action="MainController" method="POST">
            <input type="text" name="userID" placeholder="Tài khoản" required><br>
            <input type="password" name="password" placeholder="Mật khẩu" required><br>
            <input type="submit" name="action" value="Login">
        </form>
    </div>
</body>
</html>
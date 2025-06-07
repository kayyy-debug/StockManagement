<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <title>Thêm người dùng</title>
    <link rel="stylesheet" href="css/adduser.css">
</head>
<body>
    <div class="add-user-form">
        <h2>➕ Thêm người dùng mới</h2>
        
        <% if (request.getAttribute("ERROR") != null) { %>
            <div class="error"><%= request.getAttribute("ERROR") %></div>
        <% } %>
        
        <% if (request.getAttribute("MESSAGE") != null) { %>
            <div class="success"><%= request.getAttribute("MESSAGE") %></div>
        <% } %>
        
        <form action="MainController" method="POST">
            <label for="userID">Mã người dùng:</label>
            <input type="text" name="userID" id="userID" required><br>
            
            <label for="fullName">Họ tên:</label>
            <input type="text" name="fullName" id="fullName" required><br>
            
            <label for="roleID">Vai trò:</label>
            <select name="roleID" id="roleID">
                <option value="AD">Quản trị viên</option>
                <option value="NV" selected>Nhân viên</option>
            </select><br>
            
            <label for="password">Mật khẩu:</label>
            <input type="password" name="password" id="password" required><br>
            
            <input type="submit" name="action" value="CreateUser">
        </form>
        
        <a href="MainController?action=ListUsers" class="back-link">← Quay lại danh sách</a>
    </div>
</body>
</html>
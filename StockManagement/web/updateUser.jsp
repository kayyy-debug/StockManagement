<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="dto.UserDTO"%>

<!DOCTYPE html>
<html>
<head>
    <title>Cập nhật người dùng</title>
    <link rel="stylesheet" href="css/updateuser.css">
</head>
<body>
    <% 
        UserDTO u = (UserDTO) request.getAttribute("EDIT_USER");
        if (u == null) {
            response.sendRedirect("MainController?action=ListUsers");
            return;
        }
    %>

    <div class="update-user-form">
        <h2>✏️ Cập nhật người dùng</h2>
        
        <% if (request.getAttribute("ERROR") != null) { %>
            <div class="error"><%= request.getAttribute("ERROR") %></div>
        <% } %>

        <form action="MainController" method="POST">
            <label for="userID">Mã người dùng:</label>
            <input type="text" name="userID" id="userID" value="<%= u.getUserID() %>" readonly><br>

            <label for="fullName">Họ tên:</label>
            <input type="text" name="fullName" id="fullName" value="<%= u.getFullName() %>" required><br>

            <label for="roleID">Vai trò:</label>
            <select name="roleID" id="roleID">
                <option value="AD" <%= "AD".equals(u.getRoleID()) ? "selected" : "" %>>Quản trị viên</option>
                <option value="NV" <%= "NV".equals(u.getRoleID()) ? "selected" : "" %>>Nhân viên</option>
            </select><br>

            <label for="password">Mật khẩu:</label>
            <input type="password" name="password" id="password" value="<%= u.getPassword() %>" required><br>

            <input type="submit" name="action" value="UpdateUser">
        </form>

        <a href="MainController?action=ListUsers" class="back-link">← Quay lại danh sách</a>
    </div>
</body>
</html>
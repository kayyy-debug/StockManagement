<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="dto.UserDTO, java.util.List"%>

<!DOCTYPE html>
<html>
<head>
    <title>Danh sách người dùng</title>
    <link rel="stylesheet" href="css/userlist.css">
</head>
<body>
    <% 
        UserDTO loginUser = (UserDTO) session.getAttribute("LOGIN_USER");
        if (loginUser == null || !"AD".equals(loginUser.getRoleID())) {
            response.sendRedirect("login.jsp");
            return;
        }
    %>

    <h2>👤 Danh sách người dùng</h2>
    <a href="MainController?action=AddUserPage" class="add-button">➕ Thêm người dùng</a>

    <% if (request.getAttribute("MESSAGE") != null) { %>
        <div class="message"><%= request.getAttribute("MESSAGE") %></div>
    <% } %>
    <% if (request.getAttribute("ERROR") != null) { %>
        <div class="error"><%= request.getAttribute("ERROR") %></div>
    <% } %>

    <div class="table-container">
        <table>
            <tr>
                <th>STT</th>
                <th>User ID</th>
                <th>Họ tên</th>
                <th>Quyền</th>
                <th>Hành động</th>
            </tr>
            <% 
                List<UserDTO> list = (List<UserDTO>) request.getAttribute("USER_LIST");
                int index = 1;
                if (list != null) {
                    for (UserDTO u : list) { 
            %>
                <tr>
                    <td><%= index++ %></td>
                    <td><%= u.getUserID() %></td>
                    <td><%= u.getFullName() %></td>
                    <td><%= "AD".equals(u.getRoleID()) ? "Admin" : "Nhân viên" %></td>
                    <td class="actions">
                        <a href="MainController?action=EditUser&userID=<%= u.getUserID() %>">✏️ Sửa</a>
                        <a href="MainController?action=DeleteUser&userID=<%= u.getUserID() %>" onclick="return confirm('Xác nhận xóa người dùng này?')">🗑️ Xóa</a>
                    </td>
                </tr>
            <% 
                    }
                } 
            %>
        </table>
    </div>

    <a href="welcome.jsp" class="back-link">← Quay lại trang chính</a>
</body>
</html>
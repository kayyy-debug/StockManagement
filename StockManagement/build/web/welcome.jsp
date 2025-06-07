<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="dto.UserDTO"%>

<!DOCTYPE html>
<html>
    <head>
        <title>Trang chào mừng</title>
        <link rel="stylesheet" href="css/style.css">
    </head>
    <body>
        <% 
            UserDTO loginUser = (UserDTO) session.getAttribute("LOGIN_USER");
            if (loginUser == null) {
                response.sendRedirect("login.jsp");
                return;
            }
        %>
        <div class="box">
            <h2>Xin chào, <%= loginUser.getFullName() %>!</h2>
            <p>Vai trò: <%= "AD".equals(loginUser.getRoleID()) ? "Quản trị viên" : "Nhân viên" %></p>

            <h3>Chức năng hệ thống:</h3>
            <ul>
                <li><a href="MainController?action=ListStocks">📈 Quản lý cổ phiếu</a></li>
                <li><a href="MainController?action=ListTransactions">💼 Quản lý giao dịch</a></li>
                    <% if ("AD".equals(loginUser.getRoleID())) { %>
                <li><a href="MainController?action=ListUsers">👤 Quản lý người dùng</a></li>
                    <% } %>
                <li><a href="MainController?action=ListAlert"> Quản lý cảnh báo đầu tư</a> </li>
                <li><a href="MainController?action=Logout">🚪 Đăng xuất</a></li>
            </ul>
        </div>
    </body>
</html>
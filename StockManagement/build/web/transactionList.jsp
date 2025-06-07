<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="dto.TransactionDTO, dto.UserDTO, java.util.List"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Danh sách giao dịch</title>
        <link rel="stylesheet" href="css/transaction.css">
    </head>
    <body>

        <%
            UserDTO loginUser = (UserDTO) session.getAttribute("LOGIN_USER");
            if (loginUser == null) {
                response.sendRedirect("login.jsp");
                return;
            }
        %>

        <h2>💼 Danh sách giao dịch</h2>

        <% if ("NV".equals(loginUser.getRoleID())) { %>
        <a href="MainController?action=AddTransactionPage" class="add-button">➕ Thêm giao dịch</a>
        <% } %>

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
                    <th>Mã GD</th>
                    <th>Mã CP</th>
                    <th>Giá</th>
                    <th>Số lượng</th>
                    <th>Thời gian</th>
                    <th>Người dùng</th>
                    <% if ("NV".equals(loginUser.getRoleID())) { %><th>Hành động</th><% } %>
                </tr>
                <%
                    List<TransactionDTO> list = (List<TransactionDTO>) request.getAttribute("TRANS_LIST");
                    int index = 1;
                    if (list != null) {
                        for (TransactionDTO t : list) {
                %>
                <tr>
                    <td><%= index++ %></td>
                    <td><%= t.getTransID() %></td>
                    <td><%= t.getTicker() %></td>
                    <td><%= t.getPrice() %></td>
                    <td><%= t.getQuantity() %></td>
                    <td><%= t.getTransDate() %></td>
                    <td><%= t.getUserID() %></td>
                    <% if ("NV".equals(loginUser.getRoleID()) && loginUser.getUserID().equals(t.getUserID())) { %>
                    <td class="actions">
                        <a href="MainController?action=EditTransaction&transID=<%= t.getTransID() %>">✏️ Sửa</a>
                        <a href="MainController?action=DeleteTransaction&transID=<%= t.getTransID() %>"
                           onclick="return confirm('Bạn có chắc muốn xóa?')">🗑️ Xóa</a>
                    </td>

                    <% } %>
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

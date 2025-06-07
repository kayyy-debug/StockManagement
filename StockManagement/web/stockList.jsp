<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List, dto.StockDTO, dto.UserDTO"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
    UserDTO loginUser = (UserDTO) session.getAttribute("LOGIN_USER");
    pageContext.setAttribute("loginUser", loginUser);
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Danh sách cổ phiếu</title>
    <link rel="stylesheet" href="css/stock.css">
</head>
<body>
    <div class="container">
        <h2>📈 Danh sách cổ phiếu</h2>
        
        <!-- Thông báo -->
        <c:if test="${not empty MESSAGE}">
            <div class="message">${MESSAGE}</div>
        </c:if>
        <c:if test="${not empty ERROR}">
            <div class="error">${ERROR}</div>
        </c:if>

        <table>
            <thead>
                <tr>
                    <th>Mã cổ phiếu</th>
                    <th>Tên công ty</th>
                    <th>Giá</th>
                    <th>Trạng thái</th>
                    <c:if test="${loginUser.roleID == 'AD'}">
                        <th>Hành động</th>
                    </c:if>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="s" items="${STOCK_LIST}">
                    <tr>
                        <td>${s.ticker}</td>
                        <td>${s.companyName}</td>
                        <td>${s.price}</td>
                        <td>
                            <c:choose>
                                <c:when test="${s.status}">Đang giao dịch</c:when>
                                <c:otherwise>Ngừng giao dịch</c:otherwise>
                            </c:choose>
                        </td>
                        <c:if test="${loginUser.roleID == 'AD'}">
                            <td>
                                <a href="MainController?action=EditStock&ticker=${s.ticker}">✏️ Sửa</a>
                                <a href="MainController?action=DeleteStock&ticker=${s.ticker}" onclick="return confirm('Xác nhận xóa?')">🗑️ Xóa</a>
                            </td>
                        </c:if>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <c:if test="${loginUser.roleID == 'AD'}">
            <br>
            <a href="addStock.jsp">➕ Thêm cổ phiếu</a>
        </c:if>
    </div>

    <div style="margin-top: 20px;">
        <a href="welcome.jsp" class="back-link">← Quay lại trang chính</a>
    </div>
</body>
</html>
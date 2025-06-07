<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="dto.StockDTO"%>

<!DOCTYPE html>
<html>
<head>
    <title>Cập nhật cổ phiếu</title>
    <link rel="stylesheet" href="css/updatestock.css">
</head>
<body>
    <% 
        StockDTO stock = (StockDTO) request.getAttribute("EDIT_STOCK");
        if (stock == null) {
            response.sendRedirect("MainController?action=ListStocks");
            return;
        }
    %>

    <div class="update-stock-form">
        <h2>✏️ Cập nhật cổ phiếu</h2>
        
        <% if (request.getAttribute("ERROR") != null) { %>
            <div class="error"><%= request.getAttribute("ERROR") %></div>
        <% } %>

        <form action="MainController" method="POST">
            <input type="hidden" name="ticker" value="<%= stock.getTicker() %>">

            <label for="companyName">Tên công ty:</label>
            <input type="text" name="companyName" id="companyName" value="<%= stock.getCompanyName() %>" required><br>

            <label for="price">Giá:</label>
            <input type="number" step="0.01" name="price" id="price" value="<%= stock.getPrice() %>" required><br>

            <label for="status">Trạng thái:</label>
            <select name="status" id="status">
                <option value="1" <%= stock.isStatus() ? "selected" : "" %>>Đang giao dịch</option>
                <option value="0" <%= !stock.isStatus() ? "selected" : "" %>>Ngừng giao dịch</option>
            </select><br>

            <input type="submit" name="action" value="UpdateStock">
        </form>

        <a href="MainController?action=ListStocks" class="back-link">← Quay lại danh sách</a>
    </div>
</body>
</html>
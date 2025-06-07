<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <title>Thêm cổ phiếu</title>
    <link rel="stylesheet" href="css/addstock.css">
</head>
<body>
    <div class="add-stock-form">
        <h2>➕ Thêm cổ phiếu mới</h2>
        
        <% if (request.getAttribute("ERROR") != null) { %>
            <div class="error"><%= request.getAttribute("ERROR") %></div>
        <% } %>
        
        <% if (request.getAttribute("MESSAGE") != null) { %>
            <div class="success"><%= request.getAttribute("MESSAGE") %></div>
        <% } %>
        
        <form action="MainController" method="POST">
            <label for="ticker">Mã cổ phiếu:</label>
            <input type="text" name="ticker" id="ticker" required><br>
            
            <label for="companyName">Tên công ty:</label>
            <input type="text" name="companyName" id="companyName" required><br>
            
            <label for="price">Giá:</label>
            <input type="number" step="0.01" name="price" id="price" required><br>
            
            <input type="submit" name="action" value="CreateStock">
        </form>
        
        <a href="MainController?action=StockList" class="back-link">← Quay lại danh sách</a>
    </div>
</body>
</html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List, dto.StockDTO"%>

<!DOCTYPE html>
<html>
<head>
    <title>Thêm giao dịch</title>
    <link rel="stylesheet" href="css/addtransaction.css">
</head>
<body>
    <div class="add-transaction-form">
        <h2>➕ Giao dịch mới</h2>
        
        <% if (request.getAttribute("ERROR") != null) { %>
            <div class="error"><%= request.getAttribute("ERROR") %></div>
        <% } %>
        
        <% if (request.getAttribute("MESSAGE") != null) { %>
            <div class="success"><%= request.getAttribute("MESSAGE") %></div>
        <% } %>
        
        <form action="MainController" method="POST">
            <label for="ticker">Mã cổ phiếu:</label>
            <select name="ticker" id="ticker">
                <% 
                    List<StockDTO> stocks = (List<StockDTO>) request.getAttribute("STOCK_LIST");
                    if (stocks != null) {
                        for (StockDTO s : stocks) {
                            if (s.isStatus()) {
                %>
                    <option value="<%= s.getTicker() %>"><%= s.getTicker() %> - <%= s.getCompanyName() %></option>
                <% 
                            }
                        }
                    }
                %>
            </select><br>
            
            <label for="price">Giá:</label>
            <input type="number" name="price" id="price" step="0.01" required><br>
            
            <label for="quantity">Số lượng:</label>
            <input type="number" name="quantity" id="quantity" required><br>
            
            <input type="submit" name="action" value="CreateTransaction">
        </form>
        
        <a href="MainController?action=ListTransactions" class="back-link">← Quay lại danh sách</a>
    </div>
</body>
</html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List, dto.StockDTO, dto.TransactionDTO"%>

<!DOCTYPE html>
<html>
<head>
    <title>Cập nhật giao dịch</title>
    <link rel="stylesheet" href="css/updatetransaction.css">
</head>
<body>
    <% 
        TransactionDTO trans = (TransactionDTO) request.getAttribute("EDIT_TRANS");
        List<StockDTO> stocks = (List<StockDTO>) request.getAttribute("STOCK_LIST");
        if (trans == null || stocks == null) {
            response.sendRedirect("MainController?action=ListTransactions");
            return;
        }
    %>

    <div class="update-transaction-form">
        <h2>✏️ Cập nhật giao dịch</h2>
        
        <% if (request.getAttribute("ERROR") != null) { %>
            <div class="error"><%= request.getAttribute("ERROR") %></div>
        <% } %>

        <form action="MainController" method="POST">
            <input type="hidden" name="transID" value="<%= trans.getTransID() %>">

            <label for="ticker">Mã cổ phiếu:</label>
            <select name="ticker" id="ticker">
                <% 
                    for (StockDTO s : stocks) {
                        if (s.isStatus() || s.getTicker().equals(trans.getTicker())) {
                %>
                    <option value="<%= s.getTicker() %>" <%= s.getTicker().equals(trans.getTicker()) ? "selected" : "" %>>
                        <%= s.getTicker() %> - <%= s.getCompanyName() %>
                    </option>
                <% 
                        }
                    }
                %>
            </select><br>

            <label for="price">Giá:</label>
            <input type="number" name="price" id="関係" step="0.01" value="<%= trans.getPrice() %>" required><br>

            <label for="quantity">Số lượng:</label>
            <input type="number" name="quantity" id="quantity" value="<%= trans.getQuantity() %>" required><br>

            <input type="submit" name="action" value="UpdateTransaction">
        </form>

        <a href="MainController?action=ListTransactions" class="back-link">← Quay lại danh sách</a>
    </div>
</body>
</html>
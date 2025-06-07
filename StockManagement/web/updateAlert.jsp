<%-- 
    Document   : updateAlert
    Created on : Jun 6, 2025, 10:05:14 PM
    Author     : Asus
--%>
<%@page import="DAO.AlertDTO" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Alert Page</title>
    </head>
    <body>
        <%
       AlertDTO alert = (AlertDTO) request.getAttribute("ALERT");
       if (alert == null) {
           response.sendRedirect("alertList.jsp");
           return;
       }
        %>
        <h2>Update Alert</h2>
        <% if (request.getAttribute("ERROR") != null) { %>
        <p style="color:red"><%= request.getAttribute("ERROR") %></p>
        <% } %>
        <form action="MainController" method="POST">
            ID: <input type="text" name="alertID" value="<%= alert.getAlertID() %>" readonly><br>
            Ticker: <input type="text" name="ticker" value="<%= alert.getTicker() %>" readonly><br>
            Threshold: <input type="number" name="threshold" value="<%= alert.getThreshold() %>" step="0.01" required><br>
            Direction: <select name="direction">
                <option value="increase" <%= "increase".equals(alert.getDirection()) ? "selected" : "" %>>Increase</option>
                <option value="decrease" <%= "decrease".equals(alert.getDirection()) ? "selected" : "" %>>Decrease</option>
            </select><br>
            Status: <select name="status">
                <option value="inactive" <%= "inactive".equals(alert.getStatus()) ? "selected" : "" %>>Inactive</option>
                <option value="active" <%= "active".equals(alert.getStatus()) ? "selected" : "" %>>Active</option>
                <option value="pending" <%= "pending".equals(alert.getStatus()) ? "selected" : "" %>>Pending</option>
            </select><br>
            <input type="submit" name="action" value="UpdateAlert">
            <a href="alertList.jsp">Return</a>
        </form>
    </body>
</html>

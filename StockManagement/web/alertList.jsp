<%-- 
    Document   : alertList
    Created on : Jun 6, 2025, 10:04:43 PM
    Author     : Asus
--%>
<%@page import="java.util.List" %>
<%@page import="dto.UserDTO" %>
<%@page import="dto.AlertDTO" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>List Alert Page</title>
    </head>
    <body>
        <%
       UserDTO loginUser = (UserDTO) session.getAttribute("LOGIN_USER");
       if (loginUser == null) {
           response.sendRedirect("login.jsp");
           return;
       }
        %>
        <h2>Welcome <%= loginUser.getFullName() %>!</h2>
        <h3>Alert List</h3>
        <a href="MainController?action=AddAlert">Add Alert</a><br>
        <form action="MainController" method="POST">
            Search: <input type="text" name="keyword" value="${KEYWORD}">
            <input type="submit" name="action" value="ViewAlerts">
        </form>
        <% if (request.getAttribute("MESSAGE") != null) { %>
        <p style="color:green"><%= request.getAttribute("MESSAGE") %></p>
        <% } %>
        <% if (request.getAttribute("ERROR") != null) { %>
        <p style="color:red"><%= request.getAttribute("ERROR") %></p>
        <% } %>
        <%
            List<AlertDTO> list = (List<AlertDTO>) request.getAttribute("ALERT_LIST");
            if (list != null && !list.isEmpty()) {
        %>
        <table border="1">
            <tr>
                <th>NO</th>
                <th>ID</th>
                <th>UserID</th>
                <th>Ticker</th>
                <th>Threshold</th>
                <th>Direction</th>
                <th>Status</th>
                <th>Action</th>
            </tr>
            <% int count = 1; for (AlertDTO alert : list) { %>
            <tr>
                <td><%= count++ %></td>
                <td><%= alert.getAlertID() %></td>
                <td><%= alert.getUserID()  %></td>
                <td><%= alert.getTicker() %></td>
                <td><%= alert.getThreshold() %></td>
                <td><%= alert.getDirection() %></td>
                <td><%= alert.getStatus() %></td>
                <td>
                    <a href="MainController?action=UpdateAlertPage&alertID=<%= alert.getAlertID() %>">Update</a>
                    <a href="MainController?action=DeleteAlert&alertID=<%= alert.getAlertID() %>" 
                       onclick="return confirm('Are you sure')">Delete</a>
                </td>
            </tr>
            <% } %>
        </table>
        <% } else { %>
        <p>There is no data</p>
        <% } %>
        <a href="welcome.jsp">Return</a>
    </body>
</html>

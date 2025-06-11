<%-- 
    Document   : addList
    Created on : Jun 11, 2025, 12:52:54 PM
    Author     : Asus
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Them tin tuc</title>
    </head>
    <body>
         <% if (request.getAttribute("ERROR") != null) { %>
            <div class="error"><%= request.getAttribute("ERROR") %></div>
        <% } %>
        
        <% if (request.getAttribute("MESSAGE") != null) { %>
            <div class="success"><%= request.getAttribute("MESSAGE") %></div>
        <% } %>
        
        <form action="MainController" method="POST">
            <input type="hidden" name="newsID">
            Title: <input type="text" name="title" required=""><br/>
            Content: <input type="text" name="content" required=""><br/>
            Sector: <input type="text" name="sectorID" required=""><br/>
            Date: <input type="date" name="publicDate" required=""><br/>
            <input type="submit" name="action" value="CreateNews">
            <a href="newsList.jsp">Quay lai</a>
        </form>
    </body>
</html>

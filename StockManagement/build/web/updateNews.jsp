<%-- 
    Document   : updateList
    Created on : Jun 11, 2025, 12:53:02 PM
    Author     : Asus
--%>
<%@page import="dto.NewsDTO" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cap nhat tin tuc</title>
    </head>
    <body>

        <%
        NewsDTO newsDTO = (NewsDTO) request.getAttribute("EDIT_NEWS");
        if(newsDTO == null){
            response.sendRedirect("newsList.jsp");
            return;
            } 
        %>

        <% if (request.getAttribute("ERROR") != null) { %>
        <div class="error"><%= request.getAttribute("ERROR") %></div>
        <% } %>

        <% if (request.getAttribute("MESSAGE") != null) { %>
        <div class="success"><%= request.getAttribute("MESSAGE") %></div>
        <% } %>

        <form action="MainController" method="POST">
            <input type="hidden" name="newsID" value="<%= newsDTO.getNewsID() %>">
            Title: <input type="text" name="title" value="<%= newsDTO.getTitle() %>" required=""><br/>
            Content: <input type="text" name="content" value="<%= newsDTO.getContent() %>" required=""><br/>
            Sector: <input type="text" name="sectorID" value="<%= newsDTO.getSectorID() %>" readonly=""><br/>
            Date: <input type="date" name="publicDate" value="<%= newsDTO.getPublicDate() %>" readonly=""><br/>
            <input type="submit" name="action" value="UpdateNews">
            <a href="newsList.jsp">Quay lai</a>
        </form>


    </body>
</html>


<%@page import="java.util.List, dto.NewsDTO, dto.UserDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    UserDTO loginUser = (UserDTO) session.getAttribute("LOGIN_USER");
    if (loginUser == null) {
    response.sendRedirect("login.jsp");
    return;
    }
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>News List</title>
    </head>
    <body>
        <h2>Danh sach Tin tuc</h2>
        <% if ("AD".equals(loginUser.getRoleID())) { %> 
        <a href="MainController?action=AddNews">Add News</a><br>
        <% } %>
        <form action="MainController" method="POST">
            Search: <input type="text" name="keyword" value="${KEYWORD}">
            <input type="submit" name="action" value="ViewNews">
        </form>

        <% if (request.getAttribute("MESSAGE") != null) { %>
        <div class="message"><%= request.getAttribute("MESSAGE") %></div>
        <% } %>
        <% if (request.getAttribute("ERROR") != null) { %>
        <div class="error"><%= request.getAttribute("ERROR") %></div>
        <% } %>

        <%List<NewsDTO> list = (List<NewsDTO>) request.getAttribute("NEWS_LIST");
        %>

        <table border="1">
            <tr>
                <th>Ma tin tuc</th>
                <th>Tieu de</th>
                <th>Noi dung</th>
                <th>Nganh</th>
                <th>Ngay dang</th>
                    <% if ("AD".equals(loginUser.getRoleID())) { %>
                <th>Hành động</th>
                    <% } %>
            </tr>
            <%
                if(list != null){ 
                    for(NewsDTO n : list){
            %>
            <tr>
                <td><%= n.getNewsID() %></td>
                <td><%= n.getTitle() %></td>
                <td><%= n.getContent() %></td>
                <td><%= n.getSectorID() %></td>
                <td><%= n.getPublicDate() %></td>

                <% if ("AD".equals(loginUser.getRoleID())) { %>
                <td>
                    <form action="MainController" method="POST" style="display:inline;">
                        <input type="hidden" name="newsID" value="<%= n.getNewsID() %>">
                        <input type="submit" name="action" value="EditNews">
                    </form>

                    <form action="MainController" method="POST" style="display:inline;" onsubmit="return confirm('Bạn có chắc chắn muốn xóa?');">
                        <input type="hidden" name="newsID" value="<%= n.getNewsID() %>">
                        <input type="submit" name="action" value="DeleteNews">
                    </form>
                </td>
                <% } %>
            </tr>
            <% } %>
            <% } %>
        </table>   
        <a href="welcome.jsp">Return</a>
    </body>
</html>

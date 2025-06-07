<%-- 
    Document   : addAlert
    Created on : Jun 6, 2025, 10:04:07 PM
    Author     : Asus
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Alert Page</title>
    </head>
    <body>
        <form action="MainController" method="POST">
            Ticker: <input type="text" name="ticker" required=""><br>
            Threshold: <input type="number" name="threshold" step="0.01" required=""><br>
            Direction: <select name="direction">
                <option value="increase">Increase</option>
                <option value="decrease">Decrease</option>
            </select><br>
            <input type="submit" name="action" value="AddAlert">
            <a href="alertList.jsp">Return</a>
        </form>
    </body>
</html>



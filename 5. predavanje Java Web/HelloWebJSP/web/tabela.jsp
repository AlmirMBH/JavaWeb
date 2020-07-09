
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tabelarni prikaz</title>
        <style>
            table, td, th{
                border: solid 1px solid black;
                border-collapse: collapse;
            }
        </style>
    </head>
    <body>
        
        <%@include file="header.html" %>
        
        <br/>
        <hr>
        
        <h1>Prikaz vaših imena</h1>
        <%-- Varijable names tretira se kao da je napisana u okviru process request--%>
        <table border="1">
            <tr><th>Imena</th></tr>
                    <%
                        String[] names = {"Almir", "Tarik", "Elmedin", "Ivana", "Zana"};
                        for (int i = 0; i < names.length; i++) {
                    %><tr><td><%=names[i]%></td></tr>                
                        <%}%>
        </table>
        <br>
        <hr>
                        <%@include file="footer.html" %>
    </body>
</html>

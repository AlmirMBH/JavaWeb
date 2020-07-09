
<%@page import="java.time.LocalDateTime"%>
<%@page import="java.util.Date" %> <!-- Direktiva -->
<%@page contentType="text/html" pageEncoding="UTF-8"%> <!-- Direktiva -->
<!DOCTYPE html>
<html>
    
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Hello</title>
    </head>
    <body>
        <%@include file="header.html" %>
        
        <h1>Dobrodošli na stranicu Hello JSP</h1>
        <h2>Danas je <%= new Date()%></h2> <!-- Izraz -->
        <h2>Danas je <%= LocalDateTime.now()%></h2> 
        <%
        double value = Math.random();
        if(value < 0.5){
        %><h3>Dobro jutro!</h3>    
        <%}else{        
        %><h3>Dobro veče!</h3>
        <%}%>

        
    </body>
</html>

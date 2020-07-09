
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!-- Gornja linija koda: jsp kreira objekat naziva 'user', koji će biti po šablonu klase
UserBean i uredi da taj objekat živi sve dok je korisnik u sesiji scope="session" -->
<jsp:useBean id="user" class="bean.UserBean" scope="session" />
<!-- Setovanje objektnih polja objekta po šablonu klase UserBean-->
<jsp:setProperty name="user" property="username" param="korisnickoIme" />
<jsp:setProperty name="user" property="password" param="lozinka" />


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome</title>
    </head>
    <body>
        <% boolean logged = user.login();
           
            if(logged){
        %><h1>Dobro došao!</h1>
        <% }else{
                %><h1>Neuspješna prijava!</h1>
                <%}%>
    </body>
</html>

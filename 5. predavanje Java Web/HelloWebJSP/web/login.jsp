
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
    </head>
    <body>
        <h1>Uloguj se</h1>
        
        <form method="POST" action="pozdrav.jsp">
            <input type="text" name="korisnickoIme">
            <input type="password" name="lozinka">
            <input type="submit" value="Prijavi se">
        </form>
    </body>
</html>

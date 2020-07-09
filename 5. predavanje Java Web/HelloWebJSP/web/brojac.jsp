
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!-- Ovako kreirana varijabla je kao da je u servletu kreirano objektno polje, a ako je unutar
     skripleta ispod tretira se kao da je u okviru proces request (doGet ili doPost) -->
<%! int ukupanBrojProdatihKarata = 0; %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Prodaja karata</title>
    </head>
    <body>
        <h3>Ukupan broj prodatih karata: <%=++ukupanBrojProdatihKarata%></h3>
        
        <%
        if(session.getAttribute("brojac") == null){
            session.setAttribute("brojac", 1);
        }else{
            Integer brojacNaNivouKorisnika = (Integer)session.getAttribute("brojac");
            brojacNaNivouKorisnika++;
            session.setAttribute("brojac", brojacNaNivouKorisnika); 

        %><h5>Kupili ste <%=brojacNaNivouKorisnika%> karata</h5><%}%>
        
        
    </body>
</html>

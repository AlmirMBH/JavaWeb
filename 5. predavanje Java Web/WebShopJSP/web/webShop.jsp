
<%@page import="shop.bean.UserBean"%>
<%@page import="shop.model.Product"%>
<%@page import="java.util.List"%>
<%@page import="shop.model.ProductReader"%> <!-- Direktiva -->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%! ProductReader productReader = new ProductReader();%> <!-- Deklaracija (kao atribut kompletnog servleta) -->
<jsp:useBean id="userBean" class="shop.bean.UserBean"/>
<jsp:setProperty name="userBean" property="username" param="korisnickoIme"/>
<jsp:setProperty name="userBean" property="password" param="lozinka"/>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Web Shop</title>
    </head>
    <body>  
          <%  if(userBean.login()){%>
        <h3>Welcome to our web shop</h3>
        <table border="1" style="border-collapse: collapse">
            <tr bgcolor="lightgrey"><th>ID</th><th>Ime Proizvoda</th><th>Cijena</th><th>U Korpu</th></tr>
            <%                
            String path = application.getRealPath("products.txt"); // Unutar process request
            List<Product> products = productReader.readProducts(path);
            for(Product product : products){                
                %>
            <tr>
                <td><%=product.getId()%></td>
                <td><%=product.getProductName()%></td>
                <td><%=product.getPrice()%></td>
                <td>
                    <form action="shoppingCart.jsp" method="get">
                    <input type="number" name="kolicina" size="3"/>
                    <input type="hidden" name="productId" value="<%=product.getId()%>"/>
                    <input type="submit" value="Dodaj"/>
                    </form>
                </td>
            </tr>
              <%}%>      
        </table>
    <%}else{%>
     <h3>You have to log in first!</h3>
     <%}%>
    
    </body>
</html>

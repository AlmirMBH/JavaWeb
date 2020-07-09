
<%@page import="shop.model.ShoppingCartItem"%>
<%@page import="shop.model.ProductReader" %>
<%@page import="java.util.List"%>
<%@page import="shop.model.Product"%>
<%@page import="shop.model.ShoppingCart"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%! ProductReader productReader =new ProductReader(); %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Proizodi u korpi</title>
    </head>
    <body>
        <h1>Proizodi u korpi</h1>
        
        <%
        if(session.getAttribute("korpa")==null){
            session.setAttribute("korpa", new ShoppingCart());
        }
        %>
        
        <%
        ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("korpa");
        String path = application.getRealPath("products.txt");
        List<Product> products = productReader.readProducts(path);
        // productId i quantity
        for(Product product : products){
            String productIdParam = request.getParameter("productId");
            String quantityParam = request.getParameter("kolicina");
            int productId = Integer.parseInt(productIdParam);
            int quantity = Integer.parseInt(quantityParam);
            if(product.getId()==productId){
                shoppingCart.addShoppingCartItem(product, quantity);
            }
        }        
        %>
        
        <table border="1" style="border-collapse: collapse">
            <tr bgcolor="lightgrey"><th>Ime Proizvoda</th><th>Jedinicna cijena</th><th>Kolicina</th><th>Ukupna cijena</th></tr>
            <%                
            
            for(ShoppingCartItem item : shoppingCart.getShoppingCartItems()){                
                %>
            <tr>
                <td><%=item.getProduct().getProductName() %></td>
                <td><%=item.getProduct().getPrice() %></td>
                <td><%=item.getQuantity() %></td>
                <td><%=item.getTotalPrice() %></td>                
            </tr>
              <%}%>      
        </table>        
    </body>
</html>

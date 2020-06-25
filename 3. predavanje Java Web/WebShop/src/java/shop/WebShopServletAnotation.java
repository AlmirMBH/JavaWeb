
package shop;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import shop.model.Product;


@WebServlet(name = "NoviWebShopServlet", urlPatterns = {"/WebShopServletAnotation"})
public class WebShopServletAnotation extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config); // Standardan poziv metode, samo jednom unutar servletskog
                            // kontejnera kada se kreira WebShopServlet. Odmah se u 
                            // ServletContext objekat upisuje lista proizvoda iz baze   
    
    List<Product> products = new ArrayList<>();                        
    // Zajednički objekat (singleton) za sve servlete na nivou aplikacije, živi dok je 
    //  'živa' aplikacija (skladište podataka iz baze/fajla)
    
    ServletContext application = getServletContext(); 
    String path = application.getRealPath("products.txt"); 
    // Putanja do products.txt. Ako imamo folder, a onda u njemu fajl, piše se npr.
    // application.getRealPath("imeFoldera/products.txt"); 
    
    try(BufferedReader bufferedReader = new BufferedReader(new FileReader(path))){        
        
        String line = null;
        
        while((line = bufferedReader.readLine()) != null){
            // String[] fields = line.split(";");
            // Čitanje proizvoda iz products.txt, razdvajanje po ";", prebacivanje u 
            // stringove i konverzija u String, int i double
            StringTokenizer stringTokenizer = new StringTokenizer(line, ";");
            int id = Integer.parseInt(stringTokenizer.nextToken());
            String productName = stringTokenizer.nextToken();
            double price = Double.parseDouble(stringTokenizer.nextToken());
            
            // Kreiranje objekata 'product', ubacivanje vrijednosti iz stringova iznad
            Product product = new Product(id, productName, price);
            
            // Dodavanje pojedinačnih vrijednosti objekata u listu products (iznad)            
            products.add(product);
        }
        
        // Dodavanje objekata iz liste u application objekat ServletContext (iznad), kako
        // bi bio dostupan na nivou cijele aplikacije
        application.setAttribute(Constants.PRODUCTS, products);
        
    }catch(IOException exception){
        throw new RuntimeException(exception.getMessage());
    }
    }  
    
    // Svaki put kad se uradi refresh, izvrši se processRequest metoda
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        ServletContext application = getServletContext();  
        
        try (PrintWriter out = response.getWriter()) {            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Products</title>");            
            out.println("</head>");
            out.println("<body>");
            
            List<Product> products = (List<Product>) getServletContext().getAttribute(Constants.PRODUCTS);
            
            if(products != null && !products.isEmpty()){
                out.println("<h2>Dostupni proizvodi</h2>");
                out.println("<table>");
                out.println("<tr bgcolor='lightgray'><th>Naziv</th><th>Cijena</th><th>Korpu</th></tr>");
                
                for(Product product : products){
                    out.println("<tr>");
                    out.println("<td>" + product.getProductName() + "</td>"); 
                    out.println("<td>" + product.getPrice() + "</td>"); 
                    out.println("<td></td>");             
                    out.println("</tr>");                
                }
                out.println("</table>");
            }else{
                out.println("<h2>There are no products available at the moment</h2>");
            }
            out.println("</body>");
            out.println("</html>");
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}

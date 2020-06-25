
package header;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "HttpAttributesServlet", urlPatterns = {"/HttpZaglavlje"})

public class HttpAttributesServlet extends HttpServlet {

// Suština postojanja ovog servleta je da obradi klijentski zahtjev (browseri)    

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Analiza HTTP zaglavlja</title>");            
            out.println("</head>");
            out.println("<body>");
// Klijentski zahtjev je u formatu key-value, osim prve linije gdje je navedena metoda 
// (get, post...). Metoda ispod vraća nam imena svih ključeva iz zaglavlja HTTP zahtjeva
// Enumeration je kolekcija, kao lista imena u header-u, a request ima parove key-value
            Enumeration<String> headerNames = request.getHeaderNames(); // implem. iterables
            
            out.println("<p>");
            while(headerNames.hasMoreElements()){
                String headerName = headerNames.nextElement();
                String headerValue = request.getHeader(headerName); // Dohvatanje 'values'
                out.println(headerName + "   " + headerValue);
                out.println("<br>");
            }
            out.println("</p>");
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

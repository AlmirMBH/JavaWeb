
package pageVisits;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//Ukoliko ne uradimo konfiguraciju u xml-u, onda se to radi automatski preko anotacija
// VisitCounterServlet-u pristupa se preko slug-a "visitCounter". Anotacija je popularnija.

@WebServlet(name = "VisitCounterServlet", urlPatterns = {"/visitCounter"})
public class VisitCounterServlet extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Broj posjeta servera</title>");            
            out.println("</head>");
            out.println("<body>");
            // Za ukupan broj posjeta koristimo application objekat (ServletContext) koji će
            // dijeliti svi servleti. Broji jednu posjetu svaki put kad neko posjeti sajt ili
            // uradi refresh.
            // SKLADIŠTE KOJE SVI DIJELE (SIGURNI REPOZITORIJ)
            ServletContext application = getServletContext();
            Counter counter = (Counter) application.getAttribute("UKUPAN_BROJ_POSJETA");
            if(counter == null){ // If konstrukcija se izvršava samo jednom
                counter = new Counter();                
                application.setAttribute("UKUPAN_BROJ_POSJETA", counter);
            }
            counter.increment();
            
            // Session za praćenje individualnih posjeta jednog korisnika
            HttpSession session = request.getSession();
            Counter sessionCounter = (Counter) session.getAttribute("INDIVIDUALNI_BROJ_POSJETA:");
            if(sessionCounter == null){
                sessionCounter = new Counter();                
                session.setAttribute("INDIVIDUALNI_BROJ_POSJETA:", sessionCounter);                
            }            
            sessionCounter.increment();
            
            out.println("<h3>Ukupan broj posjeta: " + counter.getCount() + "</h3>");
            out.println("<br/>");
            out.println("<h3>Individualni broj posjeta: " + sessionCounter.getCount() + "</h3>");
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

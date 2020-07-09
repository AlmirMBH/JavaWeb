
package servleti;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
// Servlet je Javina klasa koja nasljeđuje HTTpServlet
/* Necemo mi pozivati konstruktor klase nego će to za nas raditi ServletContainer
Mi možemo override-ati jednu od 4 metode:
1. init- poziva se kada se kreira servletski objekat, poziva se jednom
2. doGet- poziva se kada dodje GET komanda u HTTP requestu,
3. doPost- kada dodje POST komanda,
4. destroy- poziva se kada garbage collector cisti iz memorije na serveru
*/

public class FirstServlet extends HttpServlet {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet FirstServlet</title>");            
            out.println("</head>");
            out.println("<body>");
         out.println("<h1>Trenutno vrijeme "+ LocalDateTime.now() + "</h1>");
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

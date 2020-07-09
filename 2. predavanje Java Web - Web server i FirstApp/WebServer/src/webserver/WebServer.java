package webserver;
// primjer web servera koji opsluzuje klijenta(mozila firefox, google chrome) nekim
// statičkim sadržajem.
// cilj nam je pročitati šta je došlo iz socketa i odgovoriti koristeći http protokol
// HTTP protokol je tekstualni protokol koji funkcioniše po principu request-odgovor.
// I request i response imaju uređen format kako izgledaju. Svaki request formatiran je u
// skladu sa standardom, a sastoji se od:
// 1.HTTP header sastoji se od metode (GET ili POST) za fajl kojeg zahtijevamo
// 2. HTTP body-a ( tu se nalazi neki html sadrzaj)
// Response isto se sastoji od Header-a i Body-a. ( Ovdje se prva linija sastoji od oznake
// protokola, broj koji predstavlja status izvršene operacije i tekstualni opis)
// Stateles (http)- svaki request response je neovisan od prethodnog request-response-a,
// iako dolazi od istog klijenta. Za cuvanje username-a i passworda, odnosno korisnika u
// sesiji ne možemo koristiti osobine HTTP protokola, već koristimo cookies, url rewriting.
// Stateful - request-response povezani su sa prethodnim request-response

//Statusi response-a:
//oni koji počinju sa 200 uspješno je obavljena komunikacija
// 300 - redirekcija na neki drugi server
// 400 - Klijent nije dobro kontaktirao server
// 500 - Klijent je dobro kontaktirao server, ali odgovor ne može biti poslan zbog npr.
// loše internetske veze, nismo implementirali taj dio..
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {

    /*KOraci:
    1. napraviti statičko polje broja servera,
    2. napraviti ServerSocket objekat i proslijediti mu broj porta,
    3. napraviti klijentski socket (Socket klasa) i accept-ati serverski socket.(serverSocket.accept)
    3. Izvući iz Input streama šta klijent hoće od nas (čitamo prvu liniju requesta).
       Metodu koja to radi nazvali smo getResource.
    .  Pravimo metodu koja šalje odgovor   
     */

    private static final int SERVER_PORT = 8080;

    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            Socket clientSocket = serverSocket.accept();

            // Izvući iz inputSTreama šta klijent hoće kroz dole napravljenu metodu
            String resource = getResource(clientSocket.getInputStream());
            sendResponse(resource, clientSocket.getOutputStream());
            clientSocket.close();
            
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    // metoda kojom dohvatamo InputSream, odnosno obrađujemo prvu liniju da znamo šta hoće
    public static String getResource(InputStream inputStream) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new 
                        InputStreamReader(inputStream));
        
        String prviRed = bufferedReader.readLine();
        String[] elementiPrvogReda = prviRed.split(" ");
        System.out.println("prvi element " + elementiPrvogReda[0]);
        if (!("GET".equals(elementiPrvogReda[0]))) {
            return "one.html";
        }
        // koristimo substring da bismo skinuli liniju '/' koja se nalazi ispred rijeci
        String resource = elementiPrvogReda[1].substring(1);
        if (resource.isEmpty()) {
            return "index.html";
        }
        return resource;
    }

private static void sendResponse(String resource, OutputStream outputStream) throws Exception {
        PrintStream printStream = new PrintStream(outputStream);
        if (null == resource) {
            printStream.print("HTTP/1.0 500 Internal Server\r\n\r\n");
            printStream.flush();// da bi je poslali
            return;
        }
        File file = new File(resource);
        if (!file.exists()) {
            printStream.print("HTTP/1.0 404 File not found\r\n\r\n");
            return;
        }
        
        // razmak \r\n\r\n zapravo dijeli glavu od body-a i time smo ispoštovali HTTP format
        printStream.print("HTTP/1.0 200 OK \r\n\r\n");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            printStream.print(line);
            printStream.flush();

        }
    }
}

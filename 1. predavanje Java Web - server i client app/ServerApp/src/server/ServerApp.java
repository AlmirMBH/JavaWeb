package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// server ima ulogu osluškivača
//1. Kreirat ćemo server-socket objekat
//2. Čekat će klijentski zahtjev
//3. Otvorit ćemo output stream kada budemo slali podatke klijentskoj aplikaciji i 
// input stream da bi znali šta treba servirati klijentima
// 4. Komunicirat ćemo sa klijentom 
//5. Zatvorit ćemo stream i socket

public class ServerApp {

public static void main(String[] args) {

// Kada radimo sa socket-ima, neophodni su nam host i port. S obzirom da smo
// mi pasivni, poruke ćemo slati preko klijenta koji ce već biti konfigurisan
// sa hostom, mi cemo samo sa portom
// Provjeravamo da li je postavljena konfiguracija

if (args.length != 1) {
    System.out.println("Nedostaje konfiguracija porta na kojem radi server");
    System.exit(1);
}

int portNumber = Integer.parseInt(args[0]);
System.out.println("Server je startao na portu: " + portNumber);

// 1.Kreiramo server-socket objekat
// prilikom njegovog kreiranja njegov konstruktor moze baciti IO Exception,
// a s obzirom da je klasa SocketServer closeable pišemo ga u try catch bloku koda

try (ServerSocket serverSocket = new ServerSocket(portNumber);) {
    // 2. Čekamo na klijentski zahtjev
    // While beskonačna petlja jer je jedan socket jedan klijent, ako hoćemo da ih
    // imamo više moramo ih opsluživati u petlji

    while (true) {
        Socket clientSocket = serverSocket.accept();
        System.out.println("Klijent se povezao na portu: " + portNumber);

        // 3. korak smo prebacili u Requesthandler klasu da bi se pozivala iz drugog
        // thread-a, jer ako bismo je ostavili u main funkciji mogla bi opsluživati
        // samo jednog klijenta
        // Postoji klasa ExecutorService koja može kreirati pool objekata kreiranih
        // prema klasi RequestHandler npr. 5 odjednom (5 Runnable objekata)
        
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        RequestHandler requestHandler = new RequestHandler(clientSocket);
        executorService.execute(requestHandler);
    }
} catch (IOException e) {
    throw new RuntimeException(e.getMessage());
}

}
}

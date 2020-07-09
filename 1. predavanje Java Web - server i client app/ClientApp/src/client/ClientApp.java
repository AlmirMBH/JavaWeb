package client;

// pravimo klijentsku aplikaciju koja ce komunicirati sa serverom, ovdje nam je potrebna
// konfiguracija i sa hostom i sa portom
import com.sun.tracing.dtrace.ArgsAttributes;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import javax.management.RuntimeErrorException;

// ova aplikacija ce raditi:
// 1. Kreirati i otvoriti klijentski socket objekat
// 2. Otvoriti output stream i input stream iz socketa
// 3. Čitati i pisati serverskoj aplikaciji po protokolu mreze
// 4. Zatvoriti stream i zatvoriti socket

public class ClientApp {

    public static void main(String[] args) {

        // Provjera da li je konfiguracija dobra
        System.out.println("Host(IP adress): " + args[0] + " port: " + args[1]);
        if (args.length != 2) {
            System.out.println("Klijent treba konfigurisati host i port");
            System.exit(1);
        }

        String hostAdress = args[0];
        int portNumber = Integer.parseInt(args[1]);

        // 1. Kreiramo Socket, a u ServerApp kreiramo ServerSocket
        
        try (Socket clientSocket = new Socket(hostAdress, portNumber);) {
            // 2. Iniciranje konekcije sa serverom, odnosno pisanje u outputStream-u i čitanje 
            // poruka servera kroz input stream
            // Output:
            OutputStream outputStream = clientSocket.getOutputStream();
            PrintWriter out = new PrintWriter(outputStream, true);

            System.out.println("Upisi nesto za server: ");
            String userInput;
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()) {
                userInput = scanner.nextLine();

                // Slanje poruke serveru 
                out.println(userInput);
                // S obzirom da su podaci u petlji, moramo ih poslati serveru komandom 'flush'
                out.flush();

                // Input, čime nas je server servisirao; ubacili smo u while petlju jer nikad ne dođe
                // do koda ispod jer ostajemo u mrtvoj petlji
                //InputStream inputStream = clientSocket.getInputStream();
                BufferedReader inputStreamReader = new BufferedReader(new 
                        InputStreamReader(clientSocket.getInputStream()));
                System.out.println(inputStreamReader.readLine());
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

}

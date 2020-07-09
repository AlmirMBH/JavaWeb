package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class RequestHandler implements Runnable {
// da bi znao handlati jedan zahtjev on mora imati atribut socket

    private final Socket clientSocket;

    public RequestHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        // 3.Obraditi zahtjev od klijenta: input i output stream:
        // koristimo buffer jer nećemo da čitamo bajt po bajt već liniju po liniju
        try {
            BufferedReader inputStreamReader = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));

            // output:
            OutputStream outputStream = clientSocket.getOutputStream();
            // autoflush: true - šalje podatke klijentu
            PrintWriter out = new PrintWriter(outputStream, true);

            //4. Komunikacija sa klijentskom aplikacijom
            String line;
            while ((line = inputStreamReader.readLine()) != null) {
                System.out.println("Primio sam od klijentske aplikacije: " + line);
                out.println("Dobio sam line od tebe " + line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());

        } finally {
        }
    }
}

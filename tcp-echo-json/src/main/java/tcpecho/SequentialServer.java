package tcpecho;

import com.fasterxml.jackson.databind.JsonNode;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class SequentialServer {
    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(Config.PORT, 50, InetAddress.getByName(Config.HOST));
        System.out.println("Serwer czeka na klientów...");

        while (true) {
            Socket socket = serverSocket.accept();
            try (socket;
                JsonConnection connection = new JsonConnection(socket)) {
           
                JsonNode message;
           
                while ((message = connection.read()) != null) {
                   System.out.println("Odebrano: " + message);
                   connection.send(message);
                }
                } 
            catch (IOException e) {
                System.err.println("Błąd obsługi klienta: " + e.getMessage());
            }
        }

    }
}

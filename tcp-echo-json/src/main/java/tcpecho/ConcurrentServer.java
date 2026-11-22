package tcpecho;

import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ConcurrentServer {
    public static void main(String[] args) throws IOException {
        try (ServerSocket server = new ServerSocket(
                Config.PORT, 50, InetAddress.getByName(Config.HOST))) {

            System.out.println("Serwer czeka na klientów...");

            while (true) {
                Socket socket = server.accept();
                new Thread(() -> handleClient(socket)).start();
            }
        }
    }

    private static void handleClient(Socket socket) {
        try (socket;
             JsonConnection connection = new JsonConnection(socket)) {

            JsonNode message;

            while ((message = connection.read()) != null) {
                System.out.println("Odebrano: " + message);
                connection.send(message);
            }
        } catch (IOException e) {
            System.err.println(
                    "Błąd obsługi klienta: " + e.getMessage());
        }
    }
}
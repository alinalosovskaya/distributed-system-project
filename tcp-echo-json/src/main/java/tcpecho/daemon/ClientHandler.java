package tcpecho.daemon;

import com.fasterxml.jackson.databind.JsonNode;
import tcpecho.common.JsonConnection;

import java.io.IOException;
import java.net.Socket;

final class ClientHandler {
    static void handle(Socket socket) {
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
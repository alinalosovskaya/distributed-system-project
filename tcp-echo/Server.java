import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Server {
    public static void main(String[] args) throws IOException {
        try (ServerSocket server = new ServerSocket()) {
            server.bind(new InetSocketAddress("127.0.0.1", 8765));
            System.out.println("Serwer nasłuchuje na 127.0.0.1:8765");

            while (true) {
                Socket client = server.accept();
                try (client) {
                    byte[] message = JsonMessage.read(
                            new BufferedInputStream(client.getInputStream()));
                    client.getOutputStream().write(message);
                    System.out.println("Odebrano " + new String(message, UTF_8));
                } catch (IOException error) {
                    System.err.println("Błąd połączenia " + error.getMessage());
                }
            }
        }
    }
}

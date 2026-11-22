package tcpecho;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Client {
    public static void main(String[] args) throws IOException {
        int count = args.length == 0 ? 1 : Integer.parseInt(args[0]);

        if (count < 1) {
            throw new IllegalArgumentException(
                "Liczba wiadomości musi być dodatnia");
        }

        ObjectMapper mapper = new ObjectMapper();

        

        try (Socket socket = new Socket(Config.HOST, Config.PORT);
            JsonConnection connection = new JsonConnection(socket)) {
             
                for (int i = 1; i <= count; i++) {

                    JsonNode message = mapper.createObjectNode()
                    .put("sender", "Administrator")
                    .put("message", "Wiadomość " + i);

                    connection.send(message);
                    JsonNode answer = connection.read();
                
                    if (!message.equals(answer)) {
                        throw new IOException("Niepoprawna odpowiedź serwera");
                    }
                
                    System.out.println(
                            "Odpowiedź " + i + ": " + answer);
                }
                
        }
    }    
}

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Client {
    public static void main(String[] args) throws IOException {
        String json = "{\n"
        + "  \"message\": \"Hello! }\",\n"
        + "  \"recipients\": [\"Tatiana\", \"Alina\"],\n"
        + "  \"sender\": \"Administrator\",\n"
        + "  \"priority\": 1,\n"
        + "  \"urgent\": true,\n"
        + "  \"metadata\": {\"topic\": \"projekt\"}\n"
        + "}";
        byte[] sent = json.getBytes(UTF_8);

        try (Socket socket = new Socket("127.0.0.1", 8765)) {
            socket.getOutputStream().write(sent);
            byte[] received = JsonMessage.read(
                    new BufferedInputStream(socket.getInputStream()));

            if (!Arrays.equals(sent, received)) {
                throw new IOException("Odpowiedź różni się od wysłanej wiadomości");
            }

            System.out.println("Odebrano dokładnie ten sam JSON");
            System.out.println(new String(received, UTF_8));
        }
    }
}

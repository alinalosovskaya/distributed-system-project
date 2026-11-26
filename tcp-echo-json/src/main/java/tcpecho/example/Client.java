package tcpecho.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import tcpecho.api.MsgApi;
import tcpecho.api.SendResult;
import tcpecho.common.Config;

import java.io.IOException;

public class Client {
    public static void main(String[] args) throws IOException {
        int count = args.length == 0
                ? 1
                : Integer.parseInt(args[0]);

        if (count < 1) {
            throw new IllegalArgumentException(
                    "Liczba wiadomości musi być dodatnia");
        }

        ObjectMapper mapper = new ObjectMapper();

        try (MsgApi api = new MsgApi(Config.HOST, Config.PORT)) {
            for (int i = 1; i <= count; i++) {
                JsonNode message = mapper.createObjectNode()
                        .put("sender", "Administrator")
                        .put("message", "Wiadomość " + i);

                SendResult result = api.send(message);

                if (!result.isSuccess()) {
                    throw new IOException(
                            "Błąd wysyłania [" + result.code() + "]: "
                                    + result.errorMessage());
                }

                JsonNode answer = api.receive();

                if (!message.equals(answer)) {
                    throw new IOException(
                            "Niepoprawna odpowiedź serwera");
                }

                System.out.println(
                        "Odpowiedź " + i + ": " + answer);
            }
        }
    }
}
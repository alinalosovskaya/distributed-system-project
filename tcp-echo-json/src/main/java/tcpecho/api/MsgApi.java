package tcpecho.api;

import com.fasterxml.jackson.databind.JsonNode;
import tcpecho.common.JsonConnection;

import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

public class MsgApi implements AutoCloseable {
    private final Socket socket;
    private final JsonConnection connection;

    public MsgApi(String host, int port) throws IOException {
        socket = new Socket(host, port);

        try {
            connection = new JsonConnection(socket);
        } catch (IOException | RuntimeException e) {
            try {
                socket.close();
            } catch (IOException closeError) {
                e.addSuppressed(closeError);
            }
            throw e;
        }
    }

    public SendResult send(JsonNode message) {
        if (message == null) {
            return new SendResult(
                    SendResult.Code.INVALID_MESSAGE,
                    "Wiadomość nie może być null.");
        }

        try {
            connection.send(message);
            return new SendResult(SendResult.Code.OK, null);
        } catch (IOException e) {
            return new SendResult(
                    SendResult.Code.IO_ERROR,
                    "Nie udało się wysłać wiadomości: "
                            + e.getMessage());
        }
    }

    public JsonNode receive() throws IOException {
        JsonNode message = connection.read();

        if (message == null) {
            throw new EOFException(
                    "Serwer zamknął połączenie.");
        }

        return message;
    }

    @Override
    public void close() throws IOException {
        try (socket) {
            connection.close();
        }
    }
}
package tcpecho;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class JsonConnection implements AutoCloseable {
    private final ObjectMapper mapper = new ObjectMapper();
    private final JsonParser parser;
    private final Writer output;

    public JsonConnection(Socket socket) throws IOException {
        parser = mapper.getFactory().createParser(new InputStreamReader(
                socket.getInputStream(), StandardCharsets.UTF_8));

        output = new OutputStreamWriter(
                socket.getOutputStream(), StandardCharsets.UTF_8);
    }

    public JsonNode read() throws IOException {
        return mapper.readTree(parser);
    }

    public void send(JsonNode message) throws IOException {
        output.write(mapper.writeValueAsString(message));
        output.write("\n");
        output.flush();
    }

    @Override
    public void close() throws IOException {
        parser.close();
    }
}
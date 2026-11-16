import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

final class JsonMessage {
    private JsonMessage() {
    }

    static byte[] read(InputStream input) throws IOException {
        ByteArrayOutputStream message = new ByteArrayOutputStream();
        int depth = 0;
        boolean inString = false;
        boolean escaped = false;

        for (int value; (value = input.read()) != -1; ) {
            message.write(value);

            if (depth == 0) {
                if (value == ' ' || value == '\t' || value == '\r' || value == '\n') {
                    continue;
                }
                if (value != '{' && value != '[') {
                    throw new IOException("Oczekiwano obiektu lub tablicy JSON");
                }
                depth = 1;
                continue;
            }

            if (inString) {
                if (escaped) {
                    escaped = false;
                } else if (value == '\\') {
                    escaped = true;
                } else if (value == '"') {
                    inString = false;
                }
            } else if (value == '"') {
                inString = true;
            } else if (value == '{' || value == '[') {
                depth++;
            } else if (value == '}' || value == ']') {
                depth--;
                if (depth == 0) {
                    return message.toByteArray();
                }
            }
        }

        throw new EOFException("Połączenie zamknięto przed końcem wiadomości JSON");
    }
}

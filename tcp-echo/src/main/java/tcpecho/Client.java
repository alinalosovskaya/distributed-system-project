package tcpecho;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(Config.HOST, Config.PORT);

        Writer out = new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));

        out.write("{\"hello\":\"world\"}\n");
        out.flush();

        String answer = in.readLine();
        System.out.println("Odpowiedź serwera: " + answer);

        socket.close();
    }
}
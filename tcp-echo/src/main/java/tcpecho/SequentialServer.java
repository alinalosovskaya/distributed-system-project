package tcpecho;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class SequentialServer {
    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(Config.PORT, 50, InetAddress.getByName(Config.HOST));
        System.out.println("Serwer czeka na klientów...");

        while (true) {
            Socket socket = serverSocket.accept();

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            Writer out = new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8);

            String message = in.readLine();
            System.out.println("Odebrano: " + message);

            out.write(message + "\n");
            out.flush();

            socket.close();
        }
    }
}
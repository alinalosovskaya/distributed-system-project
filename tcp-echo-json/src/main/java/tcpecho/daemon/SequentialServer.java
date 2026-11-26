package tcpecho.daemon;

import tcpecho.common.Config;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class SequentialServer {
    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(
                Config.PORT, 50, InetAddress.getByName(Config.HOST))) {

            System.out.println("Serwer sekwencyjny czeka na klientów...");

            while (true) {
                Socket socket = serverSocket.accept();
                ClientHandler.handle(socket);
            }
        }
    }
}
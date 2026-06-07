package network;

import java.net.ServerSocket;
import java.net.Socket;

/** THIRD-YEAR SOCKET CHECK: ServerSocket 5000번 포트. 별도 실행: java -cp out network.VendingServer */
public class VendingServer {
    public static void main(String[] args) { new VendingServer().start(); }
    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("VendingServer started on port 5000");
            while (true) { Socket s = serverSocket.accept(); new ClientHandler(s).start(); }
        } catch (Exception e) { System.out.println("Server error: " + e.getMessage()); }
    }
}

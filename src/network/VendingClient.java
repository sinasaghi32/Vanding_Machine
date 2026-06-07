package network;

import exception.NetworkException;

import java.io.PrintWriter;
import java.net.Socket;

/**
 * THIRD-YEAR SOCKET CHECK: localhost:5000 서버로 메시지를 보낸다.
 * 다른 PC 3대에서 테스트할 때는 "localhost"를 서버 PC IP 주소(예: 192.168.0.10)로 바꾸면 된다.
 */
public class VendingClient {
    private final String host; private final int port;
    public VendingClient() { this("localhost", 5000); }
    public VendingClient(String host, int port) { this.host = host; this.port = port; }
    public void send(String message) throws NetworkException {
        try (Socket socket = new Socket(host, port); PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) { out.println(message); }
        catch (Exception e) { throw new NetworkException("서버 전송 실패", e); }
    }
}

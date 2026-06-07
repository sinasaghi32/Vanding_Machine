package network;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;

/** 서버에서 각 클라이언트를 담당하는 Thread. 두 개 이상 클라이언트 동시 처리 가능. */
public class ClientHandler extends Thread {
    private final Socket socket;
    public ClientHandler(Socket socket) { this.socket = socket; }
    @Override public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8))) {
            String line; while ((line = in.readLine()) != null) ServerState.saveMessage(line);
        } catch (Exception e) { ServerState.saveMessage("ERROR|" + e.getMessage()); }
    }
}
class ServerState {
    private static final Path LOG = Path.of("server_data", "server_log.txt");
    private static final Path SUMMARY = Path.of("server_data", "server_summary.txt");
    static synchronized void saveMessage(String msg) {
        try {
            Files.createDirectories(LOG.getParent());
            Files.writeString(LOG, LocalDateTime.now()+"|"+msg+System.lineSeparator(), StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            // THIRD-YEAR SOCKET CHECK: 서버가 판매/재고/저재고/변경 메시지를 누적 관리한다.
            String[] p = msg.split("\\|");
            if (p.length > 0) Files.writeString(SUMMARY, classify(p, msg)+System.lineSeparator(), StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (Exception ignored) { }
    }
    private static String classify(String[] p, String msg) {
        if (p[0].equals("SALE") && p.length >= 6) return "SALES_SUMMARY|date="+p[2].substring(0,10)+"|month="+p[2].substring(0,7)+"|vm="+p[1]+"|drink="+p[3]+"|amount="+p[4];
        if (p[0].equals("STOCK") && p.length >= 4) return "REALTIME_STOCK|vm="+p[1]+"|drink="+p[2]+"|stock="+p[3];
        if (p[0].equals("LOW_STOCK")) return "WARNING_LOW_STOCK|"+msg;
        if (p[0].equals("CHANGE_NAME") || p[0].equals("CHANGE_PRICE") || p[0].equals("REFILL")) return "ADMIN_CHANGE|"+msg;
        return "MESSAGE|"+msg;
    }
}

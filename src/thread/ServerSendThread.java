package thread;

import datastructure.MyQueue;
import network.VendingClient;

/** THIRD-YEAR THREAD CHECK: 서버 전송 대기 큐에서 메시지를 꺼내 Socket으로 전송한다. */
public class ServerSendThread extends Thread {
    private final MyQueue<String> queue; private final VendingClient client = new VendingClient(); private volatile boolean running = true;
    public ServerSendThread(MyQueue<String> queue) { this.queue = queue; setDaemon(true); }
    public void stopThread() { running = false; interrupt(); }
    @Override public void run() {
        while (running) {
            try {
                String msg = queue.dequeue();
                if (msg != null) client.send(msg); else Thread.sleep(1000);
            } catch (Exception e) { try { Thread.sleep(3000); } catch (InterruptedException ignored) {} }
        }
    }
}

package thread;

import manager.VendingMachineManager;

/** THIRD-YEAR THREAD CHECK: 10초마다 파일 자동 저장. GUI가 멈추지 않도록 daemon thread 사용. */
public class AutoSaveThread extends Thread {
    private final VendingMachineManager manager; private volatile boolean running = true;
    public AutoSaveThread(VendingMachineManager manager) { this.manager = manager; setDaemon(true); }
    public void stopThread() { running = false; interrupt(); }
    @Override public void run() { while (running) { try { Thread.sleep(10000); manager.saveAll(); } catch (Exception ignored) {} } }
}

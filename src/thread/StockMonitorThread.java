package thread;

import datastructure.DrinkLinkedList;
import model.Drink;
import datastructure.MyQueue;

/** THIRD-YEAR THREAD CHECK: 5초마다 저재고(stock<=2)를 확인하고 서버 큐에 LOW_STOCK 메시지를 넣는다. */
public class StockMonitorThread extends Thread {
    private final DrinkLinkedList drinks; private final MyQueue<String> queue; private volatile boolean running = true;
    public StockMonitorThread(DrinkLinkedList drinks, MyQueue<String> queue) { this.drinks = drinks; this.queue = queue; setDaemon(true); }
    public void stopThread() { running = false; interrupt(); }
    @Override public void run() { while (running) { for (Drink d: drinks.toArray()) if (d.getStock() <= 2) queue.enqueue("LOW_STOCK|VM001|"+d.getName()+"|"+d.getStock()); try { Thread.sleep(5000); } catch (InterruptedException ignored) {} } }
}

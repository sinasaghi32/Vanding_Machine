package manager;

import database.LocalDatabaseManager;
import datastructure.DrinkLinkedList;
import datastructure.MyQueue;
import exception.*;
import file.DrinkFileManager;
import model.Drink;
import model.MoneyInput;
import model.SaleRecord;

/**
 * 핵심 비즈니스 로직을 묶는 Facade.
 * BASIC/3RD-YEAR CHECK: GUI, 파일, DB 계층, 커스텀 자료구조, 서버 전송 큐를 연결한다.
 */
public class VendingMachineManager {
    private final DrinkFileManager drinkFileManager = new DrinkFileManager();
    private final DrinkLinkedList drinks;
    private final MoneyManager moneyManager;
    private final SalesManager salesManager = new SalesManager();
    private final AdminManager adminManager;
    private final StockManager stockManager;
    private final SortSearchManager sortSearchManager = new SortSearchManager();
    private final LocalDatabaseManager dbManager = new LocalDatabaseManager();
    private final MyQueue<String> serverQueue = new MyQueue<>();
    private boolean adminActive;

    public VendingMachineManager() throws FileDataException {
        drinks = drinkFileManager.loadDrinks();
        moneyManager = new MoneyManager();
        adminManager = new AdminManager();
        stockManager = new StockManager(drinks, drinkFileManager);
        salesManager.ensureFiles();
        stockManager.ensureFiles();
    }
    public synchronized MoneyInput createMoneyInput() { return new MoneyInput(); }
    public synchronized void insertMoney(MoneyInput input, int unit) throws InvalidMoneyException, FileDataException {
        if (adminActive) throw new InvalidMoneyException("관리자 화면 사용 중에는 판매 기능이 잠깁니다.");
        moneyManager.acceptMoney(input, unit); dbManager.saveMoneyStatus(moneyManager.getCoinStorage());
    }
    public synchronized String buyDrink(int id, MoneyInput input) throws SoldOutException, NotEnoughMoneyException, ChangeNotAvailableException, FileDataException {
        if (adminActive) throw new NotEnoughMoneyException("관리자 화면 사용 중에는 구매할 수 없습니다.");
        Drink d = drinks.findById(id);
        if (d == null || d.isSoldOut()) throw new SoldOutException("품절");
        if (input == null || input.getTotal() < d.getPrice()) throw new NotEnoughMoneyException("투입 금액이 부족합니다.");
        int change = input.getTotal() - d.getPrice();
        if (!moneyManager.getCoinStorage().canMakeChange(change)) throw new ChangeNotAvailableException("거스름돈 없음");
        d.sellOne();
        SaleRecord record = new SaleRecord(d.getName(), d.getPrice(), 1);
        String changeText = moneyManager.returnChange(change);
        salesManager.recordSale(record); drinkFileManager.saveDrinks(drinks); stockManager.markSoldOutIfNeeded(d);
        dbManager.saveSaleSummary(record); dbManager.saveStockRecord(d.getName()+" stock="+d.getStock()); dbManager.saveMoneyStatus(moneyManager.getCoinStorage());
        serverQueue.enqueue(record.toServerMessage()); serverQueue.enqueue("STOCK|VM001|"+d.getName()+"|"+d.getStock());
        if (d.getStock() <= 2) serverQueue.enqueue("LOW_STOCK|VM001|"+d.getName()+"|"+d.getStock());
        return d.getName() + " 배출 완료. " + changeText;
    }
    public synchronized String refund(MoneyInput input) throws ChangeNotAvailableException, FileDataException { String r=moneyManager.refund(input); dbManager.saveMoneyStatus(moneyManager.getCoinStorage()); return r; }
    public DrinkLinkedList getDrinks() { return drinks; }
    public MoneyManager getMoneyManager() { return moneyManager; }
    public SalesManager getSalesManager() { return salesManager; }
    public AdminManager getAdminManager() { return adminManager; }
    public StockManager getStockManager() { return stockManager; }
    public SortSearchManager getSortSearchManager() { return sortSearchManager; }
    public MyQueue<String> getServerQueue() { return serverQueue; }
    public synchronized void saveAll() throws FileDataException { drinkFileManager.saveDrinks(drinks); moneyManager.save(); }
    public synchronized boolean isAdminActive() { return adminActive; }
    public synchronized void setAdminActive(boolean adminActive) { this.adminActive = adminActive; }
}

package manager;

import exception.ChangeNotAvailableException;
import exception.FileDataException;
import exception.InvalidMoneyException;
import file.MoneyFileManager;
import model.CoinStorage;
import model.MoneyInput;

/** 돈 투입, 환불, 거스름돈, 관리자 수금을 담당한다. */
public class MoneyManager {
    private final MoneyFileManager fileManager = new MoneyFileManager();
    private CoinStorage coinStorage;
    public MoneyManager() throws FileDataException { coinStorage = fileManager.loadMoney(); }
    public synchronized void acceptMoney(MoneyInput input, int unit) throws InvalidMoneyException, FileDataException {
        input.addMoney(unit);
        if (unit != 1000) coinStorage.addCoin(unit, 1); // 동전 투입 시 자판기 동전 수 증가
        save();
    }
    public synchronized String refund(MoneyInput input) throws ChangeNotAvailableException, FileDataException {
        int amount = input == null ? 0 : input.getTotal();
        if (amount == 0) return "환불할 금액이 없습니다.";
        int[] change = coinStorage.calculateChange(amount);
        if (change == null) throw new ChangeNotAvailableException("거스름돈 없음: 환불 불가");
        coinStorage.payChange(change); save();
        return formatChange(amount, change);
    }
    public synchronized String returnChange(int amount) throws ChangeNotAvailableException, FileDataException {
        if (amount <= 0) return "거스름돈 0원";
        int[] change = coinStorage.calculateChange(amount);
        if (change == null) throw new ChangeNotAvailableException("거스름돈 없음");
        coinStorage.payChange(change); save();
        return formatChange(amount, change);
    }
    public synchronized int collectMoneyKeepingMinimum() throws FileDataException { int c = coinStorage.collectKeepingMinimum(); save(); return c; }
    public synchronized CoinStorage getCoinStorage() { return coinStorage; }
    public synchronized void save() throws FileDataException { fileManager.saveMoney(coinStorage); }
    private String formatChange(int amount, int[] c) { int[] u = coinStorage.getUnits(); return amount + "원 반환 (500/100/50/10원: " + c[0]+"/"+c[1]+"/"+c[2]+"/"+c[3]+")"; }
}

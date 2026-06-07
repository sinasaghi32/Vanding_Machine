package model;

import datastructure.MyQueue;
import exception.InvalidMoneyException;

/**
 * 투입 금액 객체.
 * BASIC CHECK: CustomerPanel/MoneyManager에서 new MoneyInput()으로 동적 생성한다.
 * Java는 C처럼 free()가 없고, 구매/환불 뒤 참조를 null로 바꾸면 Garbage Collector가 정리한다.
 */
public class MoneyInput {
    public static final int BILL_LIMIT = 5000;
    public static final int TOTAL_LIMIT = 7000;
    private int total;
    private int billTotal;
    private MyQueue<Integer> inputHistory = new MyQueue<>(); // CUSTOM QUEUE CHECK

    public void addMoney(int unit) throws InvalidMoneyException {
        if (!(unit == 10 || unit == 50 || unit == 100 || unit == 500 || unit == 1000)) {
            throw new InvalidMoneyException("사용할 수 없는 돈 단위입니다: " + unit);
        }
        if (unit == 1000 && billTotal + unit > BILL_LIMIT) {
            throw new InvalidMoneyException("지폐 투입 한도 5000원을 초과했습니다.");
        }
        if (total + unit > TOTAL_LIMIT) {
            throw new InvalidMoneyException("총 투입 한도 7000원을 초과했습니다.");
        }
        total += unit;
        if (unit == 1000) billTotal += unit;
        inputHistory.enqueue(unit);
    }

    public int getTotal() { return total; }
    public int getBillTotal() { return billTotal; }
    public MyQueue<Integer> getInputHistory() { return inputHistory; }
}

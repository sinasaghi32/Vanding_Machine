package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/** 판매 기록: 파일, Stack, 서버 메시지에 공통 사용. */
public class SaleRecord {
    private final String dateTime;
    private final String drinkName;
    private final int price;
    private final int quantity;

    public SaleRecord(String drinkName, int price, int quantity) {
        this(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), drinkName, price, quantity);
    }

    public SaleRecord(String dateTime, String drinkName, int price, int quantity) {
        this.dateTime = dateTime;
        this.drinkName = drinkName;
        this.price = price;
        this.quantity = quantity;
    }

    public String getDateTime() { return dateTime; }
    public String getDate() { return dateTime.substring(0, 10); }
    public String getMonth() { return dateTime.substring(0, 7); }
    public String getDrinkName() { return drinkName; }
    public int getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public int getAmount() { return price * quantity; }
    public String toCsv() { return dateTime + "," + drinkName + "," + price + "," + quantity; }
    public String toServerMessage() { return "SALE|VM001|" + dateTime + "|" + drinkName + "|" + price + "|" + quantity; }
    public static SaleRecord fromCsv(String line) {
        String[] p = line.split(",", -1);
        return new SaleRecord(p[0], p[1], Integer.parseInt(p[2]), Integer.parseInt(p[3]));
    }
    @Override public String toString() { return dateTime + " " + drinkName + " " + price + "원 x" + quantity; }
}

package model;

/** 음료 한 종류의 정보를 저장한다. id/name/price/stock/soldCount를 모두 포함한다. */
public class Drink {
    private int id;
    private String name;
    private int price;
    private int stock;
    private int soldCount;

    public Drink(int id, String name, int price, int stock, int soldCount) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.soldCount = soldCount;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getPrice() { return price; }
    public int getStock() { return stock; }
    public int getSoldCount() { return soldCount; }
    public boolean isSoldOut() { return stock <= 0; }
    public void setName(String name) { this.name = name; }
    public void setPrice(int price) { this.price = price; }
    public void setStock(int stock) { this.stock = Math.max(0, stock); }
    public void addStock(int amount) { if (amount > 0) stock += amount; }
    public void sellOne() { stock--; soldCount++; }

    public String toCsv() { return id + "," + name + "," + price + "," + stock + "," + soldCount; }
    public static Drink fromCsv(String line) {
        String[] p = line.split(",", -1);
        return new Drink(Integer.parseInt(p[0]), p[1], Integer.parseInt(p[2]), Integer.parseInt(p[3]), Integer.parseInt(p[4]));
    }

    @Override public String toString() {
        return id + ". " + name + " / " + price + "원 / 재고 " + stock + " / 판매 " + soldCount + (isSoldOut() ? " / 품절" : "");
    }
}

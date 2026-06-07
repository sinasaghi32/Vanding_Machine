package manager;

import datastructure.DrinkLinkedList;
import exception.FileDataException;
import file.DrinkFileManager;
import file.StockHistoryFileManager;
import model.Drink;

/** 재고 보충/품절 이력/이름 가격 변경 담당. */
public class StockManager {
    private final DrinkLinkedList drinks;
    private final DrinkFileManager drinkFileManager;
    private final StockHistoryFileManager historyFileManager = new StockHistoryFileManager();
    public StockManager(DrinkLinkedList drinks, DrinkFileManager dfm) { this.drinks = drinks; this.drinkFileManager = dfm; }
    public void ensureFiles() throws FileDataException { historyFileManager.ensureFile(); }
    public synchronized void refill(int id, int amount) throws FileDataException {
        Drink d = drinks.findById(id); if (d == null || amount <= 0) return;
        d.addStock(amount); drinkFileManager.saveDrinks(drinks); historyFileManager.appendHistory("REFILL", d.getName()+","+amount+",stock="+d.getStock());
    }
    public synchronized void markSoldOutIfNeeded(Drink d) throws FileDataException { if (d.isSoldOut()) historyFileManager.appendHistory("SOLD_OUT", d.getName()+",stock=0"); }
    public synchronized void changeName(int id, String newName) throws FileDataException { Drink d=drinks.findById(id); if(d==null)return; String old=d.getName(); d.setName(newName); drinkFileManager.saveDrinks(drinks); historyFileManager.appendHistory("CHANGE_NAME", old+"->"+newName); }
    public synchronized void changePrice(int id, int price) throws FileDataException { Drink d=drinks.findById(id); if(d==null)return; d.setPrice(price); drinkFileManager.saveDrinks(drinks); historyFileManager.appendHistory("CHANGE_PRICE", d.getName()+"->"+price); }
    public String historyText() throws FileDataException { StringBuilder sb=new StringBuilder(); for(String s:historyFileManager.loadHistory()) sb.append(s).append('\n'); return sb.length()==0?"이력 없음":sb.toString(); }
}

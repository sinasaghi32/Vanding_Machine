package manager;

import datastructure.MyStack;
import exception.FileDataException;
import file.SalesFileManager;
import model.SaleRecord;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

/** 판매 기록 저장과 일/월 매출 계산. CUSTOM STACK CHECK: 최근 판매 내역 저장. */
public class SalesManager {
    private final SalesFileManager fileManager = new SalesFileManager();
    private final MyStack<SaleRecord> recentSales = new MyStack<>();
    public void ensureFiles() throws FileDataException { fileManager.ensureFiles(); }
    public void recordSale(SaleRecord r) throws FileDataException { recentSales.push(r); fileManager.appendSale(r); }
    public int getDailyTotal(LocalDate date) throws FileDataException { int sum=0; for(SaleRecord r:fileManager.loadDaily()) if(r.getDate().equals(date.toString())) sum+=r.getAmount(); return sum; }
    public int getMonthlyTotal(YearMonth m) throws FileDataException { int sum=0; for(SaleRecord r:fileManager.loadMonthly()) if(r.getMonth().equals(m.toString())) sum+=r.getAmount(); return sum; }
    public String getDailyByDrink(LocalDate date) throws FileDataException { return summarize(fileManager.loadDaily(), date.toString()); }
    public String getMonthlyByDrink(YearMonth m) throws FileDataException { return summarize(fileManager.loadMonthly(), m.toString()); }
    private String summarize(List<SaleRecord> list, String prefix) {
        StringBuilder sb = new StringBuilder();
        for (SaleRecord r : list) if (r.getDate().startsWith(prefix) || r.getMonth().equals(prefix)) sb.append(r.getDrinkName()).append(" : ").append(r.getAmount()).append("원\n");
        return sb.length() == 0 ? "기록 없음" : sb.toString();
    }
    public String recentSalesText() { Object[] arr = recentSales.toArrayNewestFirst(20); if(arr.length==0)return "최근 판매 없음"; StringBuilder sb=new StringBuilder(); for(Object o:arr) sb.append(o).append('\n'); return sb.toString(); }
}

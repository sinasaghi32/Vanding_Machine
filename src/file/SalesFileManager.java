package file;

import exception.FileDataException;
import model.SaleRecord;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/** data/sales_daily.txt, sales_monthly.txt: 구매 때마다 저장되는 판매 기록. */
public class SalesFileManager extends FileManager {
    private static final Path DAILY = Path.of("data", "sales_daily.txt");
    private static final Path MONTHLY = Path.of("data", "sales_monthly.txt");
    public void ensureFiles() throws FileDataException {
        if (readLines(DAILY).isEmpty()) writeLines(DAILY, new ArrayList<>());
        if (readLines(MONTHLY).isEmpty()) writeLines(MONTHLY, new ArrayList<>());
    }
    public void appendSale(SaleRecord r) throws FileDataException { appendLine(DAILY, r.toCsv()); appendLine(MONTHLY, r.toCsv()); }
    public List<SaleRecord> loadDaily() throws FileDataException { return load(DAILY); }
    public List<SaleRecord> loadMonthly() throws FileDataException { return load(MONTHLY); }
    private List<SaleRecord> load(Path path) throws FileDataException {
        List<SaleRecord> out = new ArrayList<>();
        try { for (String line : readLines(path)) if (!line.isBlank()) out.add(SaleRecord.fromCsv(line)); }
        catch (Exception e) { throw new FileDataException("판매 파일 형식 오류: " + path, e); }
        return out;
    }
}

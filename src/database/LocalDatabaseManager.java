package database;

import exception.FileDataException;
import model.CoinStorage;
import model.SaleRecord;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;

/**
 * THIRD-YEAR DATABASE CHECK.
 * SQLite JDBC는 외부 jar가 필요할 수 있어서 기본 실행성을 해치지 않도록 파일 기반 local DB 계층을 분리했다.
 * 일반 file 패키지와 별도로 admin_db.txt에 판매/재고/돈 상태를 동기화한다.
 */
public class LocalDatabaseManager {
    private static final Path DB = Path.of("data", "admin_db.txt");
    public synchronized void saveSaleSummary(SaleRecord r) throws FileDataException { append("SALE_DB|" + r.toCsv()); }
    public synchronized void saveStockRecord(String message) throws FileDataException { append("STOCK_DB|" + LocalDateTime.now() + "|" + message); }
    public synchronized void saveMoneyStatus(CoinStorage storage) throws FileDataException { append("MONEY_DB|" + LocalDateTime.now() + "|" + storage); }
    private void append(String line) throws FileDataException {
        try { Files.createDirectories(DB.getParent()); Files.writeString(DB, line + System.lineSeparator(), StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND); }
        catch (IOException e) { throw new FileDataException("로컬 DB 저장 실패", e); }
    }
}

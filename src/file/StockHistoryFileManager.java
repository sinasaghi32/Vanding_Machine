package file;

import exception.FileDataException;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

/** data/stock_history.txt: 품절, 보충, 이름/가격 변경 이력 저장. */
public class StockHistoryFileManager extends FileManager {
    private static final Path PATH = Path.of("data", "stock_history.txt");
    public void ensureFile() throws FileDataException { if (readLines(PATH).isEmpty()) writeLines(PATH, new java.util.ArrayList<>()); }
    public void appendHistory(String type, String message) throws FileDataException { appendLine(PATH, LocalDateTime.now() + "," + type + "," + message); }
    public List<String> loadHistory() throws FileDataException { return readLines(PATH); }
}

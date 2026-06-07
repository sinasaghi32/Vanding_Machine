package file;

import exception.FileDataException;
import model.CoinStorage;

import java.nio.file.Path;
import java.util.List;

/** data/money.txt: 현재 자판기 동전 상태 저장. */
public class MoneyFileManager extends FileManager {
    private static final Path PATH = Path.of("data", "money.txt");
    public CoinStorage loadMoney() throws FileDataException {
        List<String> lines = readLines(PATH);
        if (lines.isEmpty()) { CoinStorage c = new CoinStorage(); saveMoney(c); return c; }
        try { return CoinStorage.fromCsv(lines.get(0)); }
        catch (Exception e) { throw new FileDataException("돈 파일 형식 오류", e); }
    }
    public void saveMoney(CoinStorage storage) throws FileDataException { writeLines(PATH, List.of(storage.toCsv())); }
}

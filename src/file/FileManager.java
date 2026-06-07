package file;

import exception.FileDataException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/** 공통 파일 유틸. 파일 오류는 FileDataException으로 GUI에서 처리한다. */
public class FileManager {
    protected void ensureParent(Path path) throws FileDataException {
        try { if (path.getParent() != null) Files.createDirectories(path.getParent()); }
        catch (IOException e) { throw new FileDataException("폴더 생성 실패: " + path, e); }
    }
    protected List<String> readLines(Path path) throws FileDataException {
        try { if (!Files.exists(path)) return new ArrayList<>(); return Files.readAllLines(path, StandardCharsets.UTF_8); }
        catch (IOException e) { throw new FileDataException("파일 읽기 실패: " + path, e); }
    }
    protected void writeLines(Path path, List<String> lines) throws FileDataException {
        try { ensureParent(path); Files.write(path, lines, StandardCharsets.UTF_8); }
        catch (IOException e) { throw new FileDataException("파일 쓰기 실패: " + path, e); }
    }
    protected void appendLine(Path path, String line) throws FileDataException {
        try { ensureParent(path); Files.writeString(path, line + System.lineSeparator(), StandardCharsets.UTF_8, Files.exists(path) ? java.nio.file.StandardOpenOption.APPEND : java.nio.file.StandardOpenOption.CREATE); }
        catch (IOException e) { throw new FileDataException("파일 추가 실패: " + path, e); }
    }
}

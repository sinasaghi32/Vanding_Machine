package file;

import exception.FileDataException;
import model.Admin;

import java.nio.file.Path;
import java.util.List;

/** data/admin.txt: 관리자 비밀번호 저장. 기본값 admin123! */
public class AdminFileManager extends FileManager {
    private static final Path PATH = Path.of("data", "admin.txt");
    public Admin loadAdmin() throws FileDataException {
        List<String> lines = readLines(PATH);
        if (lines.isEmpty()) { Admin a = new Admin("admin123!"); saveAdmin(a); return a; }
        return new Admin(lines.get(0).trim());
    }
    public void saveAdmin(Admin admin) throws FileDataException { writeLines(PATH, List.of(admin.getPassword())); }
}

package manager;

import exception.FileDataException;
import exception.InvalidPasswordException;
import file.AdminFileManager;
import model.Admin;

/** 관리자 로그인/비밀번호 변경. 예외로 잘못된 비밀번호와 형식을 처리한다. */
public class AdminManager {
    private final AdminFileManager fileManager = new AdminFileManager();
    private final Admin admin;
    public AdminManager() throws FileDataException { admin = fileManager.loadAdmin(); }
    public void login(String password) throws InvalidPasswordException { if (!admin.getPassword().equals(password)) throw new InvalidPasswordException("관리자 비밀번호가 틀렸습니다."); }
    public void changePassword(String newPassword) throws InvalidPasswordException, FileDataException { validate(newPassword); admin.setPassword(newPassword); fileManager.saveAdmin(admin); }
    private void validate(String p) throws InvalidPasswordException {
        if (p == null || p.length() < 8) throw new InvalidPasswordException("비밀번호는 8자 이상이어야 합니다.");
        boolean digit=false, special=false; for(char c:p.toCharArray()){ if(Character.isDigit(c)) digit=true; if(!Character.isLetterOrDigit(c)) special=true; }
        if (!digit || !special) throw new InvalidPasswordException("비밀번호는 숫자와 특수문자를 포함해야 합니다.");
    }
}

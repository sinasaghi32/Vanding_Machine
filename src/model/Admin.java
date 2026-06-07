package model;

/** 관리자 계정. 과제에서는 비밀번호 하나만 관리한다. */
public class Admin {
    private String password;
    public Admin(String password) { this.password = password; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}

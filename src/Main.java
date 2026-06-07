import gui.MainFrame;

import javax.swing.SwingUtilities;

/**
 * 자판기 관리 프로그램 시작점.
 * BASIC/3RD-YEAR CHECK: 모든 사용자 기능은 Swing GUI에서 시작한다.
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}

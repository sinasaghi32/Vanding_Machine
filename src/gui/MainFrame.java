package gui;

import exception.FileDataException;
import manager.VendingMachineManager;
import thread.AutoSaveThread;
import thread.ServerSendThread;
import thread.StockMonitorThread;

import javax.swing.*;
import java.awt.*;

/**
 * 메인 GUI 창. title = 자판기 관리 프로그램.
 * BASIC CHECK: 고객 화면과 관리자 화면을 CardLayout으로 명확히 분리한다.
 */
public class MainFrame extends JFrame {
    private final VendingMachineManager manager;
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cards = new JPanel(cardLayout);
    private CustomerPanel customerPanel;

    public MainFrame() {
        setTitle("자판기 관리 프로그램");
        setSize(950, 650); setLocationRelativeTo(null); setDefaultCloseOperation(EXIT_ON_CLOSE);
        VendingMachineManager tmp = null;
        try { tmp = new VendingMachineManager(); } catch (FileDataException e) { JOptionPane.showMessageDialog(this, e.getMessage()); System.exit(1); }
        manager = tmp;
        customerPanel = new CustomerPanel(manager, this);
        cards.add(customerPanel, "CUSTOMER");
        cards.add(new AdminLoginPanel(manager, this), "LOGIN");
        cards.add(new AdminPanel(manager, this), "ADMIN");
        add(cards);
        // THIRD-YEAR CHECK: 의미 있는 사용자 정의 Thread 3개 시작.
        new StockMonitorThread(manager.getDrinks(), manager.getServerQueue()).start();
        new AutoSaveThread(manager).start();
        new ServerSendThread(manager.getServerQueue()).start();
    }
    public void showCustomer() { manager.setAdminActive(false); customerPanel.refresh(); cardLayout.show(cards, "CUSTOMER"); }
    public void showLogin() { manager.setAdminActive(true); customerPanel.refresh(); cardLayout.show(cards, "LOGIN"); }
    public void showAdmin() { manager.setAdminActive(true); cardLayout.show(cards, "ADMIN"); }
}

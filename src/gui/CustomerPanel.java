package gui;

import exception.*;
import manager.VendingMachineManager;
import model.Drink;
import model.MoneyInput;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/** 고객 판매 화면. 돈 투입에 따라 구매 가능한 음료 버튼만 활성화한다. */
public class CustomerPanel extends JPanel {
    private final VendingMachineManager manager; private final MainFrame frame;
    private final JLabel moneyLabel = new JLabel("투입 금액: 0원");
    private final JLabel statusLabel = new JLabel("돈을 투입하세요.");
    private final JPanel drinkPanel = new JPanel(new GridLayout(2, 4, 10, 10));
    private final Map<Integer, JButton> drinkButtons = new HashMap<>();
    // BASIC CHECK: MoneyInput은 new로 동적 생성하고 거래 종료 후 null 처리한다.
    private MoneyInput moneyInput;

    public CustomerPanel(VendingMachineManager manager, MainFrame frame) {
        this.manager = manager; this.frame = frame; setLayout(new BorderLayout(10,10));
        add(topPanel(), BorderLayout.NORTH); add(drinkPanel, BorderLayout.CENTER); add(bottomPanel(), BorderLayout.SOUTH); refresh();
    }
    private JPanel topPanel() {
        JPanel p = new JPanel(new BorderLayout()); JLabel title = new JLabel("고객 판매 화면", SwingConstants.CENTER); title.setFont(new Font("Dialog", Font.BOLD, 24));
        JButton admin = new JButton("관리자 메뉴"); admin.addActionListener(e -> frame.showLogin());
        p.add(title, BorderLayout.CENTER); p.add(admin, BorderLayout.EAST); return p;
    }
    private JPanel bottomPanel() {
        JPanel p = new JPanel(new BorderLayout()); JPanel money = new JPanel();
        for (int unit : new int[]{10,50,100,500,1000}) { JButton b = new JButton(unit+"원"); b.addActionListener(e -> insertMoney(unit)); money.add(b); }
        JButton refund = new JButton("환불"); refund.addActionListener(e -> refund()); money.add(refund);
        p.add(moneyLabel, BorderLayout.NORTH); p.add(money, BorderLayout.CENTER); p.add(statusLabel, BorderLayout.SOUTH); return p;
    }
    public void refresh() {
        drinkPanel.removeAll(); drinkButtons.clear();
        int inserted = moneyInput == null ? 0 : moneyInput.getTotal();
        for (Drink d : manager.getDrinks().toArray()) {
            JButton b = new JButton(buttonText(d));
            b.setEnabled(!manager.isAdminActive() && !d.isSoldOut() && inserted >= d.getPrice());
            b.addActionListener(e -> buy(d.getId())); drinkButtons.put(d.getId(), b); drinkPanel.add(b);
        }
        moneyLabel.setText("투입 금액: " + inserted + "원" + (manager.isAdminActive() ? " (관리자 사용 중 판매 잠김)" : ""));
        revalidate(); repaint();
    }
    private String buttonText(Drink d) { return "<html><center>"+d.getName()+"<br>"+d.getPrice()+"원<br>재고 "+d.getStock()+(d.isSoldOut()?"<br><b>품절</b>":"")+"</center></html>"; }
    private void insertMoney(int unit) {
        try {
            if (moneyInput == null) moneyInput = manager.createMoneyInput();
            manager.insertMoney(moneyInput, unit); statusLabel.setText(unit + "원 투입"); refresh();
        } catch (Exception ex) { JOptionPane.showMessageDialog(this, ex.getMessage()); if (moneyInput != null && moneyInput.getTotal()==0) moneyInput=null; refresh(); }
    }
    private void buy(int id) {
        try {
            String msg = manager.buyDrink(id, moneyInput); JOptionPane.showMessageDialog(this, msg);
            moneyInput = null; // Java GC가 사용하지 않는 객체를 정리한다.
            statusLabel.setText("구매 완료. 다시 돈을 투입하세요."); refresh();
        } catch (SoldOutException | NotEnoughMoneyException | ChangeNotAvailableException | FileDataException ex) { JOptionPane.showMessageDialog(this, ex.getMessage()); refresh(); }
    }
    private void refund() {
        try { String msg = manager.refund(moneyInput); JOptionPane.showMessageDialog(this, msg); moneyInput = null; statusLabel.setText("환불 완료. 다시 돈을 투입하세요."); refresh(); }
        catch (Exception ex) { JOptionPane.showMessageDialog(this, ex.getMessage()); refresh(); }
    }
}

package gui;

import manager.VendingMachineManager;

import javax.swing.*;
import java.awt.*;

/** 관리자 로그인 화면. 고객 화면과 분리되어 있으며 활성화 중 판매가 잠긴다. */
public class AdminLoginPanel extends JPanel {
    public AdminLoginPanel(VendingMachineManager manager, MainFrame frame) {
        setLayout(new GridBagLayout()); GridBagConstraints c = new GridBagConstraints(); c.insets = new Insets(8,8,8,8);
        JLabel title = new JLabel("관리자 로그인"); title.setFont(new Font("Dialog", Font.BOLD, 24));
        JPasswordField pw = new JPasswordField(15); JButton login = new JButton("로그인"); JButton back = new JButton("고객 화면");
        c.gridx=0;c.gridy=0;c.gridwidth=2;add(title,c); c.gridwidth=1;c.gridy=1;add(new JLabel("비밀번호:"),c); c.gridx=1;add(pw,c);
        c.gridx=0;c.gridy=2;add(login,c); c.gridx=1;add(back,c);
        login.addActionListener(e -> { try { manager.getAdminManager().login(new String(pw.getPassword())); frame.showAdmin(); } catch (Exception ex) { JOptionPane.showMessageDialog(this, ex.getMessage()); } });
        back.addActionListener(e -> frame.showCustomer());
    }
}

package gui;

import network.VendingServer;

import javax.swing.*;
import java.awt.*;

/** 선택 GUI 서버 실행 창. 서버 자체는 network.VendingServer로 콘솔 실행도 가능하다. */
public class ServerFrame extends JFrame {
    public ServerFrame() {
        setTitle("Vending Server"); setSize(400,200); setDefaultCloseOperation(EXIT_ON_CLOSE); setLocationRelativeTo(null);
        JTextArea area=new JTextArea("서버 시작 버튼을 누르면 port 5000에서 대기합니다.\n로그: server_data/server_log.txt"); add(new JScrollPane(area), BorderLayout.CENTER);
        JButton start=new JButton("서버 시작"); start.addActionListener(e->{ start.setEnabled(false); new Thread(() -> new VendingServer().start()).start(); area.append("\n서버 실행 중..."); }); add(start, BorderLayout.SOUTH);
    }
    public static void main(String[] args){ SwingUtilities.invokeLater(() -> new ServerFrame().setVisible(true)); }
}

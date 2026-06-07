package gui;

import manager.VendingMachineManager;
import model.Drink;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.YearMonth;

/**
 * 관리자 메뉴 화면.
 * BASIC CHECK: 매출/재고/돈/수금/이름가격변경/최근판매/이력/정렬검색/비밀번호 변경 제공.
 */
public class AdminPanel extends JPanel {
    private final VendingMachineManager manager; private final MainFrame frame; private final JTextArea output = new JTextArea();
    public AdminPanel(VendingMachineManager manager, MainFrame frame) { this.manager=manager; this.frame=frame; setLayout(new BorderLayout(8,8)); add(header(), BorderLayout.NORTH); add(buttons(), BorderLayout.WEST); output.setEditable(false); add(new JScrollPane(output), BorderLayout.CENTER); }
    private JPanel header() { JPanel p=new JPanel(new BorderLayout()); JLabel t=new JLabel("관리자 화면", SwingConstants.CENTER); t.setFont(new Font("Dialog", Font.BOLD, 24)); JButton back=new JButton("관리 종료/고객 화면"); back.addActionListener(e->frame.showCustomer()); p.add(t,BorderLayout.CENTER); p.add(back,BorderLayout.EAST); return p; }
    private JPanel buttons() {
        JPanel p=new JPanel(new GridLayout(0,1,4,4));
        addBtn(p,"일 매출",e->safe(()-> output.setText("오늘 총 매출: "+manager.getSalesManager().getDailyTotal(LocalDate.now())+"원")));
        addBtn(p,"월 매출",e->safe(()-> output.setText("이번 달 총 매출: "+manager.getSalesManager().getMonthlyTotal(YearMonth.now())+"원")));
        addBtn(p,"음료별 일 매출",e->safe(()-> output.setText(manager.getSalesManager().getDailyByDrink(LocalDate.now()))));
        addBtn(p,"음료별 월 매출",e->safe(()-> output.setText(manager.getSalesManager().getMonthlyByDrink(YearMonth.now()))));
        addBtn(p,"재고 보충",e->refill()); addBtn(p,"돈 상태",e->output.setText(manager.getMoneyManager().getCoinStorage().toString()));
        addBtn(p,"관리자 수금",e->safe(()-> output.setText("수금액: "+manager.getMoneyManager().collectMoneyKeepingMinimum()+"원\n최소 동전 5개씩 유지")));
        addBtn(p,"음료 이름 변경",e->changeName()); addBtn(p,"음료 가격 변경",e->changePrice());
        addBtn(p,"최근 판매",e->output.setText(manager.getSalesManager().recentSalesText()));
        addBtn(p,"품절/보충 이력",e->safe(()-> output.setText(manager.getStockManager().historyText())));
        addBtn(p,"정렬/검색",e->sortSearchDialog()); addBtn(p,"비밀번호 변경",e->changePassword()); addBtn(p,"현재 음료 목록",e->showDrinks(manager.getDrinks().toArray()));
        return p;
    }
    private void addBtn(JPanel p,String text,java.awt.event.ActionListener l){ JButton b=new JButton(text); b.addActionListener(l); p.add(b); }
    private void refill(){ safe(()->{ int id=askInt("보충할 음료 ID"); int amount=askInt("보충 수량"); manager.getStockManager().refill(id,amount); Drink d=manager.getDrinks().findById(id); if(d!=null) manager.getServerQueue().enqueue("REFILL|VM001|"+java.time.LocalDateTime.now()+"|"+d.getName()+"|"+amount); output.setText("재고 보충 완료"); }); }
    private void changeName(){ safe(()->{ int id=askInt("변경할 음료 ID"); String n=JOptionPane.showInputDialog(this,"새 이름"); Drink d=manager.getDrinks().findById(id); String old=d==null?"":d.getName(); manager.getStockManager().changeName(id,n); manager.getServerQueue().enqueue("CHANGE_NAME|VM001|"+old+"|"+n); output.setText("이름 변경 완료"); }); }
    private void changePrice(){ safe(()->{ int id=askInt("변경할 음료 ID"); int price=askInt("새 가격"); manager.getStockManager().changePrice(id,price); Drink d=manager.getDrinks().findById(id); if(d!=null) manager.getServerQueue().enqueue("CHANGE_PRICE|VM001|"+d.getName()+"|"+price); output.setText("가격 변경 완료"); }); }
    private void changePassword(){ safe(()->{ String p=JOptionPane.showInputDialog(this,"새 비밀번호(8자+, 숫자, 특수문자 포함)"); manager.getAdminManager().changePassword(p); output.setText("비밀번호 변경 완료"); }); }
    private void sortSearchDialog(){
        String[] opts={"가격순 Bubble Sort","재고순 Selection Sort","판매순 Insertion Sort","이름 Linear Search","가격 Binary Search","가격 Tree Search"};
        String o=(String)JOptionPane.showInputDialog(this,"기능 선택","정렬/검색",JOptionPane.PLAIN_MESSAGE,null,opts,opts[0]); if(o==null)return;
        safe(()->{ Drink[] arr=manager.getDrinks().toArray(); if(o.startsWith("가격순")) showDrinks(manager.getSortSearchManager().sortByPriceBubble(arr)); else if(o.startsWith("재고순")) showDrinks(manager.getSortSearchManager().sortByStockSelection(arr)); else if(o.startsWith("판매순")) showDrinks(manager.getSortSearchManager().sortBySoldInsertion(arr)); else if(o.startsWith("이름")){String name=JOptionPane.showInputDialog(this,"검색 이름"); output.setText(String.valueOf(manager.getDrinks().findByNameLinear(name)));} else if(o.startsWith("가격 Binary")){int price=askInt("검색 가격"); output.setText(String.valueOf(manager.getSortSearchManager().binarySearchByPrice(manager.getSortSearchManager().sortByPriceBubble(arr),price)));} else {int price=askInt("검색 가격"); output.setText(String.valueOf(manager.getSortSearchManager().treeSearchByPrice(arr,price)));} });
    }
    private void showDrinks(Drink[] drinks){ StringBuilder sb=new StringBuilder(); for(Drink d:drinks) sb.append(d).append('\n'); output.setText(sb.toString()); }
    private int askInt(String msg){ return Integer.parseInt(JOptionPane.showInputDialog(this,msg)); }
    private void safe(Task task){ try{ task.run(); } catch(Exception ex){ JOptionPane.showMessageDialog(this, ex.getMessage()); } }
    private interface Task { void run() throws Exception; }
}

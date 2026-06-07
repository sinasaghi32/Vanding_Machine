package datastructure;

import model.Drink;

/**
 * CUSTOM LINKED LIST CHECK: 8개 음료와 재고를 직접 연결 리스트로 관리한다.
 */
public class DrinkLinkedList {
    private DrinkNode head;
    private int size;

    public void add(Drink drink) {
        DrinkNode node = new DrinkNode(drink);
        if (head == null) head = node;
        else {
            DrinkNode cur = head;
            while (cur.next != null) cur = cur.next;
            cur.next = node;
        }
        size++;
    }
    public Drink findById(int id) {
        DrinkNode cur = head;
        while (cur != null) { if (cur.data.getId() == id) return cur.data; cur = cur.next; }
        return null;
    }
    public Drink findByNameLinear(String name) {
        DrinkNode cur = head;
        while (cur != null) { if (cur.data.getName().equalsIgnoreCase(name)) return cur.data; cur = cur.next; }
        return null;
    }
    public Drink[] toArray() {
        Drink[] arr = new Drink[size]; int i = 0;
        DrinkNode cur = head;
        while (cur != null) { arr[i++] = cur.data; cur = cur.next; }
        return arr;
    }
    public int size() { return size; }
    public DrinkNode getHead() { return head; }
}

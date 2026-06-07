package datastructure;

/** CUSTOM STACK CHECK: 최근 판매 기록에 사용한다. */
public class MyStack<T> {
    private static class Node<T> { T data; Node<T> next; Node(T data) { this.data = data; } }
    private Node<T> top;
    private int size;
    public void push(T data) { Node<T> n = new Node<>(data); n.next = top; top = n; size++; }
    public T pop() { if (top == null) return null; T d = top.data; top = top.next; size--; return d; }
    public T peek() { return top == null ? null : top.data; }
    public boolean isEmpty() { return top == null; }
    public int size() { return size; }
    public Object[] toArrayNewestFirst(int limit) {
        int n = Math.min(size, limit); Object[] arr = new Object[n]; Node<T> cur = top;
        for (int i = 0; i < n && cur != null; i++) { arr[i] = cur.data; cur = cur.next; }
        return arr;
    }
}

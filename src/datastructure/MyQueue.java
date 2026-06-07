package datastructure;

/** CUSTOM QUEUE CHECK: 투입 돈 순서와 서버 전송 대기 메시지에 사용한다. */
public class MyQueue<T> {
    private static class Node<T> { T data; Node<T> next; Node(T data) { this.data = data; } }
    private Node<T> front, rear;
    private int size;
    public synchronized void enqueue(T data) {
        Node<T> n = new Node<>(data);
        if (rear == null) front = rear = n; else { rear.next = n; rear = n; }
        size++;
    }
    public synchronized T dequeue() {
        if (front == null) return null;
        T d = front.data; front = front.next; if (front == null) rear = null; size--; return d;
    }
    public synchronized T peek() { return front == null ? null : front.data; }
    public synchronized boolean isEmpty() { return front == null; }
    public synchronized int size() { return size; }
}

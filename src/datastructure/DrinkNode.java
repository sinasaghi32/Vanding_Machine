package datastructure;

import model.Drink;

/** CUSTOM LINKED LIST NODE CHECK: Java 내장 LinkedList 대신 직접 만든 노드. */
public class DrinkNode {
    public Drink data;
    public DrinkNode next;
    public DrinkNode(Drink data) { this.data = data; }
}

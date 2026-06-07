package datastructure;

import model.Drink;

/** CUSTOM TREE NODE CHECK: 가격 검색용 이진 탐색 트리 노드. */
public class TreeNode {
    public int price;
    public Drink drink;
    public TreeNode left, right;
    public TreeNode(Drink drink) { this.price = drink.getPrice(); this.drink = drink; }
}

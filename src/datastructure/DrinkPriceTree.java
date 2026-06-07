package datastructure;

import model.Drink;

/** CUSTOM BINARY SEARCH TREE CHECK: 음료 가격 검색에 사용한다. */
public class DrinkPriceTree {
    private TreeNode root;
    public void insert(Drink drink) { root = insert(root, drink); }
    private TreeNode insert(TreeNode node, Drink drink) {
        if (node == null) return new TreeNode(drink);
        if (drink.getPrice() < node.price) node.left = insert(node.left, drink); else node.right = insert(node.right, drink);
        return node;
    }
    public Drink searchByPrice(int price) { TreeNode n = root; while (n != null) { if (price == n.price) return n.drink; n = price < n.price ? n.left : n.right; } return null; }
}

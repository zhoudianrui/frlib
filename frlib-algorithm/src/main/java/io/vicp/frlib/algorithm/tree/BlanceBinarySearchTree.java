package io.vicp.frlib.algorithm.tree;

/**
 * Created by zhoudr on 2017/4/11.
 */
public class BlanceBinarySearchTree{

    public BlanceBinaryTreeNode getRoot() {
        return root;
    }

    public void setRoot(BlanceBinaryTreeNode root) {
        this.root = root;
    }

    private BlanceBinaryTreeNode root;

    public void insert(int value) {
        BlanceBinaryTreeNode node = new BlanceBinaryTreeNode(value);
        insert(node);
    }

    public void insert(BlanceBinaryTreeNode node) {
        if (root == null) {
            root = node;
        } else {
            insert(root, node);
        }
    }

    private BlanceBinaryTreeNode insert(BlanceBinaryTreeNode parent, BlanceBinaryTreeNode node) {
        if (parent == null || parent.compare(node) == 0) {
            return node;
        } else if (parent.compare(node) > 0) {
            parent.setLeft(insert(parent.getLeft(), node));
        } else {
            parent.setRight(insert(parent.getRight(), node));
        }
        return reblance(parent);
    }

    private BlanceBinaryTreeNode reblance(BlanceBinaryTreeNode node) {
        int bf = node.getBf();
        if (bf < -1) {
            BlanceBinaryTreeNode leftNode = node.getLeft();
            if (leftNode.getBf() == 1) { // 左旋
                node.setLeft(rotateLeft(leftNode));
            }
            // 右旋
            return rotateRight(node);
        } else if (bf > 1){
            BlanceBinaryTreeNode rightNode = node.getRight();
            if (rightNode.getBf() == -1) { // 右旋
                node.setRight(rotateRight(rightNode));
            }
            // 左旋
            return rotateLeft(node);
        }
        return node;
    }

    /**
     * 右旋
     * @param node
     * @return 替换旋转结点的结点
     */
    public BlanceBinaryTreeNode rotateRight(BlanceBinaryTreeNode node) {
        if (node != null) {
            BlanceBinaryTreeNode leftNode = node.getLeft();
            BlanceBinaryTreeNode leftRightNode = leftNode.getRight();
            node.setLeft(leftRightNode);
            leftNode.setRight(node);
            if (node == root) {
                root = leftNode;
            }
            return leftNode;
        }
        return null;
    }

    /**
     * 左旋
     * @param node
     * @return 替换旋转结点的结点
     */
    public BlanceBinaryTreeNode rotateLeft(BlanceBinaryTreeNode node) {
        if (node != null) {
            BlanceBinaryTreeNode rightNode = node.getRight();
            BlanceBinaryTreeNode rightLeftNode = rightNode.getLeft();
            node.setRight(rightLeftNode);
            rightNode.setLeft(node);
            if (node == root) {
                root = rightNode;
            }
            return rightNode;
        }
        return null;
    }

    /****************** 递归方式遍历节点 ************************/
    public void display() {
        System.out.print("pre:");
        TraverseBinaryTreeUtil.preOrder(this.getRoot());
        System.out.print("\nin:");
        TraverseBinaryTreeUtil.inOrder(this.getRoot());
        System.out.print("\npost:");
        TraverseBinaryTreeUtil.postOrder(this.getRoot());
    }
    /****************** 递归方式遍历节点 ************************/

    /****************** 非递归方式遍历节点 ************************/
    public void displayWithStack() {
        System.out.print("\npreOrderWithStack:");
        TraverseBinaryTreeUtil.preOrderWithStack(this.getRoot());

        System.out.print("\ninOrderWithStack:");
        TraverseBinaryTreeUtil.inOrderWithStack(this.getRoot());

        System.out.print("\npostOrderWithStack:");
        TraverseBinaryTreeUtil.postOrderWithStack(this.getRoot());
    }
    /****************** 非递归方式遍历节点 ************************/

    public static void main(String[] args) {
        BlanceBinarySearchTree tree = new BlanceBinarySearchTree();
        tree.insert(9);
        tree.insert(5);
        tree.insert(4);
        tree.insert(8);
        tree.insert(12);
        tree.insert(15);
        tree.insert(11);

        tree.display();

    }
}

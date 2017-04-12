package io.vicp.frlib.algorithm.tree;

/**
 * Created by zhoudr on 2017/4/11.
 */
public class BlanceBinarySearchTree extends Node{

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
            reblance(parent);
            return parent;
        } else {
            parent.setRight(insert(parent.getRight(), node));
            reblance(parent);
            return parent;
        }
    }

    private void reblance(BlanceBinaryTreeNode node) {
        /*int bf = node.getBf();
        if (bf < -1) {
            if (unBlanceNode.getLeft().getRight() == node
                    || unBlanceNode.getLeft().getRight() == node.getParent()) {
                // 左儿子的右子树添加节点(先左旋后右旋)
                rotateLeft(unBlanceNode.getLeft());
                rotateRight(unBlanceNode);
            } else if (unBlanceNode.getLeft().getLeft() == node
                    || unBlanceNode.getLeft().getLeft() == node.getParent()) {
                // 左儿子的左子树添加节点(右旋)
                rotateRight(unBlanceNode);
            }
        } else if (bf > 1) {
            if (unBlanceNode.getRight().getLeft() == node
                    || unBlanceNode.getRight().getLeft() == node.getParent()) {
                // 右儿子的左子树添加节点(先右旋后左旋)
                rotateRight(unBlanceNode.getRight());
                rotateLeft(unBlanceNode);
            }else if (unBlanceNode.getRight().getRight() == node
                    || unBlanceNode.getRight().getRight() == node.getParent()) {
                // 右儿子的右子树添加节点(左旋)
                rotateLeft(unBlanceNode);
            }
        }*/
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
            return rightNode;
        }
        return null;
    }
}

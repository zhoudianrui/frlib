package io.vicp.frlib.algorithm.tree;

import java.util.Stack;

/**
 * Created by zhoudr on 2017/4/10.
 */
public class BinarySearchTree {

    public BinaryTreeNode getRoot() {
        return root;
    }

    public void setRoot(BinaryTreeNode node) {
        root = node;
    }

    private BinaryTreeNode root;

    public BinarySearchTree() {

    }

    public BinarySearchTree(BinaryTreeNode binaryTreeNode) {
        insert(binaryTreeNode);
    }

    public void insert(int value, boolean userRecursion) {
        BinaryTreeNode node = new BinaryTreeNode(value);
        insert(node, userRecursion);
    }

    /**
     * 插入结点
     * @param node
     */
    private void insert(BinaryTreeNode node) {
        // 采用循环方式
        if (node == null) {
            return;
        }
        if (root == null) {
            root = node;
            return;
        }
        BinaryTreeNode pre = null;
        BinaryTreeNode cur = root;
        int compareResult;
        do {
            pre = cur;
            compareResult = pre.compare(node);
            if (compareResult > 0) {
                cur = cur.getLeft();
            } else if (compareResult < 0) {
                cur = cur.getRight();
            } else {
                cur.setValue(node.getValue());
                break;
            }
        } while (cur != null);
        if (compareResult > 0) {
            pre.setLeft(node);
        } else if (compareResult < 0) {
            pre.setRight(node);
        }
    }

    public void insert(BinaryTreeNode node, boolean useRecursion) {
        if (useRecursion) {
            if (root == null) {
                root = node;
            } else {
                insert(root, node);
            }
        } else {
            insert(node);
        }
    }

    private BinaryTreeNode insert(BinaryTreeNode parent, BinaryTreeNode node) {
        if (parent == null || parent.compare(node) == 0) {
            return node;
        } else if (parent.compare(node) < 0) {
            parent.setRight(insert(parent.getRight(), node));
            return parent;
        } else {
            parent.setLeft(insert(parent.getLeft(), node));
            return parent;
        }
    }

    /**
     * 删除指定结点(使用右子树替换删除节点)
     * @param node
     */
    public void remove(BinaryTreeNode node) {
        if (node == null) {
            return;
        }
        BinaryTreeNode findedNode = findNode(node);
        BinaryTreeNode parentNode = parentOf(findedNode);
        BinaryTreeNode targetNode = null;
        if (findedNode != null) {
            BinaryTreeNode leftNode = findedNode.getLeft();
            BinaryTreeNode rightNode = findedNode.getRight();
            if (rightNode == null) { // 使用左子树替换
                if (leftNode == null) {
                    if (parentNode == null) {
                        root = null;
                    } else if (parentNode.getLeft() == findedNode) {
                        parentNode.setLeft(null);
                    } else {
                        parentNode.setRight(null);
                    }
                } else {
                    // 最右结点
                    BinaryTreeNode leftRightNode = leftNode.getRight();
                    while (leftNode.getRight() != null) {
                        leftRightNode = leftNode.getRight();
                    }
                    if (leftRightNode == null) {
                        targetNode = leftNode;
                    } else {
                        targetNode = leftRightNode;
                    }

                    if (targetNode != findedNode.getLeft()) {
                        targetNode.setLeft(findedNode.getLeft());
                        parentOf(targetNode).setRight(targetNode.getLeft());
                    }

                    if (parentNode == null) {
                        root = targetNode;
                    } else if (parentNode.getLeft() == findedNode) {
                        parentNode.setLeft(targetNode);
                    } else {
                        parentNode.setRight(targetNode);
                    }
                }
            } else { // 使用右子树替换
                // 使用目标节点的右子树最左节点替换
                // 如果右子树没有左结点，直接使用目标结点的右儿子替换
                BinaryTreeNode rightLeftNode = rightNode;
                while (rightLeftNode.getLeft() != null) {
                    rightLeftNode = rightLeftNode.getLeft();
                }
                if (rightLeftNode != null) {
                    targetNode = rightLeftNode;
                } else {
                    targetNode = rightNode;
                }
                if (targetNode != findedNode.getRight()) {
                    parentOf(targetNode).setLeft(targetNode.getRight());
                    if (leftNode != null) {
                        // 查找待删除结点的左子树的左右结点
                        BinaryTreeNode leftTargetNode = leftNode;
                        while(leftTargetNode.getRight() != null) {
                            leftTargetNode = leftTargetNode.getRight();
                        }
                        leftTargetNode.setRight(targetNode.getLeft());
                        targetNode.setLeft(leftNode);
                        targetNode.setRight(rightNode);
                    }
                } else {
                    targetNode.setLeft(findedNode.getLeft());
                }
                if (parentNode == null) {
                    root = targetNode;
                } else if (parentNode.getLeft() == findedNode) {
                    parentNode.setLeft(targetNode);
                } else {
                    parentNode.setRight(targetNode);
                }
            }
        }
    }

    private BinaryTreeNode parentOf(BinaryTreeNode node) {
        if (root == node) {
            return null;
        }
        BinaryTreeNode result = null;
        BinaryTreeNode current = root;
        while (current != null && current.getLeft() != node && current.getRight() != node) {
            int compareResult = current.compare(node);
            if (compareResult > 0) {
                current = current.getLeft();
            } else if (compareResult < 0) {
                current = current.getRight();
            } else {
                break;
            }
        }
        if (current != null) {
            result = current;
        }
        return result;
    }

    /**
     * 查询指定结点
     * @param node
     * @return
     */
    public BinaryTreeNode findNode(BinaryTreeNode node) {
        BinaryTreeNode result = null;
        BinaryTreeNode current = root;
        while (current != null) {
            int compareResult = current.compare(node);
            if (compareResult > 0) {
                current = current.getLeft();
            } else if (compareResult < 0) {
                current = current.getRight();
            } else {
                result = current;
                break;
            }
        }
        return result;
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
        BinarySearchTree tree = new BinarySearchTree();
        boolean useRecursion = true;
        tree.insert(9, useRecursion);
        tree.insert(5, useRecursion);
        tree.insert(4, useRecursion);
        tree.insert(8, useRecursion);
        tree.insert(12, useRecursion);
        tree.insert(15, useRecursion);
        tree.insert(11, useRecursion);

        tree.display();
        tree.displayWithStack();

        System.out.println("\nremove element:");
        tree.remove(new BinaryTreeNode(9));
        tree.display();
    }
}

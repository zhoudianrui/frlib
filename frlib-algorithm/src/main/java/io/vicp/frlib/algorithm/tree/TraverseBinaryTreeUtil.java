package io.vicp.frlib.algorithm.tree;

import java.util.Stack;

/**
 * Created by zhoudr on 2017/4/13.
 */
public class TraverseBinaryTreeUtil {
    /****************** 递归方式遍历节点 ************************/
    public static void preOrder(BinaryTreeNode node) {
        if (node != null) {
            System.out.print(node.getValue() + " ");
        } else {
            return;
        }
        preOrder(node.getLeft());
        preOrder(node.getRight());
    }

    public static void inOrder(BinaryTreeNode node) {
        if (node != null) {
            inOrder(node.getLeft());
            System.out.print(node.getValue() + " ");
            inOrder(node.getRight());
        }
        return;
    }

    public static void postOrder(BinaryTreeNode node) {
        if (node != null) {
            postOrder(node.getLeft());
            postOrder(node.getRight());
            System.out.print(node.getValue() + " ");
        }
        return;
    }
    /****************** 递归方式遍历节点 ************************/

    /****************** 非递归方式遍历节点 ************************/
    public static void preOrderWithStack(BinaryTreeNode node) {
        Stack<BinaryTreeNode> stack = new Stack<>();

        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                stack.push(node);
                System.out.print(node.getValue() + " ");
                node = node.getLeft();
            }
            if (!stack.isEmpty()) {
                node = stack.pop();
                node = node.getRight();
            }
        }
    }

    public static void  inOrderWithStack(BinaryTreeNode node) {
        Stack<BinaryTreeNode> stack = new Stack<>();

        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                stack.push(node);
                node = node.getLeft();
            }
            if (!stack.isEmpty()) {
                node = stack.pop();
                System.out.print(node.getValue() + " ");
                node = node.getRight();
            }
        }
    }

    public static void postOrderWithStack(BinaryTreeNode node) {
        Stack<BinaryTreeNode> stack = new Stack<>();

        // 对于任一结点P，将其入栈，然后沿其左子树一直往下搜索，直到搜索到没有左孩子的结点，
        // 此时该结点出现在栈顶，但是此时不能将其出栈并访问，因此其右孩子还为被访问。
        // 所以接下来按照相同的规则对其右子树进行相同的处理，当访问完其右孩子时，该结点又出现在栈顶，此时可以将其出栈并访问。
        // 这样就保证了正确的访问顺序。可以看出，在这个过程中，每个结点都两次出现在栈顶，只有在第二次出现在栈顶时，才能访问它。
        // 因此需要多设置一个变量标识该结点是否是第一次出现在栈顶。

        /*while(node != null || !stack.isEmpty()) {
            while (node != null) {
                stack.push(node);
                node.setFirst(true);
                node = node.getLeft();
            }
            if (!stack.isEmpty()) {
                BinaryTreeNode temp = stack.pop();
                if (temp.isFirst()) {
                    temp.setFirst(false);
                    stack.push(temp);
                    node = temp.getRight();
                } else {
                    System.out.print(temp.getValue() + " ");
                }
            }
        }*/

        // 要保证根结点在左孩子和右孩子访问之后才能访问，因此对于任一结点P，先将其入栈。
        // 如果P不存在左孩子和右孩子，则可以直接访问它；
        // 或者P存在左孩子或者右孩子，但是其左孩子和右孩子都已被访问过了，则同样可以直接访问该结点。
        // 若非上述两种情况，则将P的右孩子和左孩子依次入栈，
        // 这样就保证了每次取栈顶元素的时候，左孩子在右孩子前面被访问，左孩子和右孩子都在根结点前面被访问。
        BinaryTreeNode preNode = null;
        BinaryTreeNode currentNode = null;
        stack.push(node);
        while (!stack.isEmpty()) {
            currentNode = stack.pop();
            if ((currentNode != null && currentNode.getLeft() == null && currentNode.getRight() == null)
                    || (preNode != null && (preNode == currentNode.getLeft() || preNode == currentNode.getRight()))) {
                System.out.print(currentNode.getValue() + " ");
                preNode = currentNode;
            } else {
                stack.push(currentNode);
                if (currentNode.getRight() != null) {
                    stack.push(currentNode.getRight());
                }
                if (currentNode.getLeft() != null) {
                    stack.push(currentNode.getLeft());
                }
            }
        }
    }
    /****************** 非递归方式遍历节点 ************************/
}

package io.vicp.frlib.algorithm.tree;

import lombok.Data;

/**
 * Created by zhoudr on 2017/4/11.
 */
@Data
public class BlanceBinaryTreeNode extends Node {
    private BlanceBinaryTreeNode left;

    private BlanceBinaryTreeNode right;

    // 平衡因子=height(right)-height(left)
    private int bf;

    private int height;

    public BlanceBinaryTreeNode(int value) {
        setValue(value);
    }

    public int getBf() {
        int rightHeight = 0;
        int leftHeight = 0;
        if (right != null) {
            rightHeight = right.getHeight();
        }
        if (left != null) {
            leftHeight = left.getHeight();
        }
        return rightHeight - leftHeight;
    }

    public int getHeight() {
        int leftHeight = left == null ? 0 : left.getHeight();
        int rightHeight = right == null ? 0 : right.getHeight();
        return (leftHeight > rightHeight ? leftHeight : rightHeight) + 1;
    }

}

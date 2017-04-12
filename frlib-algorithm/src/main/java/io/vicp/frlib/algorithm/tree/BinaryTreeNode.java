package io.vicp.frlib.algorithm.tree;

import lombok.Data;

/**
 * Created by zhoudr on 2017/4/10.
 */
@Data
public class BinaryTreeNode extends Node{
    private BinaryTreeNode left;

    private BinaryTreeNode right;

    private boolean isFirst;

    public BinaryTreeNode(int value) {
        setValue(value);
    }
}

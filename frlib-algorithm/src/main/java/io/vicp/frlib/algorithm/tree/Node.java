package io.vicp.frlib.algorithm.tree;

import lombok.Data;

/**
 * 用于单向链表
 * Created by zhoudr on 2017/4/10.
 */
@Data
public class Node {
    private Integer value;

    private Node next;

    public int compare(Node node) {
        if (node == null) {
            return 1;
        }
        if (node.value > value) {
            return -1;
        } else if (node.value < value) {
            return 1;
        }
        return 0;
    }

    public String toString() {
        return String.valueOf(value);
    }
}

package io.vicp.frlib.algorithm.leetcode;

/**
 * Created by zhoudr on 2017/3/24.
 */
public class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
    }

    public void display() {
        ListNode node = this;
        while (node != null) {
            System.out.println(node.val);
            node = node.next;
        }
    }
}

package io.vicp.frlib.algorithm.leetcode;

/**
 * use for add BigNumber
 * You are given two non-empty linked lists representing two non-negative integers.
 * The digits are stored in reverse order and each of their nodes contain a single digit. Add the two numbers and return it as a linked list.
 * You may assume the two numbers do not contain any leading zero, except the number 0 itself.
 *
 * Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
 * Output: 7 -> 0 -> 8
 * Created by zhoudr on 2017/3/24.
 */
public class AddTwoNumbers {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode tail = null;
        ListNode head = null;
        ListNode pre = null;
        ListNode[] headTail = new ListNode[]{head, tail, pre};
        while(l1 != null && l2 != null) {
            int tempVal = l1.val + l2.val;
            headTail = put(headTail, tempVal);
            l1 = l1.next;
            l2 = l2.next;
        }
        while(l1 != null) {
            headTail = put(headTail, l1.val);
            l1 = l1.next;
        }
        while(l2 != null) {
            headTail = put(headTail, l2.val);
            l2 = l2.next;
        }
        // 删除最后一个元素
        ListNode tempNode = headTail[0];
        while(tempNode != null && tempNode.next != headTail[1]) {
            // 不做处理
            tempNode = tempNode.next;
        }
        if (tempNode.next.val == 0) {
            tempNode.next = null;
        }
        return headTail[0];
    }

    private ListNode[] put(ListNode[] headTail, int value) {
        ListNode node = new ListNode(0);
        ListNode head = headTail[0];
        ListNode tail = headTail[1];
        if (tail == null) {
            tail = node;
        }
        tail.val = tail.val + value;
        if (tail.val >= 10) {
            tail.val = tail.val - 10;
            tail.next = new ListNode(1);
        } else {
            tail.next = new ListNode(0);
        }
        tail = tail.next;
        if (head == null) {
            head = node;
            head.next = tail;
        }
        headTail[0] = head;
        headTail[1] = tail;
        return headTail;
    }

    public static void main(String[] args) {
        AddTwoNumbers instance = new AddTwoNumbers();
        ListNode l1 = instance.buildList("2436");
        ListNode l2 = instance.buildList("564");
        ListNode head = instance.addTwoNumbers(l1, l2);
        head.display();
    }

    private ListNode buildList(String data) {
        ListNode first = null;
        if (data != null && data.trim().length() > 0) {
            int index = 0;
            ListNode l1 = new ListNode(Integer.valueOf(data.substring(index++, 1)));
            first = l1;
            while(index < data.length()) {
                int val = Integer.valueOf(data.substring(index, index + 1));
                ListNode next = new ListNode(val);
                l1.next = next;
                l1 = next;
                index++;
            }
        }
        return first;
    }
}

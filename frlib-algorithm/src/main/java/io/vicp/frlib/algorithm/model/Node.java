package io.vicp.frlib.algorithm.model;

/**
 * Created by zhoudr on 2017/3/23.
 */
public class Node {
    public int getData() {
        return data;
    }

    private int data; // 数据域

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    private Node next;
    //private Node pre;

    public Node(int data) {
        this.data = data;
    }

    public String toString() {
        return "Node:" + data;
    }
}

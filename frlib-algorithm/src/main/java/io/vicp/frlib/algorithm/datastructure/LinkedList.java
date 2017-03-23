package io.vicp.frlib.algorithm.datastructure;

import io.vicp.frlib.algorithm.model.Node;

/**
 * Created by zhoudr on 2017/3/23.
 */
public class LinkedList {
    public Node getHead() {
        return head;
    }

    public void setHead(Node head) {
        this.head = head;
    }

    public Node getTail() {
        return tail;
    }

    public void setTail(Node tail) {
        this.tail = tail;
    }

    private Node head; // 头结点
    private Node tail; // 尾结点

    public void put(int data) {
        Node node = new Node(data);
        if (head == null) {
            head = node;
            tail = node;
        } else {
            tail.setNext(node);
            tail = node;
        }
    }

    /**
     * 删除指定元素(从头开始处理)
     * @param data
     * @param removeAll 是否全部删除
     */
    public void remove(int data, boolean removeAll) {
        Node node = head;
        Node pre = null;
        while (node != null) {
            if (node.getData() == data) {
                if (node == head && head == tail) {
                    head = tail = null;
                } else if(node == head){
                    head = node.getNext();
                } else {
                    pre.setNext(node.getNext());
                }
                if (!removeAll) {
                    break;
                }
            }
            pre = node;
            node = node.getNext();
        }
    }

    public boolean contains(int data) {
        Node node = head;
        while (node != null) {
            if (node.getData() == data) {
                return true;
            }
            node = node.getNext();
        }
        return false;
    }

    public void display() {
        Node node = head;
        while(node != null) {
            if (node == tail) {
                System.out.print(node);
            } else {
                System.out.print(node + "-->");
            }
            node = node.getNext();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        LinkedList linkedList = new LinkedList();
        linkedList.put(1);
        linkedList.put(1);
        linkedList.put(2);
        linkedList.put(1);
        linkedList.display();
        linkedList.remove(5, true);
        linkedList.display();
    }
}

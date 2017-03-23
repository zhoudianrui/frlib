package io.vicp.frlib.algorithm.datastructure;

import io.vicp.frlib.algorithm.model.Node;

/**
 * 基本队列的实现
 * Created by zhoudr on 2017/3/23.
 */
public class Queue {

    private LinkedList linkedList = new LinkedList();

    public void put(int data) {
        linkedList.put(data);
    }

    public Node take() {
        Node node = linkedList.getHead();
        linkedList.remove(node.getData(), false);
        return node;
    }

    public int getSize() {
        int size = 0;
        Node node = linkedList.getHead();
        while (node != null) {
            size++;
            node = node.getNext();
        }
        return size;
    }

    public boolean isEmpty() {
        return getSize() == 0 ? true : false;
    }

    public void display() {
        linkedList.display();
    }

    public static void main(String[] args) {
        Queue queue = new Queue();
        queue.put(1);
        queue.put(2);
        queue.put(3);
        queue.put(4);
        queue.display();
        Node node = queue.take();
        System.out.println(node);
        while (!queue.isEmpty()) {
            queue.display();
            node = queue.take();
            System.out.println(node);
        }
    }
}

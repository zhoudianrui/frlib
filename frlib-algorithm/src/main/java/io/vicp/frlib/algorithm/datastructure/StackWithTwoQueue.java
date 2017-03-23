package io.vicp.frlib.algorithm.datastructure;

import io.vicp.frlib.algorithm.model.Node;

/**
 * 使用两个队列实现栈操作
 * Created by zhoudr on 2017/3/23.
 */
public class StackWithTwoQueue {

    private Queue firstQueue = new Queue();

    private Queue secondQueue = new Queue();

    private boolean headFirst = false;

    public void push(int data){
        if (firstQueue.isEmpty() && secondQueue.isEmpty()) {
            firstQueue.put(data);
            headFirst = true;
        } else if (firstQueue.isEmpty()) {
            secondQueue.put(data);
            headFirst = false;
        } else if (secondQueue.isEmpty()) {
            firstQueue.put(data);
            headFirst = true;
        } else {
            System.out.println("error");
        }
    }

    public Node pop() {
        if (headFirst && !firstQueue.isEmpty()) { // 在第一个队列
            Node node = firstQueue.take();
            do {
                if (node.getNext() != null) {
                    secondQueue.put(node.getData());
                    node = firstQueue.take();
                } else {
                    headFirst = false;
                    return node;
                }
            } while (node != null);
        }
        if (!headFirst && !secondQueue.isEmpty()){
            Node node = secondQueue.take();
            do {
                if (node.getNext() != null) {
                    firstQueue.put(node.getData());
                    node = secondQueue.take();
                } else {
                    headFirst = true;
                    return node;
                }
            } while (node != null);
        }
        return null;
    }

    public boolean isEmpty() {
        return firstQueue.isEmpty() && secondQueue.isEmpty();
    }

    public static void main(String[] args) {
        StackWithTwoQueue stack = new StackWithTwoQueue();
        stack.push(1);
        stack.push(2);
        System.out.println(stack.pop());
        stack.push(3);
        System.out.println(stack.pop());
        stack.push(4);
        stack.push(5);
        stack.push(6);
        while (!stack.isEmpty()) {
            System.out.println(stack.pop());
        }
    }
}

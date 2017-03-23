package io.vicp.frlib.algorithm.datastructure;

import io.vicp.frlib.algorithm.model.Node;

/**
 * 栈的实现
 * Created by zhoudr on 2017/3/23.
 */
public class Stack {

    private Array array = new Array();

    public void push(int data) {
        array.add(data);
    }

    public Node pop() {
        int size = array.getSize();
        if (!isEmpty()) {
            Node node = array.get(array.getSize() - 1);
            array.remove(size - 1);
            return node;
        }
        return null;
    }

    public boolean isEmpty() {
        return array.getSize() == 0;
    }


    public static void main(String[] args) {
        Stack stack = new Stack();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
    }
}

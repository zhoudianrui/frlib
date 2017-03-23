package io.vicp.frlib.algorithm.datastructure;

import io.vicp.frlib.algorithm.model.Node;

import java.util.Arrays;

/**
 * Created by zhoudr on 2017/3/23.
 */
public class Array {
    private static final int DEFAULT_SIZE = 16;
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
    Node[] elements;
    private int size;

    public int getSize() {
        return size;
    }

    public Array() {
        this(DEFAULT_SIZE);
    }

    public Array(int initialSize) {
        elements = new Node[initialSize];
    }

    public void add(int data) {
        Node node = new Node(data);
        if (isFull()) {
            expand();
        }
        elements[size ++] = node;
    }

    public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == size - 1) {
            elements[index] = null;
        } else {
            // 前移数据
            for (int i = index; i < size; i++) {
                elements[i] = elements[i + 1];
            }
        }
        size--;
    }

    public Node get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return elements[index];
    }

    public boolean contains(int data) {
        if (isEmpty()) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (elements[i].getData() == data) {
                return true;
            }
        }
        return  false;
    }

    private boolean isEmpty() {
        return elements[0] == null;
    }

    private boolean isFull() {
        return elements[elements.length - 1] != null;
    }

    private void expand() {
        int currentSize = elements.length;
        int newSize = currentSize + currentSize >> 1;
        if (newSize < 0) {
            throw new OutOfMemoryError();
        } else if(newSize > MAX_ARRAY_SIZE){
            newSize = Integer.MAX_VALUE;
        }
        size = currentSize;
        elements = Arrays.copyOf(elements, newSize);
    }

    public void display() {
        if (!isEmpty()) {
            for (int i = 0; i < size; i++) {
                if (i == size - 1) {
                    System.out.print(elements[i]);
                } else {
                    System.out.print(elements[i] + "-->");
                }
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Array array = new Array();
        array.add(1);
        array.add(2);
        array.add(3);
        array.display();
        array.remove(4);
        array.display();
        array.remove(2);
        array.display();
    }
}

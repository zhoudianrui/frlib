package io.vicp.frlib.gc.createinstance;

/**
 * Created by zhoudr on 2017/3/27.
 */
public class Child extends Father {

    private int value = 5;

    public Child() {
        System.out.println("Child");
    }

    public void change(Child child) {
        child.value = 10;
    }

    public static void main(String[] args) {
        Child child = new Child();
        child.change(child);
        System.out.println(child.value);
    }
}

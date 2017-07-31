package io.vicp.frlib.client.thread.aboutvolatile;

/**
 * Created by zhoudr on 2017/6/16.
 */
public class NonSecurityAccounting implements Runnable {
    static NonSecurityAccounting instance = new NonSecurityAccounting();

    static volatile int i = 0;

    public static void increase() {
        i++;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100000000; i++) {
            increase();
        }
        System.out.println(Thread.currentThread().getName() + " run over");
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(instance, "t1");
        Thread t2 = new Thread(instance, "t2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(i);
    }
}

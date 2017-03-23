package io.vicp.frlib.thread.aboutvolatile;

/**
 * 测试volatile的重排序
 * Created by zhoudr on 2017/3/22.
 */
public class ReSort {
    int i = 0;
    private volatile static boolean ready;

    public void write() {
        i = 2;
        // storestore
        ready = true;
        // storeload
    }

    public void read() {
        if(ready) {
            // loadload
            // loadstore
            System.out.println("i=" + i);
        }
    }

    static class Reader extends Thread {
        @Override
        public void run() {
            long tryTimes = 0L;
            while (!ready) {
                ++tryTimes;
            }
            System.out.println("ready! try times : " + tryTimes);
        }
    }

    static class Writer extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 100000; i++) {
                ready = true;
            }
        }
    }

    public static void main(String[] args) throws Exception{
        new Reader().start();

        Thread.sleep(100L);

        new Writer().start();
    }
}

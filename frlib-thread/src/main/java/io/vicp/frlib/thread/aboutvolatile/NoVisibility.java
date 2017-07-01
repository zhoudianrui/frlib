package io.vicp.frlib.thread.aboutvolatile;

/**
 * Created by zhoudr on 2017/6/16.
 */
public class NoVisibility {
    private static volatile boolean ready;
    private static int number = 0;

    private static class ReaderThread extends Thread {
        @Override
        public void run() {
            while(!ready);
            System.out.println(number);
        }
    }

    public static void main(String[] args) {
        new ReaderThread().start();
        try {
            Thread.sleep(1000);
            ready = true;
            number = 42;
            //Thread.sleep(10000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

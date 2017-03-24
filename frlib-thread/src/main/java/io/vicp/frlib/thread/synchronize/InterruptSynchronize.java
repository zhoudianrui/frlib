package io.vicp.frlib.thread.synchronize;

/**
 * because java adopt non-interrupted way to
 * interrupt thread, so synchronize method always
 * hold object monitor
 *
 * Created by zhoudr on 2017/3/23.
 */
public class InterruptSynchronize {

    public static void main(String[] args) throws InterruptedException {
        SharedObject object = new SharedObject();
        Thread thread1 = new Thread(new TestThread(object), "thread1");
        Thread thread2 = new Thread(new TestThread(object), "thread2");
        thread1.start();
        thread2.start();
        Thread.sleep(1000);
        thread1.interrupt();
    }

}

class SharedObject{
    public synchronized void m() {
        System.out.println(Thread.currentThread().getName() + " enter m()");
        long current = System.currentTimeMillis();
        while (!Thread.currentThread().isInterrupted()) {
            if ((System.currentTimeMillis() - current) / 1000 == 10) {
                System.out.println(Thread.currentThread().getName() + " execute m()");
            }
        }
    }
}

class TestThread implements Runnable {
    private SharedObject object;
    public TestThread(SharedObject object) {
        this.object = object;
    }
    @Override
    public void run() {
        object.m();
    }
}

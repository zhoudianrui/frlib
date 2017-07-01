import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by zhoudr on 2017/6/21.
 */
public class LockTest implements Runnable {
    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    private volatile boolean awaitFlag = false;

    public static void main(String[] args) {
        final LockTest lockTest = new LockTest();
        Thread t1 = new Thread(lockTest, "awaitThread");
        lockTest.testSingal(t1);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // t1.interrupt();
        //System.out.println(1 >>> 16);
    }

    private void testSingal(Thread thread) {
        awaitFlag = true;
        thread.start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            Thread.currentThread().setName("signalThread");
            try {
                this.lock.lock();// 注意这里的this指的是产生lambda表达式的方法的调用对象
                System.out.println(Thread.currentThread().getName() + " get lock");
                this.condition.signal();
            } finally {
                this.lock.unlock();
                System.out.println(Thread.currentThread().getName() + " release lock");
            }
        }).start();
    }

    public void run() {
        final String threadName = Thread.currentThread().getName();
        try {
            lock.lock();
            System.out.println(threadName + " get lock");
            if (awaitFlag) {
                System.out.println(threadName + " release lock because await");
                condition.await();
                System.out.println(threadName + " get lock again");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            System.out.println(threadName + " release lock");
        }
    }
}

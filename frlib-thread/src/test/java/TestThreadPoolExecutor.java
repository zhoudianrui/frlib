import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhoudr on 2017/4/24.
 */
public class TestThreadPoolExecutor {
    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
        Thread thread = new Thread(new MyThread(), "myThread");
        threadPoolExecutor.execute(thread);
        Thread thread1 = new Thread(new MyThread(), "myThread1");
        threadPoolExecutor.execute(thread1);
        Thread thread2 = new Thread(new MyThread(), "myThread2");
        threadPoolExecutor.execute(thread2);
        /*while (threadPoolExecutor.getActiveCount() > 0 ) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        threadPoolExecutor.shutdownNow();*/
    }

    static class MyThread implements Runnable {
        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(1);
                System.out.println(Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

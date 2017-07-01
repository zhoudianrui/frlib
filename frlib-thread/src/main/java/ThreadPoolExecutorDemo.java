import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * description :
 * user : zhoudr
 * time : 2017/6/30 11:29
 */

public class ThreadPoolExecutorDemo {
    public static void main(String[] args) {
        //testFixedThreadPool();
        testCachedThreadPool();
    }

    private static void testFixedThreadPool() {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 10; i++) {
            executor.execute(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + "  " + new Date());
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private static void testCachedThreadPool() {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            executor.execute(() -> {
                try {
                    System.out.format("%15s  %28s%n", Thread.currentThread().getName(), new Date());
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        for (;;) {
            if (executor.getActiveCount() <= 0) {
                System.out.println("run over");
                executor.shutdown();
                break;
            } else {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("active:" + executor.getActiveCount() + ",task:" + executor.getTaskCount());
            }
        }
    }
}

package io.vicp.frlib.util;

import java.util.concurrent.*;

/**
 * should add shutdown hook
 *
 * use ThreadPoolExecutor to execute task.
 * when it need to execute a large amount of tasks,
 * we should take account of OOM, so we need to rewrite
 * RejectedExecutionHandler.rejectedExecution method to adjust queue.offer() to
 * queue.put() in order to block task to insert and not lost task.
 *
 * Created by zhoudr on 2017/4/25.
 */
public class ThreadUtil {

    // 适用于大量任务的线程池
    private static final ThreadPoolExecutor massThreadPoolExecutor = new ThreadPoolExecutor(
                                                            10,
                                                            20,
                                                            60,
                                                            TimeUnit.SECONDS,
                                                            new ArrayBlockingQueue<Runnable>(10),
                                                            new BlockedEnQueueForRejectedExecutionHandler());

    private static final ThreadPoolExecutor commonThreadPoolExecutor = new ThreadPoolExecutor(
                                                            10,
                                                            20,
                                                            60,
                                                            TimeUnit.SECONDS,
                                                            new LinkedBlockingDeque<Runnable>());

    private static final ThreadPoolExecutor timedThreadPoolExecutor = new ThreadPoolExecutor(
                                                            0,
                                                            Integer.MAX_VALUE,
                                                            60L,
                                                            TimeUnit.SECONDS,
                                                            new SynchronousQueue<Runnable>());

    public static void execute(boolean massTasks, Runnable r) {
        if (massTasks) {
            massThreadPoolExecutor.execute(r);
        } else {
            commonThreadPoolExecutor.execute(r);
        }
    }

    public static void executeTimed(Runnable r) {
        timedThreadPoolExecutor.execute(r);
    }

    public static void stop() {
        new Thread() {
            @Override
            public void run() {
                stopExecutor(massThreadPoolExecutor);
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                stopExecutor(commonThreadPoolExecutor);
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                stopExecutor(timedThreadPoolExecutor);
            }
        }.start();
    }

    private static void stopExecutor(ThreadPoolExecutor executor) {
        if (!(executor.isShutdown() || executor.isTerminated())) {
            executor.shutdown();
            while (executor.getActiveCount() > 0) {
                try {
                    System.out.println(executor);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void addedToShutdownHook() {
        Thread shutdownHookThread = new Thread() {
            @Override
            public void run() {
                ThreadUtil.execute(false, new Thread() {
                    public void run() {
                        System.out.println("common thread");
                    }
                });
                ThreadUtil.stop();
            }
        };
        Runtime.getRuntime().addShutdownHook(shutdownHookThread);
    }


}
class BlockedEnQueueForRejectedExecutionHandler implements RejectedExecutionHandler {
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        try {
            executor.getQueue().put(r);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

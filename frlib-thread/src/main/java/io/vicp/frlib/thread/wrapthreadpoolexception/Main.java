package io.vicp.frlib.thread.wrapthreadpoolexception;

import java.util.concurrent.*;

/**
 * description : 防止线程池的异常被丢弃
 * user : zhoudr
 * time : 2017/7/5 11:29
 */

public class Main {
    public static void main(String[] args) {
        //ThreadPoolExecutor executor = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 0, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
        ThreadPoolExecutor executor = new TraceThreadPoolExecutor(0, Integer.MAX_VALUE, 0, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
        for (int i = 0; i < 5; i++) {
            Thread t = new DivideThread(100, i);
            // 方式一
            executor.execute(t); // 打印错误信息，但是不知道任务的提交与执行

            // 方式二
            //executor.submit(t); // 不会打印错误信息

            // 方式三
            /*Future future = executor.submit(t);
            try {
                future.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }*/

            // 方式四
            executor.execute(t);
        }
        executor.shutdown();
    }
}

class DivideThread extends Thread {
    private int dividedNumber;
    private int number;

    DivideThread(int dividedNumber, int number) {
        this.dividedNumber = dividedNumber;
        this.number = number;
    }

    @Override
    public void run() {
        double result = dividedNumber / number;
        System.out.println(result);
    }
}

class TraceThreadPoolExecutor extends ThreadPoolExecutor {
    TraceThreadPoolExecutor(int core, int max, long keepAlive, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(core, max, keepAlive, unit, workQueue);
    }

    @Override
    public void execute(Runnable command) {
        super.execute(wrap(command, getException(), Thread.currentThread().getName()));
    }

    private Exception getException() {
        return new Exception("Client stack trace");
    }
    @Override
    public Future<?> submit(Runnable task) {
        return super.submit(wrap(task, getException(), Thread.currentThread().getName()));
    }

    public Runnable wrap(Runnable command, Exception statck, String threaName) {
        return () -> {
            try {
                command.run();
            } catch (Exception e) {
                statck.printStackTrace();
                throw e;
            }
        };
    }
}

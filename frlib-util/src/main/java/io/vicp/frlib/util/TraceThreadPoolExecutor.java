package io.vicp.frlib.util;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * description : 方便查看递归调用栈的线程池
 * user : zhoudr
 * time : 2017/7/5 15:03
 */

public class TraceThreadPoolExecutor extends ThreadPoolExecutor {
    public TraceThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    public void execute(Runnable command) {
        super.execute(wrap(command, getException()));
    }

    private Runnable wrap(Runnable task, Exception traceStack) {
        return () -> {
            try {
                task.run();
            } catch (Exception e) {
                traceStack.printStackTrace();
                throw e;
            }
        };
    }

    private Exception getException() {
        return new Exception("help to locate invoke stack");
    }

    @Override
    public Future<?> submit(Runnable task) {
        return super.submit(wrap(task, getException()));
    }
}

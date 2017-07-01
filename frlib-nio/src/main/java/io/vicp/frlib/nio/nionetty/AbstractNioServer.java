package io.vicp.frlib.nio.nionetty;

import java.io.IOException;
import java.nio.channels.Selector;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by zhoudr on 2017/6/15.
 */
public abstract class AbstractNioServer implements Runnable{

    protected String threadName;

    protected NioSelectorPool nioSelectorPool;

    protected Executor executor;

    protected Selector selector;

    protected AtomicBoolean wakeUp = new AtomicBoolean(); // 工作标志位

    private final Queue<Runnable> taskQueue = new ConcurrentLinkedQueue<>();

    protected void openSelector() {
        try {
            this.selector = Selector.open();
            executor.execute(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract int select(Selector selector) throws IOException;

    @Override
    public void run() {
        Thread.currentThread().setName(this.threadName);
        while (true) {
            try {
                wakeUp.set(false);
                select(selector);
                processTaskQueue();
                process(selector);
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    private final void processTaskQueue() {
        for(;;) {
            Runnable task = taskQueue.poll();
            if (task == null) {
                break;
            }
            task.run();
        }
    }

    protected final void registerTask(Runnable task) {
        taskQueue.add(task);
        final Selector selector = this.selector;
        if (selector != null) {
            if (wakeUp.compareAndSet(false, true)) {
                selector.wakeup();
            }
        } else {
            taskQueue.remove(task);
        }
    }

    protected abstract void process(Selector selector) throws IOException;
}

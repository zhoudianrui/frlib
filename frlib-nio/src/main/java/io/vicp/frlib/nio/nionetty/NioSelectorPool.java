package io.vicp.frlib.nio.nionetty;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by zhoudr on 2017/6/15.
 */
public class NioSelectorPool {

    private Boss[] bosses;

    private Worker[] workers;

    private AtomicInteger bossIndex = new AtomicInteger(0);

    private AtomicInteger workerIndex = new AtomicInteger(0);

    public NioSelectorPool() {
        this(Executors.newCachedThreadPool(), Executors.newCachedThreadPool());
    }

    public NioSelectorPool(Executor bossExecutor, Executor workerExecutor) {
        initBoss(bossExecutor, 1);
        initWorker(workerExecutor, Runtime.getRuntime().availableProcessors() * 2);
    }

    private void initBoss(Executor bossExecutor, int count) {
        bosses = new NioServerBoss[count];
        for (int i = 0; i < count; i++) {
            bosses[i] = new NioServerBoss(bossExecutor, "boss-" + i, this);
        }
    }

    private void initWorker(Executor workerExecutor, int count) {
        workers = new NioServerWorker[count];
        for (int i = 0; i < count; i++) {
            workers[i] = new NioServerWorker(workerExecutor, "worker-" + i, this);
        }
    }

    public Boss getBoss() {
        int bossCount = bosses.length;
        int index = Math.abs(this.bossIndex.getAndIncrement() % bossCount);
        return bosses[index];
    }

    public Worker getWorker() {
        int workerCount = workers.length;
        int index = Math.abs(this.workerIndex.getAndIncrement() % workerCount); // 平均使用每一个worker
        return workers[index];
    }

}

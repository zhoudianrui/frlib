package io.vicp.frlib.thread.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

/**
 * description :
 * user : zhoudr
 * time : 2017/7/4 18:40
 */

public class Main {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        CountTask task = new CountTask(1, 2000000L);
        computeWithForkJoin(task);
        System.out.println("fork/join run time: " + (System.currentTimeMillis() - startTime));
        startTime = System.currentTimeMillis();
        compute(task);
        System.out.println("loop run time: " + (System.currentTimeMillis() - startTime));
    }

    public static void computeWithForkJoin(CountTask task) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Long> forkJoinTask = forkJoinPool.submit(task);
        try {
            Long result = forkJoinTask.get();
            System.out.println(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static void compute(CountTask task) {
        long sum = 0L;
        for (long start = task.getStart(); start <= task.getEnd(); start++) {
            sum += start;
        }
        System.out.println(sum);
    }
}

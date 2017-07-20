package io.vicp.frlib.thread.forkjoin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

/**
 * description :
 * user : zhoudr
 * time : 2017/7/4 18:29
 */

public final class CountTask extends RecursiveTask<Long> {
    private static final long THRESHOLD = 100000L;

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    private long start;
    private long end;

    public CountTask(long start, long end) {
        if (start > end) {
            this.start = end;
            this.end = start;
        } else {
            this.start = start;
            this.end = end;
        }
    }

    @Override
    protected Long compute() {
        long sum = 0L;
        if (end - start < THRESHOLD) {
            for (long i = start; i <= end; i++) {
                sum += i;
            }
        } else {
            // 分解任务为100个子任务
            int taskCount = 100;
            long step = (start + end) / taskCount;
            List<CountTask> subTaskList = new ArrayList<>();// 任务队列
            long pos = start;
            for (int i = 0; i < taskCount; i++) {
                long lastOne = pos + step;
                if (lastOne > end) {
                    lastOne = end;
                }
                CountTask subTask = new CountTask(pos, lastOne);
                subTaskList.add(subTask);
                subTask.fork();
                pos += step + 1;
            }
            for (CountTask countTask : subTaskList) {
                sum += countTask.join();
            }
        }
        return sum;
    }
}

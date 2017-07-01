import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * description : 验证读写锁的作用，并且比较与ReentrantReadWriteLock的区别
 * user : zhoudr
 * time : 2017/6/21 18:47
 */

public class ReentrantReadWriteLockTest {
    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>();
        Lock readLock;
        Lock writeLock;
        boolean isParallelRead = true; // 产生锁的类型(true:读写锁,false:普通锁)
        if (isParallelRead) {
            ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
            readLock = lock.readLock();
            writeLock = lock.writeLock();
        } else {
            ReentrantLock lock = new ReentrantLock();
            readLock = writeLock = lock;
        }

        Thread readThread = new Thread(new Reader(numbers, readLock), "read");
        Thread writeThread = new Thread(new Writer(numbers, writeLock), "write");
        readThread.start();
        writeThread.start();

        Thread[] readThreadGroup = new Thread[10];
        Thread[] writeThreadGroup = new Thread[10];
        for (int i = 0; i < 10; i++) {
            readThreadGroup[i] = new Thread(new Reader(numbers, readLock), "read" + (i + 1));
            writeThreadGroup[i] = new Thread(new Writer(numbers, writeLock), "write" + (i + 1));
        }
        for (int i = 0; i < 10; i++) {
            readThreadGroup[i].start();
            writeThreadGroup[i].start();
        }
    }
}

class Reader implements Runnable {
    private Lock readLock;
    private List<Integer> numbers;

    public Reader(List<Integer> numbers, Lock readLock) {
        this.numbers = numbers;
        this.readLock = readLock;
    }

    @Override
    public void run() {
        final String threadName = Thread.currentThread().getName();
        try {
            readLock.lock();
            System.out.println("\n" + threadName + " run begin");
            numbers.forEach(number -> System.out.println(threadName + ":" + number));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println(threadName + " run over," + numbers.size() + "\n");
            readLock.unlock();
        }
    }
}

class Writer implements Runnable {
    private Lock writeLock;
    private List<Integer> numbers;

    public Writer(List<Integer> numbers, Lock writeLock) {
        this.numbers = numbers;
        this.writeLock = writeLock;
    }

    @Override
    public void run() {
        final String threadName = Thread.currentThread().getName();
        try {
            writeLock.lock();
            System.out.println("\n" + threadName + " run begin");
            ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
            numbers.add(threadLocalRandom.nextInt(100));
            numbers.add(threadLocalRandom.nextInt(100));
        } finally {
            System.out.println(threadName + " run over\n");
            writeLock.unlock();
        }
    }
}

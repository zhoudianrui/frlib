package io.vicp.frlib.thread.synchronize;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by zhoudr on 2017/3/15.
 */
public class Bucket {
    private static volatile boolean builded = false;

    private static Bucket instance = new Bucket();

    private static final int SIZE = 20;

    private List<Egg> eggs;

    private ReentrantLock lock = new ReentrantLock();

    private Condition notFull = lock.newCondition();

    private Condition notEmpty = lock.newCondition();

    private Bucket() {
        init();
    }

    private void init() {
        eggs = new ArrayList<>(SIZE); // 初始化列表
        builded = true;
    }

    public static Bucket getInstance() {
        if (builded) {
            return instance;
        }
        return null;
    }


    public void put(Egg egg) {
        /*synchronized (eggs){
            try {
                while (eggs.size() >= SIZE) {
                    eggs.wait();
                }
                eggs.add(egg);
                eggs.notifyAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/
        lock.lock();
        try {
            while (eggs.size() >= SIZE) {
                notFull.await();
            }
            eggs.add(egg);
            notEmpty.signalAll();
        } catch (InterruptedException e) {
           e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public Egg get() {
        Egg egg = null;
        /*synchronized (eggs){
            try {
                while (eggs.size() <= 0) {
                    eggs.wait();
                }
                egg = eggs.get(new Random().nextInt(eggs.size()));
                eggs.remove(egg);
                eggs.notifyAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/
        lock.lock();
        try {
            while (eggs.size() <= 0) {
                notEmpty.await();
            }
            egg = eggs.get(new Random().nextInt(eggs.size()));
            eggs.remove(egg);
            notFull.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return egg;
    }
}

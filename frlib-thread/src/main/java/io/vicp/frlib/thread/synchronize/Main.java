package io.vicp.frlib.thread.synchronize;

import java.util.Random;
import java.util.UUID;

/**
 * Created by zhoudr on 2017/3/15.
 */
public class Main {
    public static void main(String[] args) {
        Bucket bucket = Bucket.getInstance();
        Thread productor = new Thread(new Productor(bucket), "productor");
        Thread consumer = new Thread(new Consumer(bucket), "customer");
        consumer.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        productor.start();
    }
}

class Productor implements Runnable {

    private Bucket bucket;

    public Productor(Bucket bucket) {
        this.bucket = bucket;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Egg egg = new Egg();
                egg.setId(UUID.randomUUID().toString());
                bucket.put(egg);
                System.out.println("put:" + egg.getId());
                Thread.sleep(new Random().nextInt(1000));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Consumer implements Runnable {
    private Bucket bucket;

    public Consumer(Bucket bucket) {
        this.bucket = bucket;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Egg egg = bucket.get();
                System.out.println("get:" + egg.getId());
                Thread.sleep(new Random().nextInt(1000));
            }
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

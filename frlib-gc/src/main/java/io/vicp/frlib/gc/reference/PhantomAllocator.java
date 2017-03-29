package io.vicp.frlib.gc.reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;

/**
 * phantom reference is always applied to total memeory of jvm is limited to
 * specified size
 * Created by zhoudr on 2017/3/28.
 */
public class PhantomAllocator {

    private byte[] data = null;
    private ReferenceQueue<byte[]> queue = new ReferenceQueue<>(); // 引用队列
    private Reference<? extends byte[]> ref = null;

    public byte[] get(int size) {
        if (ref == null) {
            data = new byte[size];
            ref = new PhantomReference<>(data, queue);
        } else {
            data = null;
            System.gc();
            try {
                ref = queue.remove();
                ref.clear();
                ref = null;
                System.gc();
                data = new byte[size];
                ref = new PhantomReference<>(data, queue);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    public static void main(String[] args) {
        PhantomAllocator phantomAllocator = new PhantomAllocator();
        for (int i = 0; i < 100; i++) {
            phantomAllocator.get(6 * 1024 * 1024);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

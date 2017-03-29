package io.vicp.frlib.gc.reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;

/**
 * test phantom reference
 * after phantom reference enque, finalize method has been done.
 * Created by zhoudr on 2017/3/28.
 */
public class UseReferenceQueue {
    private static class ReferencedObject {
        protected void finalize() throws Throwable {
            System.out.println("finalize方法被调用");
            super.finalize();
        }
    }

    public void phantomReferenceQueue() {
        ReferenceQueue<ReferencedObject> queue = new ReferenceQueue<>();
        ReferencedObject obj = new ReferencedObject();
        PhantomReference<ReferencedObject> phantomReference = new PhantomReference<>(obj, queue);
        obj = null;
        Reference<? extends ReferencedObject> ref = null;
        while ((ref = queue.poll()) == null) {
            System.gc();
        }
        phantomReference.clear();
        System.out.println(ref == phantomReference);
        System.out.println("幽灵引用被清除");
    }

    public static void main(String[] args) {
        UseReferenceQueue useReferenceQueue = new UseReferenceQueue();
        useReferenceQueue.phantomReferenceQueue();
    }
}

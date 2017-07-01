import java.util.concurrent.SynchronousQueue;

/**
 * Created by zhoudr on 2017/4/25.
 */
public class TestSynchronousQueue {

    public static void main(String[] args) {
        final SynchronousQueue<Integer> synchronousQueue = new SynchronousQueue<>();
        new Thread(()->{
                        try {
                            synchronousQueue.put(1);
                            //synchronousQueue.offer(1, 1, TimeUnit.SECONDS);
                            //synchronousQueue.add(1);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }, "put-1").start();

        /*Thread[] takeThreadGroup = new Thread[2];
        for (int i = 0,length = takeThreadGroup.length; i < length; i++) {
            takeThreadGroup[i] = new Thread(() -> {
                try {
                    Integer value = synchronousQueue.take();
                    System.out.println(value);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "take-" + (i + 1));
        }
        for (int i = 0, length = takeThreadGroup.length; i < length; i++) {
            takeThreadGroup[i].start();
        }

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0, length = takeThreadGroup.length; i < length; i++) {
            final Thread thread = takeThreadGroup[i];
            if (thread.isAlive()) {
                System.out.println(thread.getName());
                thread.interrupt();
            }
        }*/
    }
}

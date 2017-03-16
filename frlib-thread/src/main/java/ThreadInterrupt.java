/**
 * 结论:运行状态下的线程被中断，该线程让出cpu时间片，设置为中断状态
 * 待获取CPU时间片后继续执行，如果是阻塞状态或者等待状态被中断，则
 * 线程会抛出异常，根据是否catch异常走接下来的流程
 *
 * Created by zhoudr on 2017/3/14.
 */
public class ThreadInterrupt {
    public static void main(String[] args) {

        testSelfInterrupt();

        //testInterruptBlock();
    }

    private static void testSelfInterrupt() {
        MyThreadInterrupt myThreadInterupt = new MyThreadInterrupt();
        Thread thread = new Thread(myThreadInterupt, "myInteruptThread");
        thread.start();
        thread.interrupt();
        System.out.println("main thread is end"); // 主线程运行
    }

    private static void testInterruptBlock() {
        Thread currentThread = Thread.currentThread();
        Thread thread = new Thread(new MyThreadInterruptParent(currentThread));
        thread.start();
        try {
            thread.join(); // 此时main线程处于阻塞状态
        } catch (InterruptedException e) {
            System.out.println("parent thread will die");
            System.out.println(Thread.currentThread().isInterrupted());
        }
    }
}

class MyThreadInterrupt implements Runnable {

    @Override
    public void run() {
        while (true) {
            if (Thread.interrupted()) { // 获取线程的中断状态并清除中断状态
                System.out.println("I am interrupted");
            } else {
                System.out.println("Going....");
            }
            long now = System.currentTimeMillis();
            while (System.currentTimeMillis() - now < 1000) {
                // 空转
            }
        }
    }
}

/**
 * 子线程告知父线程不用等待子线程
 */
class MyThreadInterruptParent implements Runnable {

    private Thread parent = null;

    public MyThreadInterruptParent(Thread parent) {
        this.parent = parent;
    }

    @Override
    public void run() {
        while (true) {
            //System.out.println(parent.getState());
            long now = System.currentTimeMillis();
            while (System.currentTimeMillis() - now < 2000) {
                // 空转
            }
            //parent.interrupt();
            Thread.currentThread().interrupt();
            System.out.println("Running...");
            //System.out.println(parent.getState());
        }
    }
}

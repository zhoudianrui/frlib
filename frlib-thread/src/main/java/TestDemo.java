import java.util.concurrent.TimeUnit;

/**
 * Created by zhoudr on 2017/6/13.
 */
public class TestDemo {
    public static void main(String[] args) {
        Thread daemonThread = new Thread(new DaemonThread(), "后台线程");
        daemonThread.setDaemon(true);
        daemonThread.start();
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + ":" + i);
        }
    }
}

class DaemonThread extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(getName() + ":" + i);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(getName() + "run over.");
    }
}

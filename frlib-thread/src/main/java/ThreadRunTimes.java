import java.util.concurrent.TimeUnit;

/**
 * Created by zhoudr on 2017/3/12.
 */
public class ThreadRunTimes {
    public static void main(String[] args) throws InterruptedException {
        /*Thread thread = new Thread(new MyThread());
        thread.start();
        System.out.println(thread.getState());
        thread.start();*/
        Thread thread = new Thread(new MyThread());
        thread.start();
        thread.join();
        Thread.sleep(1000);
        System.out.println(thread.getState() + ",main ...");
    }

}
class MyThread implements Runnable {

    @Override
    public void run() {
        System.out.println("run ...");
        try {
            Thread.sleep(5000);
            throw new InterruptedException();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

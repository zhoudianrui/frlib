import java.util.Date;


public class TestStop {
    public static void main(String[] args) {
        MyThread ts = new MyThread();
        ts.start();

        // 你超过三秒不醒过来，我就干死你
        try {
            Thread.sleep(3000);
            System.out.println("main call");
//            ts.stop();
            ts.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class MyThread extends Thread {
        @Override
        public void run() {
            Date now = new Date();
            System.out.println("开始执行：" + now);
            long lastTime = now.getTime();
            while (true) {
                // 我要休息10秒钟，亲，不要打扰我哦
                /*try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    System.out.println("线程被中断了");
                }*/
                if (System.currentTimeMillis() - lastTime > 2000) {
                    System.out.println("2秒后线程继续运行");
                    lastTime = System.currentTimeMillis();
                }
            }
            //System.out.println("结束执行：" + new Date());
        }
    }
}

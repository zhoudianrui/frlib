import io.vicp.frlib.util.ThreadUtil;

import java.util.concurrent.TimeUnit;

/**
 * Created by zhoudr on 2017/4/25.
 */
public class TestThreadUtil {

    public static void main(String[] args) {
        try {
            System.out.println("main");
            ThreadUtil.addedToShutdownHook();
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

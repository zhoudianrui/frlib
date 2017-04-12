package io.vicp.frlib.gc.memory;

/**
 * 测试分配担保策略
 * -XX:-HandlePromotionFailure=true
 * 是否允许分配担保策略失败(该参数在1.6U22版本之后无效)
 * Created by zhoudr on 2017/3/30.
 */
public class HandlePromotion {
    private static final int OneMB = 1024 * 1024;

    public static void main(String[] args) throws InterruptedException {
        Thread.sleep(10000);
        byte[] allocation1, allocation2, allocation3, allocation4, allocation5, allocation6, allocation7;
        allocation1 = new byte[2 * OneMB];
        allocation2 = new byte[2 * OneMB];
        allocation3 = new byte[2 * OneMB];
        allocation1 = null;
        allocation4 = new byte[1 * OneMB + 4 * OneMB / 5]; // minorGC，上次租借4m(a2与a3在old)
        allocation5 = new byte[2 * OneMB];
        allocation6 = new byte[2 * OneMB];
        /*allocation4 = null;
        allocation5 = null;
        allocation6 = null;*/
        allocation7 = new byte[4 * OneMB]; // 预备minorGC，old剩余6m,分配担保失败，触发FullGC(minorGC + majorGC)
        // 此时minorGC尽量复制所有的新生代的对象到老年代
        //Thread.sleep(300000);
    }
}

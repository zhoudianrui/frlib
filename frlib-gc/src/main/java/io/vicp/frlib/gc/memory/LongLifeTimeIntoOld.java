package io.vicp.frlib.gc.memory;

/**
 * -XX:+PrintTenuringDistribution(GC后新生代各个年龄对象的大小)
 * -XX:+PrintHeapAtGC(发生GC时，输出堆信息)
 * -XX:MaxTenuringThreshold=1(晋升到老年代的对象年龄阈值)
 * 注:相同年龄的对象大于新生代一般的空间时，minorgc时，大于等于该年龄的移动到老年代
 * Created by zhoudr on 2017/3/30.
 */
public class LongLifeTimeIntoOld {
    private final static int ONE_MB = 1024 * 1024;

    public static void main(String[] args) {
        byte[] testCase1, testCase2, testCase3, testCase4;
        testCase1 = new byte[1 * ONE_MB / 4];
        testCase2 = new byte[7 * ONE_MB];
        testCase2 = null;
        testCase3 = new byte[7 * ONE_MB];
        //testCase3 = null;
        //testCase4 = new byte[1 * ONE_MB];
        /*try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }
}

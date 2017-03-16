/**
 * -XX:+UseSerialGC(client模式下，使用Serial+Serial Old的收集器)
 * -Xms20m(最小堆内存20m)
 * -Xmx20m(最大堆内存20m)
 * -Xmn10m(新生代堆内存10m)
 * -verbose:gc(与-XX:+PrintGC相等，前者为稳定版本，后者为非稳定版本,Print messages at garbage collection)
 * -XX:+PrintGCDetail(打印堆内存,Print more details at garbage collection)
 * -XX:SurvivorRatio=8(新生代eden:survivor=8:1,from=10%,to=10%,eden=80%)
 * -XX:PretenuredSizeThreshold=3145728(3M)
 * -Xloggc:C:\Users\zhoudr\gc.log
 *
 * Created by zhoudr on 2017/3/14.
 */
public class BigMemoryAllocate {
    private static final int _1MB = 1024 * 1024;

    public static void main(String[] args) {
        byte[] allocation;
        allocation = new byte[4 * _1MB];
    }
}

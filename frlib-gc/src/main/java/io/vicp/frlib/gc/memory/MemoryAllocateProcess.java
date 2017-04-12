package io.vicp.frlib.gc.memory;

/**
 * Created by zhoudr on 2017/3/29.
 */
public class MemoryAllocateProcess {

    private static final int tenMB = 10 * 1024 * 1024;

    public static void main(String[] args) {
        byte[] allocate1, allocate2, allocate3, allocate4, allocate5;
        allocate1 = new byte[tenMB / 5];
        allocate2 = new byte[5 * tenMB];
        allocate3 = new byte[4 * tenMB]; // MinorGC
        allocate3 = null;
        allocate3 = new byte[6 * tenMB]; // FullGC and MinorGC
        allocate4 = new byte[35 * tenMB / 10]; // FullGC and MinorGC
        //allocate5 = new byte[15 * tenMB / 10];
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

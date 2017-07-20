package io.vicp.frlib.gc.memory;

/**
 * description :
 * user : zhoudr
 * time : 2017/7/7 18:44
 */

public class JVMOpt {
    public static void main(String[] args) {
        JVMOpt jvmOpt = new JVMOpt();
        long startTime = System.currentTimeMillis();
        for(int i = 0; i < 100000000; i++) {
            jvmOpt.m();
        }
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
    }

    public void m() {
        new User(1, "John");
    }

}

class User{
    private int id;
    private String name;
    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }
}

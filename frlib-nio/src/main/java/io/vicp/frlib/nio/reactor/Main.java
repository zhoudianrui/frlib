package io.vicp.frlib.nio.reactor;

/**
 * Created by zhoudr on 2017/6/7.
 */
public class Main {
    public static void main(String[] args) {
        ReactorServer server = new ReactorServer();
        server.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread client;
        for (int i = 0; i < 2; i++) {
            client = new Thread(new ReactorClient("127.0.0.1", 61284), "client" + i);
            client.start();
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        server.shutdown();
    }
}

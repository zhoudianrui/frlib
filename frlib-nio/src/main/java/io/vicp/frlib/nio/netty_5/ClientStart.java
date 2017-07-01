package io.vicp.frlib.nio.netty_5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * description : 客户端启动类
 * user : zhoudr
 * time : 2017/6/26 16:12
 */

public class ClientStart {
    public static void main(String[] args) {
        MultiplyClient multiplyClient = new MultiplyClient();
        multiplyClient.init(5);
        while (true) {
            System.out.println("请输入:");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try {
                String msg = reader.readLine();
                multiplyClient.nextChannel().writeAndFlush(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

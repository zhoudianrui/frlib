package io.vicp.frlib.nio.netty_3.fundamental.discard;

import io.vicp.frlib.nio.netty_3.ChannelHandlerAdapter;
import io.vicp.frlib.nio.netty_3.ClientUtil;

import java.util.Arrays;

/**
 * description :
 * user : zhoudr
 * time : 2017/6/26 17:57
 */

public class DiscardClient {
    static final int SIZE = Integer.parseInt(System.getProperty("size", "256"));

    public static void main(String[] args) {
        ClientUtil.startClient(Arrays.asList(new ChannelHandlerAdapter("discard", new DiscardClientHandler())), "127.0.0.1", 61284);
    }
}

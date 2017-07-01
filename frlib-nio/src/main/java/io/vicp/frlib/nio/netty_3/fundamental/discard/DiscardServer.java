package io.vicp.frlib.nio.netty_3.fundamental.discard;

import io.vicp.frlib.nio.netty_3.ChannelHandlerAdapter;
import io.vicp.frlib.nio.netty_3.ServerUtil;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

import java.util.Arrays;

/**
 * description : netty3服务端
 * user : zhoudr
 * time : 2017/6/26 17:13
 */

public class DiscardServer {

    public static void main(String[] args) {
        ServerUtil.startServer(Arrays.asList(
                new ChannelHandlerAdapter("decoder", new StringDecoder()),
                new ChannelHandlerAdapter("encoder", new StringEncoder()),
                new ChannelHandlerAdapter("discard", new DiscardServerHandler())), 61284);
    }

}

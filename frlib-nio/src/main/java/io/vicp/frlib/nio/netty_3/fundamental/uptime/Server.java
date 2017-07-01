package io.vicp.frlib.nio.netty_3.fundamental.uptime;

import io.vicp.frlib.nio.netty_3.ChannelHandlerAdapter;
import io.vicp.frlib.nio.netty_3.ServerUtil;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

import java.util.Arrays;

/**
 * description :
 * user : zhoudr
 * time : 2017/6/26 18:34
 */

public class Server {
    public static void main(String[] args) {
        ServerUtil.startServer(Arrays.asList(new ChannelHandlerAdapter("decoder", new StringDecoder()),
                                            new ChannelHandlerAdapter("encoder", new StringEncoder()),
                                            new ChannelHandlerAdapter("handler", new ServerHandler())),  61284);
    }
}

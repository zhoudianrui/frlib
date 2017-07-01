package io.vicp.frlib.nio.netty_3.binaryprotocol.objectecho;

import io.vicp.frlib.nio.netty_3.ChannelHandlerAdapter;
import io.vicp.frlib.nio.netty_3.ClientUtil;
import org.jboss.netty.handler.codec.serialization.ClassResolvers;
import org.jboss.netty.handler.codec.serialization.ObjectDecoder;
import org.jboss.netty.handler.codec.serialization.ObjectEncoder;

import java.util.Arrays;

/**
 * description : 二进制对象传输的客户端
 * user : zhoudr
 * time : 2017/6/28 14:23
 */

public class Client {

    private static final Integer BATCH_SIZE = 256;

    public static void main(String[] args) {
        ClientUtil.startClient(Arrays.asList(
                new ChannelHandlerAdapter("objectDecoder", new ObjectDecoder(ClassResolvers.cacheDisabled(Client.class.getClassLoader()))),
                new ChannelHandlerAdapter("objectEncoder", new ObjectEncoder()),
                new ChannelHandlerAdapter("handler", new ObjectEchoClientHandler(BATCH_SIZE))), "127.0.0.1", 61284);
    }
}

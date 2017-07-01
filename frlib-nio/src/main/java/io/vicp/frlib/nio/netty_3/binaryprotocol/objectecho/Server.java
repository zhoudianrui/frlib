package io.vicp.frlib.nio.netty_3.binaryprotocol.objectecho;

import io.vicp.frlib.nio.netty_3.ServerUtil;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.codec.serialization.ClassResolvers;
import org.jboss.netty.handler.codec.serialization.ObjectDecoder;
import org.jboss.netty.handler.codec.serialization.ObjectEncoder;

/**
 * description : 二进制传输协议服务端
 * user : zhoudr
 * time : 2017/6/28 14:12
 */

public class Server {

    public static void main(String[] args) {
        ChannelPipelineFactory channelPipelineFactory = () -> {
            ChannelPipeline channelPipeline = Channels.pipeline();
            channelPipeline.addLast("objectdecoder", new ObjectDecoder(ClassResolvers.cacheDisabled(Server.class.getClassLoader())));
            channelPipeline.addLast("objectencoder", new ObjectEncoder());
            channelPipeline.addLast("handler", new ObjectEchoServerHandler());
            return channelPipeline;
        };
        ServerUtil.startServer(channelPipelineFactory, 61284);
    }
}

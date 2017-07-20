package io.vicp.frlib.nio.netty_3.customprotocol.server;

import io.vicp.frlib.nio.netty_3.ServerUtil;
import io.vicp.frlib.nio.netty_3.customprotocol.code.RequestDecoder;
import io.vicp.frlib.nio.netty_3.customprotocol.code.ResponseEncoder;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;

/**
 * description : 自定义协议传输协议服务端
 * user : zhoudr
 * time : 2017/6/28 14:12
 */

public class Server {

    public static void main(String[] args) {
        ChannelPipelineFactory channelPipelineFactory = () -> {
            ChannelPipeline channelPipeline = Channels.pipeline();
            channelPipeline.addLast("encoder", new ResponseEncoder());
            channelPipeline.addLast("decoder", new RequestDecoder());
            channelPipeline.addLast("handler", new RequestServerHandler());
            return channelPipeline;
        };
        ServerUtil.startServer(channelPipelineFactory, 61284);
    }
}

package io.vicp.frlib.nio.netty_3.binaryprotocol.worldclock;

import io.vicp.frlib.nio.netty_3.ServerUtil;
import org.jboss.netty.channel.ChannelPipelineFactory;

/**
 * description : 基于protobuf的时间同步服务端
 * user : zhoudr
 * time : 2017/6/28 15:26
 */

public class Server {

    public static void main(String[] args) {
        ChannelPipelineFactory channelPipelineFactory = new LocalTimeServerPipelineFactory();
        ServerUtil.startServer(channelPipelineFactory, 61284);
    }
}

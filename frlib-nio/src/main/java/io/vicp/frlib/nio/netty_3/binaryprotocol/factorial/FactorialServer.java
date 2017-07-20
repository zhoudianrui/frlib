package io.vicp.frlib.nio.netty_3.binaryprotocol.factorial;

import io.vicp.frlib.nio.netty_3.ServerUtil;
import org.jboss.netty.channel.ChannelPipelineFactory;

/**
 * description : 采用自定义编解码以计算阶乘，服务端
 * user : zhoudr
 * time : 2017/7/3 18:15
 */

public class FactorialServer {
    public static void main(String[] args) {
        ChannelPipelineFactory channelPipelineFactory = new FactorialServerPipelineFactory();
        ServerUtil.startServer(channelPipelineFactory, 61284);
    }
}

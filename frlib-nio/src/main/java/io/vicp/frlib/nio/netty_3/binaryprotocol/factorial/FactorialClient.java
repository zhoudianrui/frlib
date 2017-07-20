package io.vicp.frlib.nio.netty_3.binaryprotocol.factorial;

import io.vicp.frlib.nio.netty_3.ClientUtil;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;

import java.net.InetSocketAddress;

/**
 * description : 采用自定义编解码以计算阶乘，客户端
 * user : zhoudr
 * time : 2017/7/3 18:25
 */

public class FactorialClient {
    static final int COUNT = Integer.parseInt(System.getProperty("count", "10"));

    public static void main(String[] args) throws Exception {
        ClientBootstrap client = ClientUtil.getClientBootstrap();
        try {
            client.setPipelineFactory(new FactorialClientPipelineFactory());
            ChannelFuture connectFuture = client.connect(new InetSocketAddress("127.0.0.1", 61284));
            Channel channel = connectFuture.sync().getChannel();
            FactorialClientHandler handler = channel.getPipeline().get(FactorialClientHandler.class);
            channel.getCloseFuture().sync();
        } finally {
            client.releaseExternalResources();
        }
    }
}

package io.vicp.frlib.nio.netty_3.fundamental.basic;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhoudr on 2017/6/13.
 */
public class Server {

    public static void main(String[] args) {
        ServerBootstrap bootstrap = new ServerBootstrap();
        ExecutorService bossExecutor = Executors.newCachedThreadPool();
        ExecutorService workerExecutor = Executors.newCachedThreadPool();
        bootstrap.setFactory(new NioServerSocketChannelFactory(bossExecutor, workerExecutor));

        bootstrap.setPipelineFactory(() -> {
                ChannelPipeline channelPipeline = Channels.pipeline();
                channelPipeline.addLast("decoder", new StringDecoder());
                channelPipeline.addLast("encoder", new StringEncoder());
                channelPipeline.addLast("HelloHandler", new HelloHandler());
                return channelPipeline;
            });

        bootstrap.bind(new InetSocketAddress(61284));
        System.out.println("server start");
    }
}

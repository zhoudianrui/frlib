package io.vicp.frlib.nio.netty_3;

import org.apache.commons.collections.CollectionUtils;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * description :
 * user : zhoudr
 * time : 2017/6/26 17:14
 */

public class ServerUtil {

    public static void startServer(List<ChannelHandlerAdapter> handlerList, int port) {
        ServerBootstrap bootstrap = new ServerBootstrap();
        ExecutorService bossExecutor = Executors.newCachedThreadPool();
        ExecutorService workerExecutor = Executors.newCachedThreadPool();
        bootstrap.setFactory(new NioServerSocketChannelFactory(bossExecutor, workerExecutor));

        bootstrap.setPipelineFactory(() -> {
            ChannelPipeline channelPipeline = Channels.pipeline();
            if (CollectionUtils.isNotEmpty(handlerList)) {
                for (ChannelHandlerAdapter channelHandler : handlerList) {
                    channelPipeline.addLast(channelHandler.getName(), channelHandler.getChannelHandler());
                }
            }
            return channelPipeline;
        });

        InetSocketAddress localAddress = new InetSocketAddress(port);
        bootstrap.bind(localAddress);

        System.out.println("server start @" + localAddress.getHostString() + ":" + port);
    }

    public static void startServer(ChannelPipelineFactory pipelineFactory, int port) {
        ServerBootstrap bootstrap = new ServerBootstrap();
        // 设置工厂
        ExecutorService bossExecutor = Executors.newCachedThreadPool();
        ExecutorService workerExecutor = Executors.newCachedThreadPool();
        bootstrap.setFactory(new NioServerSocketChannelFactory(bossExecutor, workerExecutor));

        // 设置管道工厂
        bootstrap.setPipelineFactory(pipelineFactory);

        // 绑定端口
        InetSocketAddress localAddress = new InetSocketAddress(port);
        bootstrap.bind(localAddress);

        System.out.println("server start @" + localAddress.getHostString() + ":" + port);
    }

}

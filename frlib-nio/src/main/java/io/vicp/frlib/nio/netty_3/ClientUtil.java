package io.vicp.frlib.nio.netty_3;

import org.apache.commons.collections.CollectionUtils;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * description :
 * user : zhoudr
 * time : 2017/6/26 17:46
 */

public class ClientUtil {

    public static void startClient(List<ChannelHandlerAdapter> adapterList, String host, int port) {
        ClientBootstrap client = new ClientBootstrap();
        try {
            // 设置channel工厂
            Executor boss = Executors.newCachedThreadPool();
            Executor worker = Executors.newCachedThreadPool();
            client.setFactory(new NioClientSocketChannelFactory(boss, worker));

            // 设置管道工厂
            client.setPipelineFactory(() -> {
                ChannelPipeline channelPipeline = Channels.pipeline();
                if (CollectionUtils.isNotEmpty(adapterList)) {
                    for (ChannelHandlerAdapter adapter : adapterList) {
                        channelPipeline.addLast(adapter.getName(), adapter.getChannelHandler());
                    }
                }
                return channelPipeline;
            });

            ChannelFuture channelFuture = client.connect(new InetSocketAddress(host, port));
            channelFuture.getChannel().getCloseFuture().sync();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }finally {
            client.releaseExternalResources();
        }
    }

    public static ClientBootstrap getClientBootstrap() {
        ClientBootstrap client = new ClientBootstrap();
        // 设置channel工厂
        Executor boss = Executors.newCachedThreadPool();
        Executor worker = Executors.newCachedThreadPool();
        client.setFactory(new NioClientSocketChannelFactory(boss, worker));
        return client;
    }
}

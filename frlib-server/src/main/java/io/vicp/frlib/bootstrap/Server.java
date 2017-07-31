package io.vicp.frlib.bootstrap;

import io.vicp.frlib.chat.codc.RequestDecoder;
import io.vicp.frlib.chat.codc.ResponseEncoder;
import io.vicp.frlib.handler.ServerHandler;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>服务端启动类<p>
 *
 * @author zhoudr
 * @version $Id: Server, v 0.1 2017/7/29 16:06 zhoudr Exp $$
 */
@Component
public class Server {

    @Value("${server.port}")
    private int port;

    public void start() {
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        // 设置工厂
        ExecutorService boss = Executors.newCachedThreadPool();
        ExecutorService worker = Executors.newCachedThreadPool();
        serverBootstrap.setFactory(new NioServerSocketChannelFactory(boss, worker));

        // 设置管道
        serverBootstrap.setPipelineFactory(() -> {
            ChannelPipeline channelPipeline = Channels.pipeline();
            channelPipeline.addLast("responseEncoder", new ResponseEncoder());
            channelPipeline.addLast("requestDecoder", new RequestDecoder());
            channelPipeline.addLast("handler", new ServerHandler());
            return channelPipeline;
        });

        // 配置其他属性
        serverBootstrap.setOption("backlog", 1024);

        // 绑定端口
        serverBootstrap.bind(new InetSocketAddress(port));

        System.out.println("server start");

    }
}

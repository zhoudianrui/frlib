package io.vicp.frlib.bootstrap;

import io.vicp.frlib.chat.codc.RequestEncoder;
import io.vicp.frlib.chat.codc.ResponseDecoder;
import io.vicp.frlib.handler.ClientHandler;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>客户端<p>
 *
 * @author zhoudr
 * @version $Id: Client, v 0.1 2017/7/31 15:24 zhoudr Exp $$
 */

public class Client {

    private final String host;

    private final int port;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() {
        ClientBootstrap clientBootstrap = new ClientBootstrap();
        // 设置工厂
        ExecutorService boss = Executors.newCachedThreadPool();
        ExecutorService worker = Executors.newCachedThreadPool();
        clientBootstrap.setFactory(new NioClientSocketChannelFactory(boss, worker));

        // 设置管道
        clientBootstrap.setPipelineFactory(() -> {
            ChannelPipeline channelPipeline = Channels.pipeline();
            channelPipeline.addLast("requestEncoder", new RequestEncoder());
            channelPipeline.addLast("responseDecoder", new ResponseDecoder());
            channelPipeline.addLast("handler", new ClientHandler());
            return channelPipeline;
        });
        clientBootstrap.connect(new InetSocketAddress(host, port));
    }
}

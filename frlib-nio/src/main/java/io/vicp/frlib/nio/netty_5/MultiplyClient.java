package io.vicp.frlib.nio.netty_5;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * description : netty5客户端多链接
 * user : zhoudr
 * time : 2017/6/26 15:42
 */

public class MultiplyClient {

    private Bootstrap bootstrap = new Bootstrap();

    private List<Channel> channels;

    private AtomicInteger index = new AtomicInteger(0);

    public void init(int count) {
        NioEventLoopGroup worker = new NioEventLoopGroup();
        bootstrap.group(worker);

        bootstrap.channel(NioSocketChannel.class);

        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline channelPipeline = ch.pipeline();
                channelPipeline.addLast(new StringDecoder());
                channelPipeline.addLast(new StringEncoder());
                channelPipeline.addLast(new ClientHandler());
            }
        });

        bootstrap.option(ChannelOption.TCP_NODELAY, true);

        channels = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            ChannelFuture channelFuture = bootstrap.connect("10.7.253.120", 61284);
            Channel channel = channelFuture.channel();
            channels.add(channel);
        }
    }

    public Channel nextChannel() {
        return getFirstActiveChannel(0);
    }

    private Channel getFirstActiveChannel(int count) {
        Channel channel = channels.get(Math.abs(index.getAndIncrement() % channels.size()));
        if (!channel.isActive()) {
            // 重连，如果其他链接也已经断开，都重新链接
            reconnect(channel);
            if (count >= channels.size()) {
                throw new RuntimeException("no can use channel");
            }
            return getFirstActiveChannel(count + 1);
        }
        return channel;
    }

    private void reconnect(Channel channel) {
        synchronized (channel) {
            int channelIndex = channels.indexOf(channel);
            if (channelIndex == -1) {
                return;
            }
            Channel newChannel = bootstrap.connect("10.7.253.120", 61284).channel();
            channels.set(channelIndex, newChannel);
        }
    }
}

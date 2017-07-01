package io.vicp.frlib.nio.netty_5;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;

/**
 * description : netty5服务端
 * user : zhoudr
 * time : 2017/6/26 15:05
 */

public class Server {

    public static void main(String[] args) {
        ServerBootstrap server = new ServerBootstrap();

        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // 设置EventLoopEvent
            bossGroup = new NioEventLoopGroup();
            workerGroup = new NioEventLoopGroup();

            server.group(bossGroup, workerGroup);

            // 设置channel
            server.channel(NioServerSocketChannel.class);

            // 设置pipeline
            server.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    ChannelPipeline channelPipeline = socketChannel.pipeline();
                    channelPipeline.addLast(new StringDecoder());
                    channelPipeline.addLast(new StringEncoder());
                    channelPipeline.addLast(new ServerHandler());
                }
            });

            // 设置属性
            server.option(ChannelOption.SO_BACKLOG, 2048); // 最大连接数
            server.childOption(ChannelOption.SO_KEEPALIVE, true); // 是否保活
            server.childOption(ChannelOption.TCP_NODELAY, true); // tcp链接是否延迟发送

            // 绑定端口
            ChannelFuture channelFuture = server.bind(new InetSocketAddress(61284)).sync();
            System.out.println("server start");
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}

package io.vicp.frlib.nio.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by zhoudr on 2017/3/10.
 */
public class TimeServer {

    public static void main(String[] args) {
        int port = 5566;
        /*try {
            if (!ArrayUtils.isEmpty(args)) {
                port = Integer.parseInt(args[0]);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }*/
        new TimeServer().bind(port);
    }

    public void bind(int port) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.ALLOCATOR.SO_BACKLOG, 1024)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new TimeServerHandler());
                        }
                    });

            // 绑定端口，同步等待
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            // 等待服务器监听端口关闭
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 优雅退出，释放线程池资源SS
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}

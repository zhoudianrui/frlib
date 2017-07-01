package io.vicp.frlib.nio.netty_3.textprotocols.telnet;

import io.vicp.frlib.nio.netty_3.Constants;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * description : telnet客户端
 * user : zhoudr
 * time : 2017/6/27 15:55
 */

public class Client {
    public static void main(String[] args) {
        ClientBootstrap clientBootstrap = new ClientBootstrap();
        InetSocketAddress remoteAddress = new InetSocketAddress("127.0.0.1", 61284);
        try {
            Executor boss = Executors.newCachedThreadPool();
            Executor worker = Executors.newCachedThreadPool();
            clientBootstrap.setFactory(new NioClientSocketChannelFactory(boss, worker));

            // 设置pipeline工厂
            clientBootstrap.setPipelineFactory(new TelnetClientPipelineFactory());
            ChannelFuture channelFuture = clientBootstrap.connect(remoteAddress);
            Channel channel = channelFuture.getChannel();

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            ChannelFuture lastWriteFuture = null;
            while (true) {
                String msg = reader.readLine();
                if (msg == null) {
                    break;
                }
                lastWriteFuture = channel.write(msg + Constants.LINE_DELIMITER);
                if ("bye".equalsIgnoreCase(msg)) {
                    channel.getCloseFuture().sync();
                    break;
                }
            }
            if (lastWriteFuture != null) {
                channelFuture.sync();
            }
            channel.close().sync();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            clientBootstrap.releaseExternalResources();
        }
    }
}

package io.vicp.frlib.nio.netty_3.textprotocols.securechat;

import io.vicp.frlib.nio.netty_3.ClientUtil;
import io.vicp.frlib.nio.netty_3.Constants;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.handler.ssl.SslContext;
import org.jboss.netty.handler.ssl.util.InsecureTrustManagerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;

/**
 * description :
 * user : zhoudr
 * time : 2017/6/27 18:42
 */

public class Client {
    public static void main(String[] args) {
        try {
            final SslContext sslContext = SslContext.newClientContext(InsecureTrustManagerFactory.INSTANCE);

            ClientBootstrap client = ClientUtil.getClientBootstrap();
            // 设置管道工厂
            ChannelPipelineFactory pipelineFactory = new SecureClientPipelineFactory(sslContext);
            client.setPipelineFactory(pipelineFactory);

            // 绑定端口
            InetSocketAddress remoteAddress = new InetSocketAddress("127.0.0.1", 61284);
            ChannelFuture channelFuture = client.connect(remoteAddress);
            Channel channel = channelFuture.sync().getChannel();

            ChannelFuture lastWriteFuter = null;
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                String message = reader.readLine();
                if (message == null) {
                    break;
                }
                lastWriteFuter = channel.write(message + Constants.LINE_DELIMITER);

                if ("bye".equalsIgnoreCase(message)) {
                    channel.getCloseFuture().sync();
                    break;
                }
            }
            if (lastWriteFuter != null) {
                lastWriteFuter.sync();
            }
            channel.close().sync();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

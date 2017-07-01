package io.vicp.frlib.nio.netty_3.binaryprotocol.protobuf;

import io.vicp.frlib.nio.netty_3.ClientUtil;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFuture;

import java.net.InetSocketAddress;

/**
 * description :
 * user : zhoudr
 * time : 2017/6/30 10:14
 */

public class Client {
    public static void main(String[] args) {
        ClientBootstrap client = ClientUtil.getClientBootstrap();
        try {
            client.setPipelineFactory(new ProtobufClientPipelineFactory());
            ChannelFuture channelFuture = client.connect(new InetSocketAddress("127.0.0.1", 61284));
            channelFuture.getChannel().getCloseFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            client.releaseExternalResources();
        }
    }
}

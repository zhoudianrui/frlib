package io.vicp.frlib.nio.netty_3.binaryprotocol.worldclock;

import io.vicp.frlib.nio.netty_3.ClientUtil;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipelineFactory;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * description : 获取指定城市的当前是时间客户端，采用protobuf协议传输
 * user : zhoudr
 * time : 2017/6/29 16:29
 */

public class Client {
    public static void main(String[] args) {
        ClientBootstrap client = ClientUtil.getClientBootstrap();
        ChannelPipelineFactory channelPipelineFactory = new LocalTimeClientPipelineFactory();
        client.setPipelineFactory(channelPipelineFactory);
        ChannelFuture channelFuture = client.connect(new InetSocketAddress("127.0.0.1", 61284));
        try {
            Channel channel = channelFuture.sync().getChannel();
            LocalTimeClientHandler handler = channel.getPipeline().get(LocalTimeClientHandler.class);
            List<String> CITIES = new ArrayList<>(Arrays.asList("Asia/Seoul,Asia/Shanghai,Europe/Berlin,America/Los_Angeles".split(",")));
            List<String> response = handler.getLocalTimes(CITIES);
            channel.close().sync();
            for (int i= 0; i < response.size(); i++) {
                System.out.format("%28s: %s%n", CITIES.get(i), response.get(i));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            client.releaseExternalResources();
        }
    }
}

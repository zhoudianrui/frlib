package io.vicp.frlib.nio.netty_3.customprotocol.client;

import io.vicp.frlib.nio.netty_3.ClientUtil;
import io.vicp.frlib.nio.netty_3.customprotocol.code.RequestEncoder;
import io.vicp.frlib.nio.netty_3.customprotocol.code.ResponseDecoder;
import io.vicp.frlib.nio.netty_3.customprotocol.entity.Request;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.Channels;

import java.net.InetSocketAddress;

/**
 * description : 自定义协议传输的客户端
 * user : zhoudr
 * time : 2017/6/28 14:23
 */

public class Client {

    private ClientBootstrap client;

    private String serverIp;

    private int port;

    public Client(String serverIp, int port) {
        this.serverIp = serverIp;
        this.port = port;
        init();
    }

    public void init() {
        // no op
    }

    public void start() throws InterruptedException {
        this.client = ClientUtil.getClientBootstrap();
        this.client.setPipelineFactory(() -> {
            ChannelPipeline channelPipeline = Channels.pipeline();
            channelPipeline.addLast("encoder", new RequestEncoder());
            channelPipeline.addLast("docoder", new ResponseDecoder());
            channelPipeline.addLast("handler", new RequestClientHandler());
            return channelPipeline;
        });
        ChannelFuture channelFuture = this.client.connect(new InetSocketAddress(this.serverIp, this.port));
        channelFuture.getChannel().getCloseFuture().sync();
    }

    public void sendRequest(Request request) {
        this.client.getPipeline().getChannel().write(request);
    }

    public static void main(String[] args) {
        Client client = new Client("127.0.0.1", 61284);
        try {
            client.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

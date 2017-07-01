package io.vicp.frlib.nio.netty_3.fundamental.uptime;

import io.vicp.frlib.nio.netty_3.ClientUtil;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.timeout.ReadTimeoutHandler;
import org.jboss.netty.util.HashedWheelTimer;
import org.jboss.netty.util.Timer;

import java.net.InetSocketAddress;

/**
 * description : 设置读取超时handler，断链后实现重连
 * user : zhoudr
 * time : 2017/6/26 18:38
 */

public class Client {
    static final int RECONNECT_DELAY = Integer.valueOf(System.getProperty("reconnectDelay", "5"));

    private static final int READ_TIMEOUT = Integer.valueOf(System.getProperty("readTimeout", "10"));

    public static void main(String[] args) {
        ClientBootstrap client = ClientUtil.getClientBootstrap();
        Timer timer = new HashedWheelTimer();
        client.setPipelineFactory(() -> {
            final ChannelHandler timeoutHandler = new ReadTimeoutHandler(timer, READ_TIMEOUT);
            UptimeHandler uptimeHandler = new UptimeHandler(client, timer);
            return Channels.pipeline(timeoutHandler, uptimeHandler);
        });
        client.setOption("remoteAddress", new InetSocketAddress("127.0.0.1", 61284));
        client.connect();
    }
}

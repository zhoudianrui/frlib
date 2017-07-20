package io.vicp.frlib.nio.netty_3.fundamental.hearbeat;

import io.vicp.frlib.nio.netty_3.ServerUtil;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;
import org.jboss.netty.handler.timeout.IdleStateHandler;
import org.jboss.netty.util.HashedWheelTimer;
import org.jboss.netty.util.Timer;

/**
 * description :
 * user : zhoudr
 * time : 2017/7/3 17:14
 */

public class Server {
    public static void main(String[] args) {
        ServerUtil.startServer(() -> {
            ChannelPipeline channelPipeline = Channels.pipeline();
            Timer timer = new HashedWheelTimer();
            channelPipeline.addLast("idleState", new IdleStateHandler(timer, 5, 5, 10));
            channelPipeline.addLast("decoder", new StringDecoder());
            channelPipeline.addLast("encoder", new StringEncoder());
            channelPipeline.addLast("handler", new ServerHandler());
            return channelPipeline;}, 61284);

    }
}

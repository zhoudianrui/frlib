package io.vicp.frlib.nio.netty_3.binaryprotocol.factorial;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;

/**
 * description :
 * user : zhoudr
 * time : 2017/7/3 18:16
 */

public class FactorialServerPipelineFactory implements ChannelPipelineFactory{
    @Override
    public ChannelPipeline getPipeline() throws Exception {
        ChannelPipeline channelPipeline = Channels.pipeline();
        channelPipeline.addLast("frameDecoder", new NumberDecoder());
        channelPipeline.addLast("numberEncoder", new NumberEncoder());
        channelPipeline.addLast("handler", new FactorialServerHandler());
        return channelPipeline;
    }
}

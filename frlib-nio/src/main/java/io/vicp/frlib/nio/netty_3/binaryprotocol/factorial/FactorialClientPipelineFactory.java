package io.vicp.frlib.nio.netty_3.binaryprotocol.factorial;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;

/**
 * description :
 * user : zhoudr
 * time : 2017/7/3 18:29
 */

public class FactorialClientPipelineFactory implements ChannelPipelineFactory{
    @Override
    public ChannelPipeline getPipeline() throws Exception {
        ChannelPipeline channelPipeline = Channels.pipeline();
        channelPipeline.addLast("frameDecoder", new NumberDecoder());
        channelPipeline.addLast("encoder", new NumberEncoder());
        channelPipeline.addLast("handler", new FactorialClientHandler());
        return channelPipeline;
    }
}

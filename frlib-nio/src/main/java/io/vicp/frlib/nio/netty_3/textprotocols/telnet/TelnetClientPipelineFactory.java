package io.vicp.frlib.nio.netty_3.textprotocols.telnet;

import io.vicp.frlib.nio.netty_3.Constants;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.codec.frame.DelimiterBasedFrameDecoder;
import org.jboss.netty.handler.codec.frame.Delimiters;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

/**
 * description :
 * user : zhoudr
 * time : 2017/6/27 15:57
 */

public class TelnetClientPipelineFactory implements ChannelPipelineFactory {
    @Override
    public ChannelPipeline getPipeline() throws Exception {
        ChannelPipeline channelPipeline = Channels.pipeline();
        channelPipeline.addLast("frameDecoder", new DelimiterBasedFrameDecoder(Constants.FRAME_MAX_LENGTH, Delimiters.lineDelimiter()));
        channelPipeline.addLast("decoder", new StringDecoder());
        channelPipeline.addLast("encoder", new StringEncoder());
        channelPipeline.addLast("handler", new TelnetClientHandler());
        return channelPipeline;
    }
}

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
 * description : 服务端管道工厂
 * user : zhoudr
 * time : 2017/6/27 15:32
 */

public class TelnetServerPipelineFactory implements ChannelPipelineFactory {

    @Override
    public ChannelPipeline getPipeline() throws Exception {
        ChannelPipeline channelPipeline = Channels.pipeline();
        // 采用行分隔符作为帧的分隔符
        channelPipeline.addLast("frameDecoder", new DelimiterBasedFrameDecoder(Constants.FRAME_MAX_LENGTH, Delimiters.lineDelimiter()));
        channelPipeline.addLast("decoder", new StringDecoder());
        channelPipeline.addLast("encoder", new StringEncoder());
        channelPipeline.addLast("handler", new TelnetServerHandler());
        return channelPipeline;
    }
}

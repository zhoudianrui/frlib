package io.vicp.frlib.nio.netty_3.textprotocols.securechat;

import io.vicp.frlib.nio.netty_3.Constants;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.codec.frame.DelimiterBasedFrameDecoder;
import org.jboss.netty.handler.codec.frame.Delimiters;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;
import org.jboss.netty.handler.ssl.SslContext;

/**
 * description :
 * user : zhoudr
 * time : 2017/6/27 18:45
 */

public class SecureClientPipelineFactory implements ChannelPipelineFactory {

    private final SslContext sslContext;

    public SecureClientPipelineFactory(SslContext sslContext) {
        this.sslContext = sslContext;
    }

    @Override
    public ChannelPipeline getPipeline() throws Exception {
        ChannelPipeline channelPipeline = Channels.pipeline();
        channelPipeline.addLast("ssl", sslContext.newHandler("127.0.0.1", 61284));
        channelPipeline.addLast("frameDecoder", new DelimiterBasedFrameDecoder(Constants.FRAME_MAX_LENGTH, Delimiters.lineDelimiter()));
        channelPipeline.addLast("decoder", new StringDecoder());
        channelPipeline.addLast("encoder", new StringEncoder());
        channelPipeline.addLast("handler", new SecureClientHandler());
        return channelPipeline;
    }
}

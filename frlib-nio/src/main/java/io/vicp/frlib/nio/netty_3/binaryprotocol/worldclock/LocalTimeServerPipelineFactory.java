package io.vicp.frlib.nio.netty_3.binaryprotocol.worldclock;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.codec.protobuf.ProtobufDecoder;
import org.jboss.netty.handler.codec.protobuf.ProtobufEncoder;
import org.jboss.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import org.jboss.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

/**
 * description :
 * user : zhoudr
 * time : 2017/6/28 15:28
 */

public class LocalTimeServerPipelineFactory implements ChannelPipelineFactory {
    @Override
    public ChannelPipeline getPipeline() throws Exception {
        ChannelPipeline channelPipeline = Channels.pipeline();
        channelPipeline.addLast("frameDocoder", new ProtobufVarint32FrameDecoder());
        channelPipeline.addLast("protobufDecoder", new ProtobufDecoder(LocalTimeProtocol.Locations.getDefaultInstance()));
        channelPipeline.addLast("frameEncoder", new ProtobufVarint32LengthFieldPrepender());
        channelPipeline.addLast("protobufEncoder", new ProtobufEncoder());
        channelPipeline.addLast("handler", new LocalTimeServerHandler());
        return channelPipeline;
    }
}

package io.vicp.frlib.nio.netty_3.binaryprotocol.protobuf;

import io.vicp.frlib.nio.netty_3.binaryprotocol.protobuf.AddressBookProtocol.AddressBook;
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
 * time : 2017/6/29 18:29
 */

public class ProtobufServerPipelineFactory implements ChannelPipelineFactory{

    @Override
    public ChannelPipeline getPipeline() throws Exception {
        ChannelPipeline channelPipeline = Channels.pipeline();
        channelPipeline.addLast("frameDecoder", new ProtobufVarint32FrameDecoder());
        channelPipeline.addLast("protobufDecoder", new ProtobufDecoder(AddressBook.getDefaultInstance()));

        channelPipeline.addLast("frameLengthEncoder", new ProtobufVarint32LengthFieldPrepender());
        channelPipeline.addLast("protobufEncoder", new ProtobufEncoder());
        channelPipeline.addLast("handler", new ProtobufServerHandler());
        return channelPipeline;
    }
}

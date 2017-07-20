package io.vicp.frlib.nio.netty_3.binaryprotocol.factorial;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.CorruptedFrameException;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

import java.math.BigInteger;

/**
 * description :
 * user : zhoudr
 * time : 2017/7/3 18:06
 */

public class NumberDecoder extends FrameDecoder {

    @Override
    protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
        if (buffer.readableBytes() < 5) {
            return null;
        }
        buffer.markReaderIndex();
        int magicNumber = buffer.readUnsignedByte();
        if (magicNumber != 'Z') {
            buffer.resetReaderIndex();
            throw  new CorruptedFrameException("Invalid magic number:" + magicNumber);
        }
        int dataLength = buffer.readInt();
        if (buffer.readableBytes() < dataLength) {
            buffer.resetReaderIndex();
            return null;
        }
        byte[] data = new byte[dataLength];
        buffer.readBytes(data);
        return new BigInteger(data);
    }
}

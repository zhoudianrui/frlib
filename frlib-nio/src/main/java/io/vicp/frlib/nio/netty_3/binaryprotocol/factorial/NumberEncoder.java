package io.vicp.frlib.nio.netty_3.binaryprotocol.factorial;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

import java.math.BigInteger;

/**
 * description :
 * user : zhoudr
 * time : 2017/7/3 18:01
 */

public class NumberEncoder extends OneToOneEncoder {
    @Override
    protected Object encode(ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception {
        if (!(msg instanceof Number)) {
            return msg;
        }
        BigInteger value;
        if (msg instanceof BigInteger) {
            value = (BigInteger)msg;
        } else {
            value = new BigInteger(String.valueOf(msg));
        }
        byte[] data = value.toByteArray();
        int dataLength = data.length;
        ChannelBuffer channelBuffer = ChannelBuffers.dynamicBuffer();
        channelBuffer.writeByte((byte)'Z'); // magic number
        channelBuffer.writeInt(dataLength);
        channelBuffer.writeBytes(data);
        return channelBuffer;
    }
}

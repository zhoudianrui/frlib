package io.vicp.frlib.nio.netty_3.serializer;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

/**
 * <p>注释<p>
 *
 * @author zhoudr
 * @version $Id: BufferFactory, v 0.1 2017/7/14 21:01 zhoudr Exp $$
 */

public class BufferFactory {

    public static ChannelBuffer getBuffer() {
        ChannelBuffer channelBuffer = ChannelBuffers.dynamicBuffer();
        return channelBuffer;
    }

    public static ChannelBuffer getBuffer(byte[] bytes) {
        ChannelBuffer copiedBuffer = ChannelBuffers.wrappedBuffer(bytes);
        return copiedBuffer;
    }
}

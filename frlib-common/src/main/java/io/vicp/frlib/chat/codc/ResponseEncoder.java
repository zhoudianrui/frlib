package io.vicp.frlib.chat.codc;

import io.vicp.frlib.chat.module.Response;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

/**
 * <p>响应编码器<p>
 *--------------------------------------------------------------------------------
 *|            |             |             |              |             |        |
 *|--head(4)-- |--module(2)--|--status(2)--|--command(2)--|--length(2)--|--data--|
 *|            |             |             |              |             |        |
 *--------------------------------------------------------------------------------
 * @author zhoudr
 * @version $Id: ResponseEncoder, v 0.1 2017/7/29 16:26 zhoudr Exp $$
 */

public class ResponseEncoder extends OneToOneEncoder{
    @Override
    protected Object encode(ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception {
        Response response = (Response)msg;
        ChannelBuffer channelBuffer = ChannelBuffers.dynamicBuffer();
        channelBuffer.writeInt(Constants.MAGIC_HEAD);
        channelBuffer.writeShort(response.getModule());
        channelBuffer.writeShort(response.getCommand());
        channelBuffer.writeShort(response.getResultCode());
        channelBuffer.writeShort(response.getDataLength());
        channelBuffer.writeBytes(response.getData());
        return channelBuffer;
    }
}

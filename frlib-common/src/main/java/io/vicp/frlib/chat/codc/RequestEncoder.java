package io.vicp.frlib.chat.codc;

import io.vicp.frlib.chat.module.Request;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

/**
 * <p>请求编码器<p>
 *
 *------------------------------------------------------------------
 *|            |             |              |             |        |
 *|--head(4)-- |--module(2)--|--command(2)--|--length(2)--|--data--|
 *|            |             |              |             |        |
 *------------------------------------------------------------------
 * @author zhoudr
 * @version $Id: RequestEncoder, v 0.1 2017/7/29 16:19 zhoudr Exp $$
 */

public class RequestEncoder extends OneToOneEncoder{

    @Override
    protected Object encode(ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception {
        Request request = (Request)msg;
        ChannelBuffer channelBuffer = ChannelBuffers.dynamicBuffer();
        channelBuffer.writeInt(Constants.MAGIC_HEAD); // 头部
        channelBuffer.writeShort(request.getModule());// 模块
        channelBuffer.writeShort(request.getCommand());// 命令
        channelBuffer.writeShort(request.getDataLength()); // 数据长度
        channelBuffer.writeBytes(request.getData());
        return channelBuffer;
    }
}

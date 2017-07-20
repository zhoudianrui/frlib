package io.vicp.frlib.nio.netty_3.customprotocol.code;

import io.vicp.frlib.nio.netty_3.Constants;
import io.vicp.frlib.nio.netty_3.customprotocol.entity.Response;
import io.vicp.frlib.nio.netty_3.serializer.BufferFactory;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

/**
 * <p>响应编码器<p>
 *
 * @author zhoudr
 * @version $Id: ResponseEncoder, v 0.1 2017/7/15 17:49 zhoudr Exp $$
 */

public class ResponseEncoder extends OneToOneEncoder {
    @Override
    protected Object encode(ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception {
        Response response = (Response) msg;
        ChannelBuffer channelBuffer = BufferFactory.getBuffer();
        channelBuffer.writeInt(Constants.MESSAGE_HEADER); // 报文头
        channelBuffer.writeShort(response.getModule()); // 模块
        channelBuffer.writeShort(response.getCommand()); // 命令
        channelBuffer.writeShort(response.getStatusCode()); // 状态码
        int dataLength = response.getDataLength();
        channelBuffer.writeInt(dataLength);
        if (response.getData() != null) {
            channelBuffer.writeBytes(response.getData());
        }
        return channelBuffer;
    }
}

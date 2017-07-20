package io.vicp.frlib.nio.netty_3.customprotocol.code;

import io.vicp.frlib.nio.netty_3.Constants;
import io.vicp.frlib.nio.netty_3.customprotocol.entity.Request;
import io.vicp.frlib.nio.netty_3.serializer.BufferFactory;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

/**
 * <p>请求编码器<p>
 * 数据包格式
 * +——----——+——-----——+——----——+——----——+——-----——+
 * | 包头   | 模块号   | 命令号 |  长度   |   数据  |
 * +——----——+——-----——+——----——+——----——+——-----——+
 * @author zhoudr
 * @version $Id: RequestEncoder, v 0.1 2017/7/15 16:38 zhoudr Exp $$
 */

public class RequestEncoder extends OneToOneEncoder {
    @Override
    protected Object encode(ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception {
        Request request = (Request) msg;
        ChannelBuffer channelBuffer = BufferFactory.getBuffer();
        channelBuffer.writeInt(Constants.MESSAGE_HEADER); // 报文头
        channelBuffer.writeShort(request.getModule()); // 模块
        channelBuffer.writeShort(request.getCommand()); // 命令
        int dataLength = request.getDataLength();
        channelBuffer.writeInt(dataLength);
        if (request.getData() != null) {
            channelBuffer.writeBytes(request.getData());
        }
        return channelBuffer;
    }
}

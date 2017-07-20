package io.vicp.frlib.nio.netty_3.customprotocol.code;

import io.vicp.frlib.nio.netty_3.Constants;
import io.vicp.frlib.nio.netty_3.customprotocol.entity.Response;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

/**
 * <p>响应解码器<p>
 *数据包格式
 * +——----——+——-----——+——----——+——----——+——-----——+——-----——+
 * | 包头   | 模块号   | 命令号 |  状态码 |  长度   |   数据   |
 * +——----——+——-----——+——----——+——----——+——-----——+——-----——+
 * @author zhoudr
 * @version $Id: ResponseDecoder, v 0.1 2017/7/15 17:42 zhoudr Exp $$
 */

public class ResponseDecoder extends FrameDecoder {
    private static int BASE_LENGTH = 4 + 2 + 2 + 2 + 4;

    @Override
    protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
        int readableBytes = buffer.readableBytes();
        if (readableBytes < BASE_LENGTH) {
            return null;
        }
        // 防止socket字节流攻击
        if (readableBytes > 2048) {
            buffer.skipBytes(buffer.readableBytes());
        }
        // 记录包头开始的index
        int beginReader;
        while (true) {
            beginReader = buffer.readerIndex();
            buffer.markReaderIndex();
            int messageHeader = buffer.readInt();
            if (messageHeader == Constants.MESSAGE_HEADER) {
                break;
            }
            // 未读到包头，略过一个字节
            buffer.resetReaderIndex();
            buffer.readByte();

            // 长度又不满足时
            if (buffer.readableBytes() < BASE_LENGTH) {
                return null;
            }
        }

        // 模块
        short module = buffer.readShort();
        // 命令
        short command = buffer.readShort();
        // 状态码
        short statusCode = buffer.readShort();
        // 数据长度
        int dataLength = buffer.readInt();
        byte[] data = new byte[dataLength];
        int leftBytes = buffer.readableBytes();
        if (leftBytes < dataLength) {
            buffer.readerIndex(beginReader);
            return null;
        }
        buffer.readBytes(data);
        Response response = new Response();
        response.setModule(module);
        response.setCommand(command);
        response.setStatusCode(statusCode);
        response.setData(data);
        return response;
    }
}

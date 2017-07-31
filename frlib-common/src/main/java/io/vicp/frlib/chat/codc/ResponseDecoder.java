package io.vicp.frlib.chat.codc;

import io.vicp.frlib.chat.module.Response;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

/**
 * <p>响应解码器<p>
 *--------------------------------------------------------------------------------
 *|            |             |             |              |             |        |
 *|--head(4)-- |--module(2)--|--status(2)--|--command(2)--|--length(2)--|--data--|
 *|            |             |             |              |             |        |
 *--------------------------------------------------------------------------------
 * @author zhoudr
 * @version $Id: ResponseDecoder, v 0.1 2017/7/31 15:00 zhoudr Exp $$
 */

public class ResponseDecoder extends FrameDecoder{

    private static final int RESPONSE_LEAST_PACKAGE_LENGTH = 12;

    @Override
    protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
        // 判断目前可读取的字节大小
        int readableBytes = buffer.readableBytes();
        if (RESPONSE_LEAST_PACKAGE_LENGTH < readableBytes) {
            // 防止socket流攻击
            if (readableBytes > Constants.LONGEST_PACKAGE_LENGTH) {
                buffer.skipBytes(Constants.LONGEST_PACKAGE_LENGTH);
            }
            int readerIndex;
            while (true) {
                readerIndex = buffer.readerIndex(); // 读取开始位置
                buffer.markReaderIndex();// 标记读取位置，用于恢复功能
                int head = buffer.readInt();
                if (Constants.MAGIC_HEAD == head) {
                    break;
                }
                buffer.resetReaderIndex();// 恢复读取位置，防止数据丢失

                // 跳过1个字节，因为有可能因为防止socket攻击跳过最大字节后，数据的开始不是head
                // 也有可能原数据的发送格式与所要求的本就不同
                buffer.skipBytes(1);
                readableBytes = buffer.readableBytes();
                if (RESPONSE_LEAST_PACKAGE_LENGTH > readableBytes) {
                    return null;
                }
            }
            short module = buffer.readShort(); // 模块
            short command = buffer.readShort(); // 命令
            short resultCode = buffer.readShort(); // 状态响应码
            short dataLength = buffer.readShort(); // 数据长度
            readableBytes = buffer.readableBytes();
            if (readableBytes < dataLength) {
                buffer.readerIndex(readerIndex); // 恢复读取位置
                return null;
            }
            byte[] data = new byte[dataLength];
            buffer.readBytes(data);
            Response response = new Response();
            response.setModule(module);
            response.setCommand(command);
            response.setResultCode(resultCode);
            response.setData(data);
            return response;
        }
        return null;
    }
}

package io.vicp.frlib.nio.netty_3.customprotocol.server;

import io.vicp.frlib.nio.netty_3.Constants;
import io.vicp.frlib.nio.netty_3.customprotocol.entity.Player;
import io.vicp.frlib.nio.netty_3.customprotocol.entity.Request;
import io.vicp.frlib.nio.netty_3.customprotocol.entity.Response;
import io.vicp.frlib.nio.netty_3.serializer.BufferFactory;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import java.util.Date;

/**
 * <p>注释<p>
 *
 * @author zhoudr
 * @version $Id: RequestServerHandler, v 0.1 2017/7/15 17:28 zhoudr Exp $$
 */

public class RequestServerHandler extends SimpleChannelHandler {
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        Request request = (Request) e.getMessage();
        if (request.getModule() == 10 && request.getCommand() == 2) {
            byte[] data = request.getData();
            Player player = new Player();
            player.readFromBytes(data);
            System.out.println("玩家信息:" + player);

            Response response = new Response();
            response.setModule((short)1);
            response.setCommand((short)2);
            response.setStatusCode(Constants.STATUS_SUCCESS);
            long time = new Date().getTime();
            byte[] timeData = new byte[8];
            ChannelBuffer channelBuffer = BufferFactory.getBuffer();
            channelBuffer.writeLong(time);
            channelBuffer.readBytes(timeData);
            response.setData(timeData);
            ctx.getChannel().write(response);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        e.getCause().printStackTrace();
        e.getChannel().close();
    }
}

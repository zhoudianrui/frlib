package io.vicp.frlib.nio.netty_3.customprotocol.client;

import io.vicp.frlib.nio.netty_3.Constants;
import io.vicp.frlib.nio.netty_3.customprotocol.entity.Player;
import io.vicp.frlib.nio.netty_3.customprotocol.entity.Request;
import io.vicp.frlib.nio.netty_3.customprotocol.entity.Response;
import io.vicp.frlib.nio.netty_3.serializer.BufferFactory;
import io.vicp.frlib.util.DateUtil;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * <p>注释<p>
 *
 * @author zhoudr
 * @version $Id: RequestClientHandler, v 0.1 2017/7/15 17:08 zhoudr Exp $$
 */

public class RequestClientHandler extends SimpleChannelHandler {

    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        Request request = new Request();
        request.setModule((short)10);
        request.setCommand((short)2);
        Player player = new Player();
        player.setId(100000);
        player.setAliasName("无敌是多么寂寞");
        player.setSkills(new ArrayList<>(Arrays.asList(23, 111)));
        request.setData(player.getBytes());
        ctx.getChannel().write(request);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        e.getCause().printStackTrace();
        e.getChannel().close();
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        Response response = (Response)e.getMessage();
        if(response.getStatusCode() == Constants.STATUS_SUCCESS) {
            byte[] data = response.getData();
            ChannelBuffer reader = BufferFactory.getBuffer(data);
            Date syncTime = new Date(reader.readLong());// 同步时间
            System.out.println("server time:" + DateUtil.converDateToString(syncTime, null));
        }
        System.out.println();
    }
}

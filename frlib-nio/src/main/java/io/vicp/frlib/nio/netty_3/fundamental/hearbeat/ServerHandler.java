package io.vicp.frlib.nio.netty_3.fundamental.hearbeat;

import io.vicp.frlib.util.DateUtil;
import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.handler.timeout.IdleState;
import org.jboss.netty.handler.timeout.IdleStateEvent;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * description :
 * user : zhoudr
 * time : 2017/7/3 17:39
 */

public class ServerHandler extends SimpleChannelHandler{
    @Override
    public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e) throws Exception {
        if (e instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent)e;
            System.out.println(event.getState() + " @ " + DateUtil.converDateToString(new Date(), new SimpleDateFormat("ss")));
            // 踢玩家下线
            if (event.getState() == IdleState.ALL_IDLE) {
                e.getChannel().write("long time with no any action, you will lose connection");
                e.getChannel().close();
            }
        }
        super.handleUpstream(ctx, e);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        e.getCause().printStackTrace();
        e.getChannel().close();
    }
}

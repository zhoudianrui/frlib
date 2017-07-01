package io.vicp.frlib.nio.netty_3.binaryprotocol.objectecho;

import org.jboss.netty.channel.*;

import java.util.concurrent.atomic.AtomicLong;

/**
 * description : 二进制对象服务端处理器
 * user : zhoudr
 * time : 2017/6/28 14:20
 */

public class ObjectEchoServerHandler extends SimpleChannelUpstreamHandler {
    private AtomicLong transferMessages = new AtomicLong(0);

    @Override
    public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e) throws Exception {
        if (e instanceof ChannelStateEvent) {
            System.out.println(e);
        }
        super.handleUpstream(ctx, e);
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        long times = transferMessages.incrementAndGet();
        Object message = e.getMessage();
        System.out.println("第" + times  + "次收到" + message);

        if (message instanceof User) {
            User user = (User) message;
            System.out.println(user.getId());
        }
        e.getChannel().write(message);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        e.getCause().printStackTrace();
        e.getChannel().close();
    }
}

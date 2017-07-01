package io.vicp.frlib.nio.netty_3.fundamental.basic;

import org.jboss.netty.channel.*;

/**
 * Created by zhoudr on 2017/6/13.
 */
public class HelloHandler extends SimpleChannelHandler{
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        System.out.println("receiveMessage");
        String receiveMessage = (String)e.getMessage();
        System.out.println(receiveMessage);
        ctx.getChannel().write("Hi");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        System.out.println("exceptionCaught");
    }

    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        System.out.println("channelConnected");
    }

    @Override
    public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        System.out.println("channelDisconnected");
    }

    @Override
    public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        System.out.println("channelClosed");
    }
}

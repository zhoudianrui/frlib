package io.vicp.frlib.nio.netty_5;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * description :
 * user : zhoudr
 * time : 2017/6/26 15:13
 */

public class ServerHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void messageReceived(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("收到:" + msg);
        ctx.writeAndFlush("Hi");
    }
}

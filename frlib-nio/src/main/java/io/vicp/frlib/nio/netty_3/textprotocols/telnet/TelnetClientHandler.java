package io.vicp.frlib.nio.netty_3.textprotocols.telnet;

import org.jboss.netty.channel.*;

/**
 * description :
 * user : zhoudr
 * time : 2017/6/27 16:04
 */

public class TelnetClientHandler extends SimpleChannelUpstreamHandler {
    @Override
    public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e) throws Exception {
        if (e instanceof ChannelStateEvent) {
            System.err.println(e);
        }
        super.handleUpstream(ctx, e);
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        System.err.println(e.getMessage());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        e.getCause().printStackTrace();
        e.getChannel().close();
    }
}

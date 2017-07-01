package io.vicp.frlib.nio.netty_3.textprotocols.securechat;

import org.jboss.netty.channel.*;

/**
 * description :
 * user : zhoudrl
 * time : 2017/6/27 18:55
 */

public class SecureClientHandler extends SimpleChannelUpstreamHandler {
    @Override
    public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e) throws Exception {
        if (e instanceof ChannelStateEvent) {
            System.err.println(e);
        }
        super.handleUpstream(ctx, e);
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        System.out.println(e.getMessage());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        e.getCause().printStackTrace();
        e.getChannel().close();
    }
}

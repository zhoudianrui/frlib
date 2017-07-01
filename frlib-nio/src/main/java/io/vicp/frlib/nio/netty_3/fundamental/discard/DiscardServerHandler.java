package io.vicp.frlib.nio.netty_3.fundamental.discard;

import org.jboss.netty.channel.*;

/**
 * description :
 * user : zhoudr
 * time : 2017/6/26 17:34
 */

public class DiscardServerHandler extends SimpleChannelUpstreamHandler {
    private long transferredBytes;

    @Override
    public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e) throws Exception {
        if (e instanceof ChannelStateEvent) {
            System.err.println("server:" + e);
        }
        super.handleUpstream(ctx, e);
    }


    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        transferredBytes += e.getMessage().toString().getBytes().length;
        System.out.println("receice " + transferredBytes + " bytes");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        e.getCause().printStackTrace();
        e.getChannel().close();
    }
}

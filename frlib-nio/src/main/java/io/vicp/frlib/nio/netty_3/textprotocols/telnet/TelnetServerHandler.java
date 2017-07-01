package io.vicp.frlib.nio.netty_3.textprotocols.telnet;

import io.vicp.frlib.nio.netty_3.Constants;
import org.jboss.netty.channel.*;

/**
 * description :
 * user : zhoudr
 * time : 2017/6/27 15:41
 */

public class TelnetServerHandler extends SimpleChannelHandler {

    @Override
    public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e) throws Exception {
        if (e instanceof ChannelStateEvent) {
            System.out.println(e);
        }
        super.handleUpstream(ctx, e);
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        final String tailAppend = Constants.LINE_DELIMITER;
        String request = (String) e.getMessage();
        boolean close = false;

        String response;
        if (request.length() == 0) {
            response = "Please tpye something" + tailAppend;
        } else if ("bye".equalsIgnoreCase(request)) {
            response = "Have a good day!" + tailAppend;
            close = true;
        } else {
            response = "Did you say '" + request + "'?" + tailAppend;
        }
        ChannelFuture channelFuture = e.getChannel().write(response);
        if (close) {
            channelFuture.addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        e.getCause().printStackTrace();
        e.getChannel().close();
    }
}

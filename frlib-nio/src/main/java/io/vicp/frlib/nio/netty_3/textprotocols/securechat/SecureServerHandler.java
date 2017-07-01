package io.vicp.frlib.nio.netty_3.textprotocols.securechat;

import io.vicp.frlib.nio.netty_3.Constants;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.handler.ssl.SslHandler;

import java.net.InetAddress;

/**
 * description :
 * user : zhoudr
 * time : 2017/6/27 18:21
 */

public class SecureServerHandler extends SimpleChannelUpstreamHandler {
    private static final ChannelGroup channels = new DefaultChannelGroup();

    @Override
    public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e) throws Exception {
        if (e instanceof ChannelStateEvent) {
            System.err.println(e);
        }
        super.handleUpstream(ctx, e);
    }

    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        final SslHandler sslHandler = ctx.getPipeline().get(SslHandler.class);
        ChannelFuture channelFuture = sslHandler.handshake();
        channelFuture.addListener(new Greeter(sslHandler));
    }

    @Override
    public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        channels.remove(e.getChannel());
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        final Channel channel = e.getChannel();
        String request = (String) e.getMessage();
        for (Channel c : channels) {
            if (c != channel) {
                c.write("[" + channel.getRemoteAddress() + "]" + request + Constants.LINE_DELIMITER);
            } else {
                c.write("[you] " + request + Constants.LINE_DELIMITER);
            }
        }
        if ("bye".equalsIgnoreCase(request)) {
            channel.close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        e.getCause().printStackTrace();
        e.getChannel().close();
    }

    private static class Greeter implements ChannelFutureListener{
        final SslHandler sslHandler;
        public Greeter(SslHandler sslHandler) {
            this.sslHandler = sslHandler;
        }

        @Override
        public void operationComplete(ChannelFuture future) throws Exception {
            if (future.isSuccess()) {
                final Channel channel = future.getChannel();
                String welcome = "Welcome to " + InetAddress.getLocalHost().getHostName() + " secure chat server!" + Constants.LINE_DELIMITER;
                channel.write(welcome);
                String alterMessage = "Your session is protected by " + sslHandler.getEngine().getSession().getCipherSuite() + " cipher suite." +
                        Constants.LINE_DELIMITER;
                channel.write(alterMessage);
                channels.add(channel);
            } else {
                future.getChannel().close();
            }
        }
    }
}

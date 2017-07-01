package io.vicp.frlib.nio.netty_3.fundamental.uptime;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.handler.timeout.ReadTimeoutException;
import org.jboss.netty.util.Timeout;
import org.jboss.netty.util.Timer;

import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * description :
 * user : zhoudr
 * time : 2017/6/26 18:39
 */

public class UptimeHandler extends SimpleChannelHandler {
    private ClientBootstrap clientBootstrap;
    private Timer timer;
    private long startTime = -1;

    public UptimeHandler(ClientBootstrap clientBootstrap, Timer timer) {
        this.clientBootstrap = clientBootstrap;
        this.timer = timer;
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        super.messageReceived(ctx, e);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        Throwable cause = e.getCause();
        if (cause instanceof ConnectException) {
            startTime = -1;
            println("fail to connect to " + getRemoteAddress());
        }
        if (cause instanceof ReadTimeoutException) {
            println("Disconnecting due to no inbound traffic");
        } else {
            cause.printStackTrace();
        }
        // 存在异常关闭channel
        ctx.getChannel().close();
    }

    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        if (startTime < 0) {
            startTime = System.currentTimeMillis();
        }
        println("connect to " + getRemoteAddress());
    }

    /**
     * 已经建立的链接断开时触发的动作
     *
     * @param ctx
     * @param e
     * @throws Exception
     */
    @Override
    public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        println("Disconnected from " + getRemoteAddress());
    }

    /**
     * 未建立链接断开时触发的动作
     *
     * @param ctx
     * @param e
     * @throws Exception
     */
    @Override
    public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        println("sleep " + Client.RECONNECT_DELAY + " s");
        timer.newTimeout((Timeout timeout) -> {
            println("reconnect to " + getRemoteAddress());
            clientBootstrap.connect();
        }, Client.RECONNECT_DELAY, TimeUnit.SECONDS);
    }

    private void println(String msg) {
        if (startTime < 0) {
            System.err.format("[SERVER IS DOWN] %s%n", msg);
        } else {
            long interval = System.currentTimeMillis() - startTime;
            System.err.format("[UPTIME: %5ds] %s%n", interval / 1000, msg);
        }
    }

    private InetSocketAddress getRemoteAddress() {
        return (InetSocketAddress) clientBootstrap.getOption("remoteAddress");
    }
}

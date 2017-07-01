package io.vicp.frlib.nio.netty_3.fundamental.discard;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.*;

/**
 * description :
 * user : zhoudr
 * time : 2017/6/26 17:55
 */

public class DiscardClientHandler extends SimpleChannelUpstreamHandler {
    private final byte[] content;
    private long transferredBytes;

    public DiscardClientHandler() {
        content = new byte[DiscardClient.SIZE];
    }

    @Override
    public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e) throws Exception {
        if (e instanceof ChannelStateEvent) {
            if (((ChannelStateEvent) e).getState() != ChannelState.INTEREST_OPS) {
                System.err.println("client" + e);
            }
        }
        super.handleUpstream(ctx, e);
    }

    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        generateTraffic(e);
    }

    @Override
    public void channelInterestChanged(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        generateTraffic(e);
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {

    }

    @Override
    public void writeComplete(ChannelHandlerContext ctx, WriteCompletionEvent e) throws Exception {
        transferredBytes += e.getWrittenAmount();
        System.out.println("client write " + transferredBytes + " bytes");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        e.getCause().printStackTrace();
        e.getChannel().close();
    }

    private void generateTraffic(ChannelStateEvent event) {
        Channel channel = event.getChannel();
        while (channel.isWritable()) {
            ChannelBuffer msg = nextMessage();
            if (msg == null) {
                break;
            }
            channel.write(msg);
        }
    }

    private ChannelBuffer nextMessage() {
        return ChannelBuffers.wrappedBuffer(content);
    }
}

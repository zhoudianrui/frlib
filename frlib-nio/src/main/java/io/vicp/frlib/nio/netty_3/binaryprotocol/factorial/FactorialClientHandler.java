package io.vicp.frlib.nio.netty_3.binaryprotocol.factorial;

import org.jboss.netty.channel.*;

import java.math.BigInteger;

/**
 * description :
 * user : zhoudr
 * time : 2017/7/3 18:31
 */

public class FactorialClientHandler extends SimpleChannelHandler {
    private int i = 1;
    private int receivedMessages;
    /*final BlockingQueue<BigInteger> answer = new LinkedBlockingQueue<>();

    public BigInteger getFactorial() {
        boolean interrupted = false;
        for (;;) {
            try {
                return answer.take();
            } catch (InterruptedException e) {
                interrupted = true;
            } finally {
                if(interrupted) {
                    Thread.interrupted();
                }
            }
        }
    }*/

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        receivedMessages++;
        if (receivedMessages <= FactorialClient.COUNT) {
            BigInteger value = (BigInteger) e.getMessage();
            System.out.format("%2d! = %d\n", receivedMessages, value);
            if (receivedMessages == FactorialClient.COUNT) {
                e.getChannel().close();
            }
        } else {
            e.getChannel().close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        e.getCause().printStackTrace();
        e.getChannel().close();
    }

    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        sendNumbers(e);
    }

    /*@Override
    public void channelInterestChanged(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        sendNumbers(e);
    }*/

    private void sendNumbers(ChannelStateEvent e) {
        Channel channel = e.getChannel();
        while (channel.isWritable()) {
            if (i <= FactorialClient.COUNT) {
                channel.write(i++);
            } else {
                break;
            }
        }
    }
}

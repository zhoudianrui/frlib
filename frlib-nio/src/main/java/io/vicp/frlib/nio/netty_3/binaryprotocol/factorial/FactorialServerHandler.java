package io.vicp.frlib.nio.netty_3.binaryprotocol.factorial;

import org.jboss.netty.channel.*;

import java.math.BigInteger;
import java.util.Formatter;

/**
 * description :
 * user : zhoudr
 * time : 2017/7/3 18:18
 */

public class FactorialServerHandler extends SimpleChannelHandler {
    private int lastMultiplier = 1;
    private BigInteger factorial = new BigInteger(new byte[]{1});

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        BigInteger number;
        if (e.getMessage() instanceof BigInteger) {
            number = (BigInteger) e.getMessage();
        } else {
            number = new BigInteger(String.valueOf(e.getMessage().toString()));
        }
        lastMultiplier = number.intValue();
        factorial = factorial.multiply(number); // 保留上一次计算结果
        e.getChannel().write(factorial);
    }

    @Override
    public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        System.out.println(new Formatter().format("Factorial of %d is: %d", lastMultiplier, factorial));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        e.getCause().printStackTrace();
        e.getChannel().close();
    }
}

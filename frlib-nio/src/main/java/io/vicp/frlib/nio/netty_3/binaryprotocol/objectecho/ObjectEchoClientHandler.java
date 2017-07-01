package io.vicp.frlib.nio.netty_3.binaryprotocol.objectecho;

import org.apache.commons.collections.CollectionUtils;
import org.jboss.netty.channel.*;

import java.util.ArrayList;
import java.util.List;

/**
 * description :
 * user : zhoudr
 * time : 2017/6/28 14:28
 */

public class ObjectEchoClientHandler extends SimpleChannelUpstreamHandler {
    private List<Integer> messages = new ArrayList<>();

    public ObjectEchoClientHandler(int size) {
        if (size <=0) {
            throw new IllegalArgumentException("size can not be smaller than 0");
        }
        for (int i = 0; i < size; i++) {
            messages.add(i);
        }
    }

    @Override
    public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e) throws Exception {
        if (e instanceof ChannelStateEvent) {
            System.out.println(e);
        }
        super.handleUpstream(ctx, e);
    }

    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        if (CollectionUtils.isNotEmpty(messages)) {
            messages.forEach(message -> e.getChannel().write(message));
        }
        // 最后发送一个普通对象
        User user = new User();
        user.setId(1);
        user.setName("张三");
        user.setAge(30);
        e.getChannel().write(user);
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        System.out.println("client收到:" + e.getMessage());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        e.getCause().printStackTrace();
        e.getChannel().close();
    }
}

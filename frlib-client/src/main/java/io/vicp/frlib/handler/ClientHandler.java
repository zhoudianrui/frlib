package io.vicp.frlib.handler;

import io.vicp.frlib.chat.module.CommandEnum;
import io.vicp.frlib.chat.module.ModuleEnum;
import io.vicp.frlib.chat.module.Player;
import io.vicp.frlib.chat.module.Request;
import io.vicp.frlib.chat.module.Response;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

/**
 * <p>注释<p>
 *
 * @author zhoudr
 * @version $Id: ClientHandler, v 0.1 2017/7/31 15:47 zhoudr Exp $$
 */

public class ClientHandler extends SimpleChannelHandler{
    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        Request request = new Request();
        Player player = new Player();
        player.setName("张三");
        request.setModule(ModuleEnum.PLAYER.getValue());
        request.setCommand(CommandEnum.REGISTER.getValue());
        request.setData(player.getBytes());
        ctx.getChannel().write(request);
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        Response response = (Response) e.getMessage();
        byte[] data = response.getData();
        if (data != null) {
            Player player = new Player();
            player.readFromBytes(data);
            System.out.println(player);
        } else {
            System.err.println("null data");
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        e.getCause().printStackTrace();
        e.getChannel().close();
    }
}

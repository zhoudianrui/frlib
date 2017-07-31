package io.vicp.frlib.handler;

import io.vicp.frlib.chat.module.Request;
import io.vicp.frlib.chat.module.Response;
import io.vicp.frlib.chat.module.Result;
import io.vicp.frlib.chat.module.ResultCode;
import io.vicp.frlib.router.Invoker;
import io.vicp.frlib.router.InvokerHolder;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

/**
 * <p>服务处理器<p>
 *
 * @author zhoudr
 * @version $Id: ServerHandler, v 0.1 2017/7/29 16:27 zhoudr Exp $$
 */

public class ServerHandler extends SimpleChannelHandler {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        e.getCause().printStackTrace();
        e.getChannel().close();
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        Request request = (Request)e.getMessage();
        handle(ctx.getChannel(), request);
    }

    private void handle(Channel channel, Request request) {
        Invoker invoker = InvokerHolder.get(request.getModule(), request.getCommand());
        if (invoker != null) {
            Response response = new Response(request);
            try {
                byte[] data = request.getData();
                Result<?> result = (Result<?>)invoker.invoke(channel, data);
                if (ResultCode.SUCCESS == result.getResultCode()) {
                    response.setResultCode(ResultCode.SUCCESS);
                    response.setData(result.getContent().getBytes());
                }
            } catch (Exception e) {
                response.setResultCode(ResultCode.FAILED);
                e.printStackTrace();
            }
            channel.write(response);
        }
    }
}

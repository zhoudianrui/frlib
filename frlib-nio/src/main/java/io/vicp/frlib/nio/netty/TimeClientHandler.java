package io.vicp.frlib.nio.netty;

/**
 * Created by zhoudr on 2017/3/10.
 */
/*public class TimeClientHandler extends ChannelHandlerAdapter {

    private final ByteBuf firstMessage;

    private final ByteBuf lastMessage;

    public TimeClientHandler() {
        byte[] req = "QUERY TIME ORDER".getBytes();
        firstMessage = Unpooled.buffer(req.length);
        firstMessage.writeBytes(req);

        byte[] lastReq = "GOODBYE".getBytes();
        lastMessage = Unpooled.buffer(lastReq.length);
        lastMessage.writeBytes(lastReq);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(firstMessage);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf)msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body = new String(req, "UTF-8");
        if ("GOODBYE".equalsIgnoreCase(body)) {
            System.out.println("receive goodbye from server");
            ctx.close();
        } else {
            System.out.println("Now is : " + body);
            ctx.writeAndFlush(lastMessage);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("Unexpected exception from downstream : " + cause.getMessage());
        ctx.close();
    }
}*/

package io.vicp.frlib.nio.netty;

/**
 * Created by zhoudr on 2017/3/10.
 */
/*public class TimeServerHandler extends ChannelHandlerAdapter{

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf)msg;
        byte[] req = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(req);
        String body = new String(req, "UTF-8");
        System.out.println("The server receive order : " + body);
        if ("QUERY TIME ORDER".equalsIgnoreCase(body)) {
            String currentTime = new Date().toString();
            ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
            ctx.write(resp);
        } else if ("GOODBYE".equalsIgnoreCase(body)){
            String goodBye = "GOODBYE";
            ByteBuf resp = Unpooled.copiedBuffer(goodBye.getBytes());
            ctx.write(resp);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}*/

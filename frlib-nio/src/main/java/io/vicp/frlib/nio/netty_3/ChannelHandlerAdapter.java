package io.vicp.frlib.nio.netty_3;

import lombok.Data;
import org.jboss.netty.channel.ChannelHandler;

/**
 * description : 包含名字的handler
 * user : zhoudr
 * time : 2017/6/26 17:24
 */
@Data
public class ChannelHandlerAdapter {
    private String name;
    private ChannelHandler channelHandler;

    public ChannelHandlerAdapter(String name, ChannelHandler channelHandler) {
        this.name = name;
        this.channelHandler = channelHandler;
    }
}

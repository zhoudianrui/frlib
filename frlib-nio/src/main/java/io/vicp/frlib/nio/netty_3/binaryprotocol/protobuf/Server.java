package io.vicp.frlib.nio.netty_3.binaryprotocol.protobuf;

import io.vicp.frlib.nio.netty_3.ServerUtil;
import org.jboss.netty.channel.ChannelPipelineFactory;

/**
 * description : 采用protobuf获取包含列表的对象
 * user : zhoudr
 * time : 2017/6/29 18:18
 */

public class Server {
    public static void main(String[] args) {
        ChannelPipelineFactory pipelineFactory = new ProtobufServerPipelineFactory();
        ServerUtil.startServer(pipelineFactory, 61284);
    }
}

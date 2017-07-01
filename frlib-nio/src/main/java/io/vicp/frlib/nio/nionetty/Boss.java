package io.vicp.frlib.nio.nionetty;

import java.nio.channels.ServerSocketChannel;

/**
 * Created by zhoudr on 2017/6/15.
 */
public interface Boss {
    public void registerAcceptableChannelTask(ServerSocketChannel serverSocketChannel);
}

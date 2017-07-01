package io.vicp.frlib.nio.nionetty;

import java.nio.channels.SocketChannel;

/**
 * Created by zhoudr on 2017/6/15.
 */
public interface Worker {
    public void registerNewChannelTask(SocketChannel socketChannel);
}

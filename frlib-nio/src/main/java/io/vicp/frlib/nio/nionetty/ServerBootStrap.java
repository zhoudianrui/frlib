package io.vicp.frlib.nio.nionetty;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;

/**
 * Created by zhoudr on 2017/6/15.
 */
public class ServerBootStrap {

    private NioSelectorPool nioSelectorPool;

    public ServerBootStrap(NioSelectorPool nioSelectorPool) {
        this.nioSelectorPool = nioSelectorPool;
    }

    public void bind(int port) {
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.bind(new InetSocketAddress(port));
            Boss boss = nioSelectorPool.getBoss();
            boss.registerAcceptableChannelTask(serverSocketChannel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

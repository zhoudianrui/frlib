package io.vicp.frlib.nio.nionetty;

import java.io.IOException;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Executor;

/**
 * Created by zhoudr on 2017/6/15.
 */
public class NioServerBoss extends AbstractNioServer implements Boss{

    public NioServerBoss(Executor bossExecutor, String threadName, NioSelectorPool nioSelectorPool) {
        this.executor = bossExecutor;
        this.threadName = threadName;
        this.nioSelectorPool = nioSelectorPool;
        openSelector();
    }

    @Override
    public int select(Selector selector) throws IOException{
        return selector.select();
    }

    @Override
    protected void process(Selector selector) throws IOException{
        Set<SelectionKey> keySet = selector.selectedKeys();
        if (keySet.isEmpty()) {
            return;
        }
        Iterator<SelectionKey> keyIterator = keySet.iterator();
        while (keyIterator.hasNext()) {
            SelectionKey selectionKey = keyIterator.next();
            keyIterator.remove();
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
            // 新连接的客户端
            SocketChannel socketChannel = serverSocketChannel.accept();
            // 设置为非阻塞模式
            socketChannel.configureBlocking(false);
            // 注册客户端接入任务
            Worker worker = this.nioSelectorPool.getWorker();// 保证每次产生的新连接被分配到每一个worker，使其处理
            worker.registerNewChannelTask(socketChannel);
            System.out.println("新客户端连接");
        }
    }

    public void registerAcceptableChannelTask(final ServerSocketChannel serverSocketChannel) {
        final Selector selector = this.selector;
        registerTask(new Runnable() {
            @Override
            public void run() {
                try {
                    serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
                } catch (ClosedChannelException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

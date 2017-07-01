package io.vicp.frlib.nio.nionetty;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Executor;

/**
 * Created by zhoudr on 2017/6/15.
 */
public class NioServerWorker extends AbstractNioServer implements Worker{
    public NioServerWorker(Executor workerExecutor, String threadName, NioSelectorPool nioSelectorPool) {
        this.executor = workerExecutor;
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
        if (keySet == null) {
            return;
        }
        Iterator<SelectionKey> keyIterator = keySet.iterator();
        while (keyIterator.hasNext()) {
            SelectionKey selectionKey = keyIterator.next();
            keyIterator.remove();
            SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
            int size = 0;
            boolean failure = false;
            ByteBuffer input = ByteBuffer.allocate(1024);
            try {
                size = socketChannel.read(input);
            } catch (Exception e) {
                failure = true;
            }
            if (failure || size <= 0) {
                selectionKey.cancel();
                System.out.println("客户端断开连接");
            } else {
                String message = new String(input.array());
                System.out.println("收到消息:" + message);
                ByteBuffer output = ByteBuffer.wrap("\n收到".getBytes());
                socketChannel.write(output);
            }
        }
    }

    @Override
    public void registerNewChannelTask(final SocketChannel socketChannel) {
        final Selector selector = this.selector;
        registerTask(new Runnable() {
            @Override
            public void run() {
                try {
                    socketChannel.register(selector, SelectionKey.OP_READ);
                } catch (ClosedChannelException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

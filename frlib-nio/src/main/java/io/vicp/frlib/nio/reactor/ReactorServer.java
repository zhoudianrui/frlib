package io.vicp.frlib.nio.reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * Created by zhoudr on 2017/6/7.
 */
public class ReactorServer implements Runnable{

    private ServerSocketChannel serverSocketChannel = null;
    private Selector selector = null;
    private static final Integer defaultPort = 61284;
    private volatile boolean stop = false;

    public ReactorServer() {
        this(defaultPort);
    }
    public ReactorServer(Integer port) {
        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(port));
            serverSocketChannel.configureBlocking(false); // 非阻塞模式
            SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("The time server is start in port : " + port);
            selectionKey.attach(new Acceptor());
        } catch (IOException e) {
            stopServer();
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(!stop) {
            try {
                selector.select();
                Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
                while (keyIterator.hasNext()) {
                    SelectionKey selectionKey = keyIterator.next();
                    keyIterator.remove();
                    try {
                        dispatch(selectionKey); // 复用selectionKey的attach
                    }catch (Exception e) {
                        System.out.println("client down");
                        if (selectionKey != null) {
                            SelectableChannel tempSocketChannel = selectionKey.channel();
                            tempSocketChannel.close();
                            selectionKey.cancel();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            selector.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void dispatch(SelectionKey selectionKey) {
        Runnable runnable = (Runnable)selectionKey.attachment();
        if (runnable != null) {
            runnable.run();
        }
    }

    public void start() {
        Thread server = new Thread(this, "server");
        server.start();
    }

    public void shutdown() {
        stopServer();
        // 处理完事件后停服
        selector.wakeup();
    }

    public void stopServer() {
        this.stop = true;
    }

    class Acceptor implements Runnable{

        @Override
        public void run() {
            try {
                SocketChannel socketChannel = serverSocketChannel.accept();
                if (socketChannel != null) {
                    new SocketReadHandler(selector, socketChannel);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

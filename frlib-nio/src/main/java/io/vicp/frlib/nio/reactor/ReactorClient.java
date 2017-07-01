package io.vicp.frlib.nio.reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * Created by zhoudr on 2017/6/7.
 */
public class ReactorClient implements Runnable {

    private SocketChannel socketChannel;
    private Selector selector;
    private String host;
    private Integer port;
    private boolean stop = false;

    public ReactorClient(String host, Integer port) {
        try {
            this.host = host;
            this.port = port;
            selector = Selector.open();
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void doConnect() {
        try {
            boolean connectResult = socketChannel.connect(new InetSocketAddress(host, port));
            if (connectResult) {
                socketChannel.register(selector, SelectionKey.OP_READ);
            } else {
                socketChannel.register(selector, SelectionKey.OP_CONNECT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void doWrite(String message) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.clear();
        byteBuffer.put(message.getBytes());
        byteBuffer.flip();
        try {
            socketChannel.write(byteBuffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        doConnect();
        while(!stop) {
            try {
                selector.select();
                Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
                while (keyIterator.hasNext()) {
                    SelectionKey selectionKey = keyIterator.next();
                    keyIterator.remove();
                    try {
                        process(selectionKey);
                    }catch (Exception e) {
                        e.printStackTrace();
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
        if (selector != null) {
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " is over");
        }
    }

    private void process(SelectionKey selectionKey) throws IOException {
        if (selectionKey.isValid()) {
            SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
            if (selectionKey.isConnectable()) {
                if (socketChannel.finishConnect()) {
                    socketChannel.register(selector, SelectionKey.OP_READ);
                    InetSocketAddress inetSocketAddress = (InetSocketAddress) socketChannel.getLocalAddress();
                    System.out.println("client port:" + inetSocketAddress.getPort());
                    doWrite(Thread.currentThread().getName());
                } else {
                    System.exit(1);
                }
            }
            if (selectionKey.isReadable()) {
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                readBuffer.clear();
                int readBytes = socketChannel.read(readBuffer);
                if (readBytes > 0) {
                    readBuffer.flip();
                    byte[] bytes = new byte[readBuffer.remaining()];
                    readBuffer.get(bytes);
                    String body = new String(bytes, "UTF-8");
                    System.out.println("client receive:" + body);
                    doWrite("Goodbye");
                    this.stop = true;
                } else if (readBytes < 0) {
                    selectionKey.cancel();
                    socketChannel.close();
                }
            }
        }
    }
}

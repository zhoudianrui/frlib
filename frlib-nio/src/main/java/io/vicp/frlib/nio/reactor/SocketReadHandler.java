package io.vicp.frlib.nio.reactor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * Created by zhoudr on 2017/6/7.
 */
public class SocketReadHandler implements Runnable {
    private SocketChannel socketChannel;
    private SelectionKey selectionKey;

    public SocketReadHandler(Selector selector, SocketChannel sc) {
        try {
            this.socketChannel = sc;
            socketChannel.configureBlocking(false);
            selectionKey = socketChannel.register(selector, 0);

            // 将selectionKey绑定为该handler下一步有事件触发时，将调用本类的run
            selectionKey.interestOps(SelectionKey.OP_READ);
            selectionKey.attach(this);
            selector.wakeup(); // 唤醒监听器
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        readRequest();
    }

    private void readRequest() {
        ByteBuffer input = ByteBuffer.allocate(1024);
        input.clear();
        try {
            StringBuffer receiveMsg = new StringBuffer();
            int readBytes = socketChannel.read(input);
            if(readBytes > 0) {
                input.flip();
                byte[] bytes = new byte[input.remaining()];
                input.get(bytes);
                receiveMsg.append(new String(bytes, "UTF-8"));
                process(socketChannel, receiveMsg.toString());
            } else {
                this.selectionKey.cancel();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void process(SocketChannel socketChannel, String message) {
        System.out.println("server receive: " + message);
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.clear();
        byteBuffer.put(new String("return " + message).getBytes());
        byteBuffer.flip();
        try {
            socketChannel.write(byteBuffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

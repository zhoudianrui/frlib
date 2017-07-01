package io.vicp.frlib.nio.exercise.channel;

import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;

/**
 * Created by zhoudr on 2017/1/13.
 */
public class MultiplexerTimeServer implements Runnable{

    /**
     * 选择器
     */
    private Selector selector;

    /**
     * 服务端channel
     */
    private ServerSocketChannel serverSocketChannel;

    /**
     * 程序结束标志
     */
    private volatile boolean stop;

    /**
     * 初始化多路复用器、绑定监听端口
     * @param port
     */
    public MultiplexerTimeServer(int port) {
        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false); // 非阻塞模式
            serverSocketChannel.socket().bind(new InetSocketAddress("127.0.0.1", port), 1024); // 设置backlog，最大等待连接数
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);  // 注册channel至选择器并且监听SelectionKey.OP_ACCEPT操作位
            System.out.println("The time server is start in port : " + port);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * 停止运行服务
     */
    public void stop() {
        this.stop = true;
    }

    @Override
    public void run() {
        while (!stop) {
            try {
                // selector.select()方法的阻塞唤醒方式：有socket事件，超时或者selector.warkup()中断发生
                // selector.select()方法，只有在收到已经选择的channel或者被中断，或者超出时间时才返回
                //int selectedCount = selector.select(1000);//相当于Object.wait(1000)
                selector.select();
                //if (selectedCount > 0) {
                    Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
                    while (keyIterator.hasNext()) {
                        final SelectionKey selectionKey = keyIterator.next();
                        System.out.println("next:" + selectionKey);
                        keyIterator.remove();
                        //不能重新启动线程，否则有空指针异常，以为第一个key未处理完，然后会再被select一次
                        // 线程执行时，第一次正常然后第二个因为第一个处理完然后SelectionKeyImpl被删除的因素，导致第二个进程空指针
                        /*new Thread(){
                            @Override
                            public void run() {
                                try {
                                    handleInput(selectionKey);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }.start();*/
                        try {
                            handleInput(selectionKey);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                //}
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 多路复用器关闭后，所有注册在上面的channel和pipe等资源都会被自动去注册关闭，所以不需要重复释放资源
        if (selector != null) {
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 处理新接入的请求消息
     * @param selectionKey
     * @throws IOException
     */
    private void handleInput(SelectionKey selectionKey) throws IOException{
        System.out.println("handle:" + selectionKey);
        if (selectionKey.isValid()) {
            // 首先判断网络事件的类型
            if (selectionKey.isAcceptable()) {
                ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
                SocketChannel socketChannel = serverSocketChannel.accept();
                socketChannel.configureBlocking(false);
                socketChannel.register(selector, SelectionKey.OP_READ);
            }
            if (selectionKey.isReadable()) {
                // read data
                SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                readBuffer.clear();
                int readBytes = socketChannel.read(readBuffer);
                if (readBytes > 0) {
                    readBuffer.flip();
                    byte[] bytes = new byte[readBuffer.remaining()];
                    readBuffer.get(bytes);
                    String body = new String(bytes, "UTF-8");
                    System.out.println("The time server receive order : " + body);
                    String currentTime = "Query Time Order".equalsIgnoreCase(body) ? new Date().toString() : "Bad Order";
                    //doWrite(socketChannel, currentTime);
                } else if (readBytes < 0) {
                    // 链路关闭
                    selectionKey.cancel();
                    socketChannel.close();
                } else {
                    // 读到0字节，忽略
                }
            }
        }
    }

    private void doWrite(SocketChannel socketChannel, String data) throws IOException{
        if (StringUtils.isNotEmpty(data)) {
            byte[] bytes = data.getBytes();
            ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
            writeBuffer.put(bytes);
            writeBuffer.flip();
            socketChannel.write(writeBuffer);
        }
    }
}

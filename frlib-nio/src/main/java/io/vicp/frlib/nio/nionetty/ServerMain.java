package io.vicp.frlib.nio.nionetty;

/**
 * Created by zhoudr on 2017/6/15.
 */
public class ServerMain {
    public static void main(String[] args) {
        NioSelectorPool nioSelectorPool = new NioSelectorPool();
        ServerBootStrap serverBootStrap = new ServerBootStrap(nioSelectorPool);
        serverBootStrap.bind(61284);
        System.out.println("服务器已经启动");
    }
}

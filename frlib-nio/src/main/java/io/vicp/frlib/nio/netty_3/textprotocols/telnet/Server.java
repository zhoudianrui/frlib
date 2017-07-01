package io.vicp.frlib.nio.netty_3.textprotocols.telnet;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * description : 模拟telnet服务端
 * user : zhoudr
 * time : 2017/6/27 15:19
 */

public class Server {

    public static void main(String[] args) {
        InetSocketAddress bindAdress = new InetSocketAddress(61284);
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        // 分别产生boss与worker线程池
        Executor boss = Executors.newCachedThreadPool();
        Executor worker = Executors.newCachedThreadPool();
        serverBootstrap.setFactory(new NioServerSocketChannelFactory(boss, worker));

        // 设置pipeline工厂
        serverBootstrap.setPipelineFactory(new TelnetServerPipelineFactory());

        serverBootstrap.bind(bindAdress);

        System.out.println("server start @" + bindAdress.getHostString() + ":" + bindAdress.getPort());
    }
}

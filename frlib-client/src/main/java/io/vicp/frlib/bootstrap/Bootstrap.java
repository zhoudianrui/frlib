package io.vicp.frlib.bootstrap;

/**
 * <p>客户端启动器<p>
 *
 * @author zhoudr
 * @version $Id: Bootstrap, v 0.1 2017/7/31 15:23 zhoudr Exp $$
 */

public class Bootstrap {
    public static void main(String[] args) {
        final String host = "127.0.0.1";
        final int port = 61284;
        Client client = new Client(host, port);
        client.start();
    }
}

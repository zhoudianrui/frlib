package io.vicp.frlib.bootstrap;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * <p>启动器<p>
 *
 * @author zhoudr
 * @version $Id: Bootstrap, v 0.1 2017/7/29 16:00 zhoudr Exp $$
 */

public class Bootstrap {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        Server server = (Server)applicationContext.getBean("server");
        server.start();
    }
}

package io.vicp.frlib.web.mvc.listener;

import io.vicp.frlib.util.ResourceConfigUtil;
import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.ServletContextEvent;

/**
 * Created by zhoudr on 2016/12/12.
 */
public class ApplicationListener extends ContextLoaderListener {

    public void contextInitialized(ServletContextEvent sce) {
        super.contextInitialized(sce);
        String env = ResourceConfigUtil.getProperty("env");
        System.out.println("application initialized, current system is " + env);
    }

    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("application destroyed");
        super.contextDestroyed(sce);
    }
}

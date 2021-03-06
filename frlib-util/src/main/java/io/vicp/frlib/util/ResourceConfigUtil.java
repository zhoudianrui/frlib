package io.vicp.frlib.util;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * 配置资源工具类
 * Created by zhoudr on 2016/12/12.
 */
public class ResourceConfigUtil {

    private static final Properties properties = new Properties();

    static {
        loadResource();
    }

    private static void loadResource(){
        String filePath = getClassPath() + "config.properties";
        try {
            FileInputStream fis = new FileInputStream(filePath);
            properties.load(fis);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * 获取根路径
     *
     * @return
     */
    public static String getClassPath() {
        String classPath = ResourceConfigUtil.class.getResource("/").getPath();
        return classPath;
    }
}

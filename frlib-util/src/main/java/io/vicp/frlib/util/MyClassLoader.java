package io.vicp.frlib.util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by zhoudr on 2017/5/25.
 */
public class MyClassLoader {
    public static void main(String[] args) throws MalformedURLException {
        URL[] repositories = new URL[1];
        String baseDir = System.getProperty("user.dir");
        File file = new File(baseDir);
        String fileUrlString = file.toURI().toString();
        repositories[0] = new URL(fileUrlString);
        ClassLoader classLoader = new URLClassLoader(repositories);
        ClassLoader parentClassLoader = classLoader.getParent();
        /*System.out.println(Thread.currentThread().getContextClassLoader());
        System.out.println(classLoader);
        System.out.println(parentClassLoader);*/

        Thread.currentThread().setContextClassLoader(classLoader);
        MyClass instance = null;
        try {
            Class clazz = classLoader.loadClass("io.vicp.frlib.util.MyClass");
            instance = (MyClass)clazz.newInstance();
            System.out.println(clazz.getClassLoader());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        MyClass myClass = new MyClass();
        System.out.println(myClass.getClass().getClassLoader() == instance.getClass().getClassLoader());
    }
}



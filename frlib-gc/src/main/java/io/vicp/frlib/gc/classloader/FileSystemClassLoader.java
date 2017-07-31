package io.vicp.frlib.gc.classloader;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by zhoudr on 2017/3/27.
 */
public class FileSystemClassLoader extends ClassLoader {

    private Path path;

    public FileSystemClassLoader(Path path) {
        this.path = path;
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        Class<?> c = findLoadedClass(name);
        if (name.contains("frlib")) {
            if (c == null) {
                c = findClass(name);
                if (c == null) {
                    try {
                        if (this.getParent() != null) {
                            c = this.getParent().loadClass(name);
                        } else {
                            return FileSystemClassLoader.getSystemClassLoader().loadClass(name);
                        }
                    } catch (ClassNotFoundException e) {
                    }
                }
            }
        } else {
            c = super.loadClass(name);
        }
        return c;
    }

    public Class<?> findClass(String className) throws ClassNotFoundException{
        try {
            byte[] classBytes = getClassData(className);
            return defineClass(className, classBytes, 0, classBytes.length);
        }catch (IOException e) {
            throw new ClassNotFoundException();
        }
    }

    public byte[] getClassData(String className) throws IOException {
        Path classNamePath = classNameToPath(className);
        return Files.readAllBytes(classNamePath);
    }

    private Path classNameToPath(String className) {
        Path result = path.resolve(className.replace('.', File.separatorChar) + ".class");
        return result;
    }

    public static void main(String[] args) {
        try {
            Path path = Paths.get("e:\\github\\frlib\\frlib-gc\\src\\main\\java\\");
            FileSystemClassLoader fileSystemClassLoader1 = new FileSystemClassLoader(path);
            FileSystemClassLoader fileSystemClassLoader2 = new FileSystemClassLoader(path);
            Class<?> class1 = fileSystemClassLoader1.loadClass("io.vicp.frlib.gc.classloader.Sample");
            Object object1 = class1.newInstance();
            Class<?> class2 = fileSystemClassLoader2.loadClass("io.vicp.frlib.gc.classloader.Sample");
            Object object2 = class2.newInstance();
            Class<?> class3 = FileSystemClassLoader.getSystemClassLoader().loadClass("io.vicp.frlib.gc.classloader.Sample");
            Method[] methods = class1.getMethods();
            Method setMethod = class1.getMethod("setSample", Sample.class);
            setMethod.invoke(object1, object2);
            //System.out.println(((Sample)object1).getObj()== object2);
            System.out.println(class1.getClassLoader());
            System.out.println(class2.getClassLoader());
            System.out.println(class3.getClassLoader());
            System.out.println(class1 == class2);
            System.out.println(object1.getClass().getClassLoader());
            System.out.println(object2.getClass().getClassLoader());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}


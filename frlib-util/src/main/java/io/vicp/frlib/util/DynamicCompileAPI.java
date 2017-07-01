package io.vicp.frlib.util;

import javax.tools.*;
import java.net.URI;
import java.util.Arrays;

/**
 * Created by zhoudr on 2017/5/22.
 */
public class DynamicCompileAPI {
    public static void main(String[] args) {

    }

    public void dynamicCompile(String src, String output) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler(); // default compiler
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        Iterable<? extends JavaFileObject> compliationUnits = fileManager.getJavaFileObjects(src);
        Iterable<String> options = Arrays.asList("-d", output);
        JavaCompiler.CompilationTask compilationTask = compiler.getTask(null, fileManager, null, options, null, compliationUnits);
        compilationTask.call();
    }
}

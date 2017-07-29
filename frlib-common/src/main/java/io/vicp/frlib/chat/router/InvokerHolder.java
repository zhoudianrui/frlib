package io.vicp.frlib.chat.router;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * <p>注释<p>
 *
 * @author zhoudr
 * @version $Id: InvokerHolder, v 0.1 2017/7/22 17:23 zhoudr Exp $$
 */
@Component
public class InvokerHolder implements BeanPostProcessor {

    private Map<Integer, Map<Integer, Method>> map = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class[] interfaces = bean.getClass().getInterfaces();
        if (interfaces != null) {
            for (Class interfaceClass : interfaces) {
                Annotation tempAnnotation = interfaceClass.getAnnotation(Module.class);
                if (tempAnnotation != null) {
                    Module moduleAnnotation = (Module)tempAnnotation;
                    int module = moduleAnnotation.value();
                    Method[] methods = bean.getClass().getMethods();
                    if (methods != null) {
                        for (Method method : methods) {
                            final Command commandAnnotation = method.getAnnotation(Command.class);
                            if (commandAnnotation != null) {
                                int command = commandAnnotation.value();
                                Map<Integer, Method> commandMethodMap = map.get(module);
                                if (commandMethodMap == null) {
                                    commandMethodMap = new HashMap<>();
                                    map.put(module, commandMethodMap);
                                }
                                commandMethodMap.put(command, method);
                            }
                        }
                    }
                }
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}

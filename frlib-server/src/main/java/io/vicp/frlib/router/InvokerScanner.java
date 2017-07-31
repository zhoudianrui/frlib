package io.vicp.frlib.router;

import io.vicp.frlib.chat.annotation.Command;
import io.vicp.frlib.chat.annotation.Module;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * <p>注解扫描器<p>
 *
 * @author zhoudr
 * @version $Id: InvokerHolder, v 0.1 2017/7/22 17:23 zhoudr Exp $$
 */
@Component
public class InvokerScanner implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class beanClass = bean.getClass();
        if (beanClass != null) {
            Annotation tempAnnotation = beanClass.getAnnotation(Module.class);
            if (tempAnnotation != null) {
                Module moduleAnnotation = (Module)tempAnnotation;
                int module = moduleAnnotation.value().getValue();
                Method[] methods = bean.getClass().getMethods();
                if (methods != null) {
                    for (Method method : methods) {
                        final Command commandAnnotation = method.getAnnotation(Command.class);
                        if (commandAnnotation != null) {
                            int command = commandAnnotation.value().getValue();
                            Invoker invoker = InvokerHolder.get(module, command);
                            if (invoker == null) {
                                invoker = new Invoker(bean, method);
                                InvokerHolder.add(module, command, invoker);
                            } else {
                                System.err.println("重复命令:[module=" + module + "],[command=" + command+ "]");
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

package io.vicp.frlib.router;

import lombok.Data;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * <p>调用器<p>
 *
 * @author zhoudr
 * @version $Id: Invoker, v 0.1 2017/7/29 15:40 zhoudr Exp $$
 */
@Data
public class Invoker {

    private Method method;

    private Object target;

    public Invoker(Object target, Method method) {
        this.target = target;
        this.method = method;
    }

    public Object invoke(Object... params) {
        try {
            return method.invoke(target, params);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}

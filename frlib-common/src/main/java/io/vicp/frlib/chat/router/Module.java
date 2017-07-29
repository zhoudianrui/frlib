package io.vicp.frlib.chat.router;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>模块<p>
 *
 * @author zhoudr
 * @version $Id: Module, v 0.1 2017/7/22 17:20 zhoudr Exp $$
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @ interface Module {
    int value() default 0;
}

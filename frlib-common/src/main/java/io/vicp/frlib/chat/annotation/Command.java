package io.vicp.frlib.chat.annotation;

import io.vicp.frlib.chat.module.CommandEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>指令<p>
 *
 * @author zhoudr
 * @version $Id: Command, v 0.1 2017/7/22 17:31 zhoudr Exp $$
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @ interface Command {
    CommandEnum value() default CommandEnum.INVALID;
}

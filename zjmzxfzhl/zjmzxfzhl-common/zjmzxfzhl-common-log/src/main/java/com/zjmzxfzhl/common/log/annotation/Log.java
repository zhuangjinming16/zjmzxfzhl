package com.zjmzxfzhl.common.log.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 系统日志注解
 *
 * @author 庄金明
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    /**
     * 日志内容
     *
     * @return
     */
    String value() default "";
}

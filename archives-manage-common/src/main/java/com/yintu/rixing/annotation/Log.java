package com.yintu.rixing.annotation;

import com.yintu.rixing.enumobject.EnumLogLevel;

import java.lang.annotation.*;

/**
 * @Author: mlf
 * @Date: 2020/11/27 13:59
 * @Version: 1.0
 */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    /**
     * 日志级别
     */
    EnumLogLevel level() default EnumLogLevel.TRACE;

    /**
     * 模块
     */
    String module() default "";

    /**
     * 内容
     */
    String context() default "";
}

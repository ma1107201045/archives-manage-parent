package com.yintu.rixing.annotation;

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
     * 模块
     */
    String module() default "";

    /**
     * 描述
     */
    String description() default "";
}

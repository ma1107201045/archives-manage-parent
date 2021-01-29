package com.yintu.rixing.annotation;

import java.lang.annotation.*;

/**
 * @Author: mlf
 * @Date: 2021/1/28 19:22:41
 * @Version: 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface JwtTokenIgnore {
}

package com.yintu.rixing.util;

import com.yintu.rixing.exception.BaseRuntimeException;

import java.util.Collection;

/**
 * @Author: mlf
 * @Date: 2021/1/18 16:51:55
 * @Version: 1.0
 */
public class AssertUtil {


    public static void isNull(Object value, String message) {
        if (value != null) {
            throw new BaseRuntimeException(message);
        }
    }

    public static void notNull(Object value, String message) {
        if (value == null) {
            throw new BaseRuntimeException(message);
        }
    }

    public static <T extends String> void isEmpty(T value, String message) {
        notNull(value, message);
        if (!value.isEmpty()) {
            throw new BaseRuntimeException(message);
        }
    }

    public static <T extends String> void notEmpty(T value, String message) {
        notNull(value, message);
        if (value.isEmpty()) {
            throw new BaseRuntimeException(message);
        }
    }

    public static void isLength(Collection<?> value, String message) {
        if (!value.isEmpty()) {
            throw new BaseRuntimeException(message);
        }
    }

    public static <T extends String> void notLength(Collection<?> value, String message) {
        if (value.isEmpty()) {
            throw new BaseRuntimeException(message);
        }
    }
}

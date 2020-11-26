package com.yintu.rixing.config.exception;

/**
 * @Author: mlf
 * @Date: 2020/11/26 13:56
 * @Version: 1.0
 */
public class BaseRuntimeException extends RuntimeException {
    private static final long serialVersionUID = -7890197876492968262L;

    public BaseRuntimeException(String message) {
        super(message);
    }


    public BaseRuntimeException(Throwable cause) {
        super(cause);

    }

    public BaseRuntimeException(String message, Throwable cause) {
        super(message, cause);

    }
}

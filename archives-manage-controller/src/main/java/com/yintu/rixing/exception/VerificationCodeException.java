package com.yintu.rixing.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @Author: mlf
 * @Date: 2020/11/25 15:33
 * @Version: 1.0
 */
public class VerificationCodeException extends AuthenticationException {

    private static final long serialVersionUID = 1716529874750079887L;

    public VerificationCodeException(String msg) {
        super(msg);
    }

    public VerificationCodeException(String msg, Throwable t) {
        super(msg, t);
    }
}

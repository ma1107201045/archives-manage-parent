package com.yintu.rixing.security;

import org.aspectj.lang.JoinPoint;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: mlf
 * @Date: 2020/11/27 14:11
 * @Version: 1.0
 */
public interface ILogService {

    @Transactional(rollbackFor = {Exception.class})
    void put(JoinPoint joinPoint, HttpServletRequest request, String methodName, Integer userId, String username, String operator, Short level, String module, String context);

}

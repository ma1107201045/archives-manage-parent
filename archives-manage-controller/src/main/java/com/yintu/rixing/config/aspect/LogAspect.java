package com.yintu.rixing.config.aspect;

import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.config.other.Authenticator;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.system.ILogService;
import com.yintu.rixing.system.SysUser;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @Author: mlf
 * @Date: 2020/11/27 13:47
 * @Version: 1.0
 */
@Aspect
@Component
public class LogAspect extends Authenticator {

    @Autowired
    private ILogService iLogService;

    /**
     * 日志切入点
     */
    @Pointcut("@annotation(com.yintu.rixing.annotation.Log)")
    public void logPointCut() {
    }

    @AfterReturning(pointcut = "logPointCut()")
    public void doAfter(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String methodName = joinPoint.getSignature().getName();
        SysUser sysUser = this.getLoginUser();
        Integer userId = -1;
        String username = "unknown";
        String nickName = "unknown";
        if (sysUser != null) {
            userId = sysUser.getId();
            username = sysUser.getUsername();
            nickName = sysUser.getNickname();
        }
        Method method = currentMethod(joinPoint, methodName);
        Log log = method.getAnnotation(Log.class);
        EnumLogLevel enumLogLevel = log.level();
        Short level = null;
        switch (enumLogLevel) {
            case TRACE:
                level = 1;
                break;
            case INFO:
                level = 2;
                break;
            case DEBUG:
                level = 3;
                break;
            case WARN:
                level = 4;
                break;
            case ERROR:
                level = 5;
                break;
        }
        String module = log.module();
        String description = log.description();
        iLogService.put(joinPoint, request, methodName, userId, username, nickName, level, module, description);
    }

    /**
     * 获取当前执行的方法
     *
     * @param joinPoint  连接点
     * @param methodName 方法名称
     * @return 方法
     */
    private Method currentMethod(JoinPoint joinPoint, String methodName) {
        /*
         * 获取目标类的所有方法，找到当前要执行的方法
         */
        Method[] methods = joinPoint.getTarget().getClass().getMethods();
        Method resultMethod = null;
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                resultMethod = method;
                break;
            }
        }
        return resultMethod;
    }

}

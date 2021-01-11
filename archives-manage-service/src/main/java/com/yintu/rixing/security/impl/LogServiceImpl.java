package com.yintu.rixing.security.impl;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.security.ILogService;
import com.yintu.rixing.security.ISecLogService;
import com.yintu.rixing.security.SecLog;
import com.yintu.rixing.util.IPUtil;
import org.apache.ibatis.javassist.*;
import org.apache.ibatis.javassist.bytecode.CodeAttribute;
import org.apache.ibatis.javassist.bytecode.LocalVariableAttribute;
import org.apache.ibatis.javassist.bytecode.MethodInfo;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: mlf
 * @Date: 2020/11/27 15:08
 * @Version: 1.0
 */
@Component
public class LogServiceImpl implements ILogService {


    private static final String LOG_DESCRIPTION = "[类名]:%s,[方法]:%s,[参数]:%s,[IP]:%s";


    @Autowired
    private ISecLogService iSecLogService;

    @Override
    public void put(JoinPoint joinPoint, HttpServletRequest request, String methodName, Integer userId, String username, String operator, Short level, String module, String context) {
        SecLog secLog = new SecLog();
        String loginIp = IPUtil.getIpAddress(request);
        secLog.setCreateTime(DateUtil.date());
        secLog.setUserId(userId);
        secLog.setUsername(username);
        secLog.setOperator(operator);
        secLog.setLevel(level);
        secLog.setModule(module);
        secLog.setContext(context);
        try {
            secLog.setDescription(this.operateContent(joinPoint, methodName, IPUtil.getIpAddress(request), request));
        } catch (Exception e) {
            secLog.setLevel((short) 5);
            secLog.setModule("日志管理");
            secLog.setContext(context);
            secLog.setDescription(String.format(LOG_DESCRIPTION, this.getClass().getName(), "put", "message={\"error\":\"" + e.getMessage() + "\"}", loginIp));
        }
        secLog.setLoginIp(loginIp);
        iSecLogService.save(secLog);
    }


    public String operateContent(JoinPoint joinPoint, String methodName, String ip, HttpServletRequest request) throws ClassNotFoundException, NotFoundException {
        String className = joinPoint.getTarget().getClass().getName();
        Object[] params = joinPoint.getArgs();
        String classType = joinPoint.getTarget().getClass().getName();
        Class<?> clazz = Class.forName(classType);
        String clazzName = clazz.getName();
        Map<String, Object> nameAndArgs = getFieldsName(this.getClass(), clazzName, methodName, params);
        StringBuilder sb = new StringBuilder();
        if (!CollectionUtils.isEmpty(nameAndArgs)) {
            for (Map.Entry<String, Object> entry : nameAndArgs.entrySet()) {
                String key = entry.getKey();
                String value = JSONObject.toJSONString(entry.getValue());
                sb.append(key).append("=");
                sb.append(value).append("&");
            }
        }
        if (StringUtils.isEmpty(sb.toString())) {
            sb.append(request.getQueryString());
        }
        return String.format(LOG_DESCRIPTION, className, methodName, sb.toString(), ip);
    }

    private Map<String, Object> getFieldsName(Class<?> cls, String clazzName, String methodName, Object[] args) throws NotFoundException {
        Map<String, Object> map = new HashMap<>();

        ClassPool pool = ClassPool.getDefault();
        ClassClassPath classPath = new ClassClassPath(cls);
        pool.insertClassPath(classPath);

        CtClass cc = pool.get(clazzName);
        CtMethod cm = cc.getDeclaredMethod(methodName);
        MethodInfo methodInfo = cm.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
        if (attr == null) {
            // exception
            return map;
        }
        int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
        for (int i = 0; i < cm.getParameterTypes().length; i++) {
            map.put(attr.variableName(i + pos), args[i]);//paramNames即参数名
        }
        return map;
    }

}

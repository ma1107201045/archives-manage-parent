package com.yintu.rixing.system.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.yintu.rixing.system.ILogService;
import com.yintu.rixing.system.ISysLogService;
import com.yintu.rixing.system.SysLog;
import com.yintu.rixing.util.IPUtil;
import jdk.nashorn.internal.ir.annotations.Reference;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @Author: mlf
 * @Date: 2020/11/27 15:08
 * @Version: 1.0
 */
@Component
public class LogServiceImpl implements ILogService {


    private static final String LOG_CONTENT = "[类名]:%s,[方法]:%s,[参数]:%s,[IP]:%s";


    @Autowired
    private ISysLogService iSysLogService;

    @Override
    public void put(JoinPoint joinPoint, HttpServletRequest request, String methodName, Integer userId, String username, String operator, Short level, String module, String description) {
        try {
            SysLog sysLog = new SysLog();
            sysLog.setUserId(userId);
            sysLog.setUsername(username);
            sysLog.setOperator(operator);
            sysLog.setLevel(level);
            sysLog.setModule(module);
            sysLog.setCreateTime(DateUtil.date());
            sysLog.setDescription(description);
            sysLog.setLoginIp(IPUtil.getIpAddress(request));
            sysLog.setContext(operateContent(joinPoint, methodName, IPUtil.getIpAddress(request), request));
            iSysLogService.save(sysLog);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        return String.format(LOG_CONTENT, className, methodName, sb.toString(), ip);
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
            if (args[i] instanceof ServletResponse)
                map.put(attr.variableName(i + pos), args[i].toString());
            else
                map.put(attr.variableName(i + pos), args[i]);//paramNames即参数名
        }
        return map;
    }

}

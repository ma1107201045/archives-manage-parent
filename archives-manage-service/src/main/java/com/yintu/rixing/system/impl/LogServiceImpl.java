package com.yintu.rixing.system.impl;

import cn.hutool.core.util.StrUtil;
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

    private String username;

    @Autowired
    private ISysLogService iSysLogService;


    public String initUsername(String username) {
        if (!StrUtil.isEmpty(username)) {
            this.username = username;
        }
        return this.username;
    }

    @Override
    public void put(JoinPoint joinPoint, HttpServletRequest request, Integer userId, String username, String methodName, String module, String description) {
        try {
            SysLog sysLog = new SysLog();
            if (StrUtil.isEmpty(username)) {
                this.username = "未知用户";
            }
            String ip = IPUtil.getIpAddress(request);
            sysLog.setUserId(userId);
            sysLog.setUsername(username);
            sysLog.setModule(module);
            sysLog.setCreateTime(new Date());
            sysLog.setDescription(description);
            sysLog.setLoginIp(ip);
            sysLog.setContext(operateContent(joinPoint, methodName, ip, request));
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
        StringBuffer sb = new StringBuffer();
        if (!CollectionUtils.isEmpty(nameAndArgs)) {
            Iterator<Map.Entry<String, Object>> it = nameAndArgs.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, Object> entry = it.next();
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
            map.put(attr.variableName(i + pos), args[i]);//paramNames即参数名
        }
        return map;
    }

}

package com.yintu.rixing.config.component;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.yintu.rixing.system.SysUser;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * @Author: mlf
 * @Date: 2020/11/28 19:20
 * @Version: 1.0
 */
@Component
public class CustomMetaObjectHandler implements MetaObjectHandler {

//    @Autowired
//    private AuthenticationTrustResolver authenticationTrustResolver;

    @Override
    public void insertFill(MetaObject metaObject) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            SysUser sysUser = (SysUser) authentication.getPrincipal();
            this.setFieldValByName("createBy", sysUser.getUsername(), metaObject);
            this.setFieldValByName("modifiedBy", sysUser.getUsername(), metaObject);
        } else {
            this.setFieldValByName("createBy", "unknown", metaObject);
            this.setFieldValByName("modifiedBy", "unknown", metaObject);
        }
        this.setFieldValByName("createTime", DateUtil.date(), metaObject);
        this.setFieldValByName("modifiedTime", DateUtil.date(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            SysUser sysUser = (SysUser) authentication.getPrincipal();
            this.setFieldValByName("modifiedBy", sysUser.getUsername(), metaObject);
        } else {
            this.setFieldValByName("modifiedBy", "unknown", metaObject);
        }
        this.setFieldValByName("modifiedTime", DateUtil.date(), metaObject);
    }
}

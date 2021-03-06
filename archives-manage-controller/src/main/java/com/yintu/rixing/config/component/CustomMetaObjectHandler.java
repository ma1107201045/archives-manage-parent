package com.yintu.rixing.config.component;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.yintu.rixing.config.other.Authenticator;
import com.yintu.rixing.system.SysUser;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

/**
 * @Author: mlf
 * @Date: 2020/11/28 19:20
 * @Version: 1.0
 */
@Component
public class CustomMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        SysUser sysUser = Authenticator.getPrincipal();
        if (sysUser != null) {
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
        SysUser sysUser = Authenticator.getPrincipal();
        if (sysUser != null) {
            this.setFieldValByName("modifiedBy", sysUser.getUsername(), metaObject);
        } else {
            this.setFieldValByName("modifiedBy", "unknown", metaObject);
        }
        this.setFieldValByName("modifiedTime", DateUtil.date(), metaObject);
    }
}

package com.yintu.rixing.config.controller;

import com.yintu.rixing.system.SysUser;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @Author: mlf
 * @Date: 2020/11/27 13:11
 * @Version: 1.0
 */
public class Authenticator {


    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static SysUser getPrincipal() {
        Authentication authentication = getAuthentication();
        return authentication == null ? null : (SysUser) authentication.getPrincipal();
    }

    /**
     * 获取登录用户信息
     *
     * @return 用户信息
     */
    protected SysUser getLoginUser() {
        SecurityContext sc = SecurityContextHolder.getContext();
        Authentication auth = sc.getAuthentication();
        return auth == null ? null : (SysUser) auth.getPrincipal();
    }

    /**
     * 获取登录用户信息id
     *
     * @return 用户id值
     */
    protected Integer getLoginUserId() {
        return this.getLoginUser() == null ? -1 : this.getLoginUser().getId();
    }

    /**
     * 获取登录用户名
     *
     * @return 用户名
     */
    protected String getLoginUserName() {
        return this.getLoginUser() == null ? "unknown" : this.getLoginUser().getUsername();
    }

    /**
     * 获取登录用户名称
     *
     * @return 用户名称
     */
    protected String getLoginTrueName() {
        return this.getLoginUser() == null ? "unknown" : this.getLoginUser().getNickname();
    }

    /**
     * 获取登录用户类型
     *
     * @return 用户类型值
     */
    protected Short getUserAuthType() {
        return this.getLoginUser() == null ? -1 : this.getLoginUser().getAuthType();
    }
}

package com.yintu.rixing.config.component;

import com.yintu.rixing.enumobject.EnumAuthType;
import com.yintu.rixing.system.SysUser;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

/**
 * @author:mlf
 * @date:2020/5/18 19:54
 */

/**
 * (授权)自定义权限控制管理器（处理逻辑）
 */
@Component
public class CustomAccessDecisionManager implements AccessDecisionManager {
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        //此处判断没有登录或者没有权限访问的url是否通过
        if (authentication instanceof AnonymousAuthenticationToken) {
            throw new AuthenticationServiceException("尚未登录，请先登录");
        } else {
            //如果当前用户的auth_type为管理员，则用户拥有所有的权限,否则需要判断当前用户是否具有角色权限
            Object obj = authentication.getPrincipal();
            if (obj instanceof SysUser) {
                SysUser sysUser = (SysUser) obj;
                if (sysUser.getAuthType().equals(EnumAuthType.ADMIN.getValue()))
                    return;
            }
            for (ConfigAttribute configAttribute : configAttributes) {
                String needRole = configAttribute.getAttribute();
                Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
                for (GrantedAuthority authority : authorities) {
                    if (authority.getAuthority().equals(needRole)) {
                        return;
                    }
                }
            }
            if (configAttributes instanceof List) {
                List<ConfigAttribute> roles = (List<ConfigAttribute>) configAttributes;
                if (!roles.isEmpty()) {
                    String roleName = roles.get(0).getAttribute();
                    if ("URL_NOT_AUTHORIZATION".equals(roleName)) {
                        return;
                    }
                }
            }
            throw new AuthorizationServiceException("权限不足，请联系管理员");
        }
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return false;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }


}

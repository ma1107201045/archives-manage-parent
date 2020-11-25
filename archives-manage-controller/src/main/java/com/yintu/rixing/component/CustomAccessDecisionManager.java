package com.yintu.rixing.component;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Collection;

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
        System.out.println(authentication);
        System.out.println(authentication.getPrincipal());
        if (authentication instanceof AnonymousAuthenticationToken) {
            throw new AuthenticationServiceException("尚未登录，请先登录");
        } else {
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

package com.yintu.rixing.config.component;

import com.yintu.rixing.enumobject.EnumFlag;
import com.yintu.rixing.system.ISysPermissionService;
import com.yintu.rixing.vo.system.SysPermissionVo;
import com.yintu.rixing.vo.system.SysRoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author:mlf
 * @date:2020/5/18 19:56
 * （授权）自定义资源（url）权限 （role）数据源（从数据库中查出每个Permission对应的角色，放到Collection<ConfigAttribute>）
 */
@Component
public class CustomFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private ISysPermissionService iSysPermissionService;

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        FilterInvocation filterInvocation = (FilterInvocation) object;
        String requestUrl = filterInvocation.getRequestUrl().split("[?]")[0];//url后边的参数去掉（？号后边的）
        String requestMethod = filterInvocation.getRequest().getMethod();
        List<SysPermissionVo> sysPermissionVos = iSysPermissionService.list(EnumFlag.False.getValue());
        List<ConfigAttribute> configAttributes = new ArrayList<>();
        for (SysPermissionVo sysPermissionVo : sysPermissionVos) {
            if (antPathMatcher.match(sysPermissionVo.getUrl(), requestUrl) && requestMethod.toUpperCase().equals(sysPermissionVo.getMethod())) {
                List<SysRoleVo> sysRoleVos = sysPermissionVo.getSysRoleVos();
                for (SysRoleVo sysRoleVo : sysRoleVos) {
                    configAttributes.add(new SecurityConfig(sysRoleVo.getName().trim()));
                }
            }
        }
        //如果不想在数据库中的权限分配给角色则可以在此处写逻辑
        if (configAttributes.isEmpty()) {
            if (antPathMatcher.match("/common/**", requestUrl)) {
                return SecurityConfig.createList("URL_NOT_AUTHORIZATION");
            } else {
                return SecurityConfig.createList("URL_NEED_AUTHORIZATION");
            }
        }
        return configAttributes;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}

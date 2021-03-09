package com.yintu.rixing.config.component;

import com.yintu.rixing.enumobject.EnumFlag;
import com.yintu.rixing.enumobject.EnumRole;
import com.yintu.rixing.system.ISysPermissionService;
import com.yintu.rixing.pojo.SysPermissionPojo;
import com.yintu.rixing.pojo.SysRolePojo;
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

    private static final String URL_IGNORE = "/common/**";

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        FilterInvocation filterInvocation = (FilterInvocation) object;
        //url后边的参数去掉（？号后边的）
        String requestUrl = filterInvocation.getRequestUrl().split("[?]")[0];
        String requestMethod = filterInvocation.getRequest().getMethod();
        //如果需要授权的接口直接返回固定角色
        if (antPathMatcher.match(URL_IGNORE, requestUrl)) {
            return SecurityConfig.createList(EnumRole.URL_NOT_AUTHORIZATION.toString());
        } else {
            List<SysPermissionPojo> sysPermissionVos = iSysPermissionService.list(EnumFlag.False.getValue());
            List<ConfigAttribute> configAttributes = new ArrayList<>();
            for (SysPermissionPojo sysPermissionVo : sysPermissionVos) {
                if (antPathMatcher.match(sysPermissionVo.getUrl(), requestUrl) && requestMethod.toUpperCase().equals(sysPermissionVo.getMethod())) {
                    List<SysRolePojo> sysRoleVos = sysPermissionVo.getSysRoleVos();
                    for (SysRolePojo sysRoleVo : sysRoleVos) {
                        configAttributes.add(new SecurityConfig(sysRoleVo.getName().trim()));
                    }
                }
            }
            return configAttributes.isEmpty() ? SecurityConfig.createList(EnumRole.URL_NEED_AUTHORIZATION.toString()) : configAttributes;
        }
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

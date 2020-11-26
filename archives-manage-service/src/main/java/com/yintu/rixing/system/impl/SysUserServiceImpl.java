package com.yintu.rixing.system.impl;

import com.yintu.rixing.system.ISysUserService;
import com.yintu.rixing.system.SysUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统用户表 服务实现类
 * </p>
 *
 * @author mlf
 * @since 2020-11-26
 */
@Service
public class SysUserServiceImpl implements ISysUserService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //在这里可以自己调用数据库，对username进行查询，看看在数据库中是否存在
        SysUser sysUser = new SysUser();
        sysUser.setAccountExpired((short) 0);
        sysUser.setAccountLocked((short) 0);
        sysUser.setCredentialsExpired((short) 0);
        sysUser.setAccountEnabled((short) 1);
        sysUser.setUsername(username);
        sysUser.setPassword("$2a$10$5JOpGerCZFUMUunh9AIZO.GFTfW5Cxwt12..qaF35M2YRq71zY1DW");
        return sysUser;
    }
}

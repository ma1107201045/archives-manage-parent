package com.yintu.rixing.system.impl;

import com.yintu.rixing.system.ISysUserService;
import com.yintu.rixing.system.SysUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @Author: mlf
 * @Date: 2020/11/25 14:47
 * @Version: 1.0
 */
@Service
public class SysUserServiceImpl implements ISysUserService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //在这里可以自己调用数据库，对username进行查询，看看在数据库中是否存在
        SysUser sysUser = new SysUser();
        sysUser.setUsername(username);
        sysUser.setPassword("123456");
        return sysUser;
    }
}

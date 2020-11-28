package com.yintu.rixing.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yintu.rixing.system.ISysUserService;
import com.yintu.rixing.system.SysUser;
import com.yintu.rixing.system.SysUserMapper;
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
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //在这里可以自己调用数据库，对username进行查询，看看在数据库中是否存在
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .select(SysUser.class, tableFieldInfo -> !"".equals(tableFieldInfo.getColumn()))
                .eq(SysUser::getUsername, username);
        SysUser sysUser = this.getOne(queryWrapper);
        if (sysUser == null)
            throw new UsernameNotFoundException("用户名不存在");
        //查询角色
        return sysUser;
    }
}

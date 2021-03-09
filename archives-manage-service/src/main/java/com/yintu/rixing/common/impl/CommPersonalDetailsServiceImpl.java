package com.yintu.rixing.common.impl;

import cn.hutool.core.bean.BeanUtil;
import com.yintu.rixing.common.ICommPersonalDetailsService;
import com.yintu.rixing.dto.common.CommPersonalDetailsFormDto;
import com.yintu.rixing.exception.BaseRuntimeException;
import com.yintu.rixing.system.ISysUserService;
import com.yintu.rixing.system.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: mlf
 * @Date: 2021/3/9 10:31:34
 * @Version: 1.0
 */
@Service
public class CommPersonalDetailsServiceImpl implements ICommPersonalDetailsService {

    @Autowired
    private ISysUserService iSysUserService;

    @Override
    public void updateById(CommPersonalDetailsFormDto commPersonalDetailsFormDto) {
        Integer id = commPersonalDetailsFormDto.getId();
        String username = commPersonalDetailsFormDto.getUsername();
        SysUser sysUser = iSysUserService.getById(id);
        if (sysUser != null) {
            //判断用户名是否重复
            List<Integer> ids = iSysUserService.listByUsername(username);
            if (!ids.isEmpty() && !ids.get(0).equals(id)) {
                throw new BaseRuntimeException("用户名重复");
            }
            BeanUtil.copyProperties(commPersonalDetailsFormDto, sysUser);
            iSysUserService.updateById(sysUser);
        }
    }
}

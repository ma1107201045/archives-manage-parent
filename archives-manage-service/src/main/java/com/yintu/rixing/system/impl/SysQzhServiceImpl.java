package com.yintu.rixing.system.impl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yintu.rixing.dto.system.SysQzhFromDto;
import com.yintu.rixing.dto.system.SysQzhQueryDto;
import com.yintu.rixing.system.ISysQzhService;
import com.yintu.rixing.system.SysQzh;
import com.yintu.rixing.system.SysQzhMapper;

import com.yintu.rixing.system.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author mlf
 * @since 2020-11-27
 */
@Service
public class SysQzhServiceImpl extends ServiceImpl<SysQzhMapper, SysQzh> implements ISysQzhService {


    @Override
    public void save(SysQzhFromDto sysUserFormDto) {
        SysQzh sysQzh = new SysQzh();
        BeanUtil.copyProperties(sysUserFormDto, sysQzh);
        this.save(sysQzh);
    }

    @Override
    public void updateById(SysQzhFromDto sysQzhFromDto) {
        SysQzh sysQzh = this.getById(sysQzhFromDto.getId());
        if (sysQzh != null) {
            BeanUtil.copyProperties(sysQzhFromDto, sysQzh);
            this.save(sysQzh);
        }
    }

    @Override
    public Page<SysQzh> page(SysQzhQueryDto sysQzhQueryDto) {
        Integer num = sysQzhQueryDto.getNum();
        Integer size = sysQzhQueryDto.getSize();
        String name = sysQzhQueryDto.getName();
        QueryWrapper<SysQzh> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .like(SysQzh::getName, name == null ? "" : name);
        queryWrapper.orderByDesc("id");
        return this.page(new Page<>(num, size), queryWrapper);
    }
}

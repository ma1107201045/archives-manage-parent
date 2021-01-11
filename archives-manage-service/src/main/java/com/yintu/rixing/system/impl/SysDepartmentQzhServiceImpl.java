package com.yintu.rixing.system.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.ObjectId;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yintu.rixing.dto.system.SysDepartmentQzhFromDto;
import com.yintu.rixing.dto.system.SysDepartmentQzhQueryDto;
import com.yintu.rixing.system.ISysDepartmentIdQzhService;
import com.yintu.rixing.system.SysDepartmentQzh;
import com.yintu.rixing.system.SysDepartmentQzhMapper;

import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * <p>
 * 系统全宗号表 服务实现类
 * </p>
 *
 * @author mlf
 * @since 2020-11-27
 */
@Service
public class SysDepartmentQzhServiceImpl extends ServiceImpl<SysDepartmentQzhMapper, SysDepartmentQzh> implements ISysDepartmentIdQzhService {


    @Override
    public void save(SysDepartmentQzhFromDto sysDepartmentQzhFromDto) {
        SysDepartmentQzh sysDepartmentQzh = new SysDepartmentQzh();
        BeanUtil.copyProperties(sysDepartmentQzhFromDto, sysDepartmentQzh);
        sysDepartmentQzh.setNumber(IdUtil.objectId().toUpperCase(Locale.ROOT));
        this.save(sysDepartmentQzh);
    }

    @Override
    public void updateById(SysDepartmentQzhFromDto sysDepartmentQzhFromDto) {
        SysDepartmentQzh sysQzh = this.getById(sysDepartmentQzhFromDto.getId());
        if (sysQzh != null) {
            BeanUtil.copyProperties(sysDepartmentQzhFromDto, sysQzh);
            this.save(sysQzh);
        }
    }

    @Override
    public Page<SysDepartmentQzh> page(SysDepartmentQzhQueryDto sysQzhQueryDto) {
        Integer num = sysQzhQueryDto.getNum();
        Integer size = sysQzhQueryDto.getSize();
        String name = sysQzhQueryDto.getName();
        QueryWrapper<SysDepartmentQzh> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .like(SysDepartmentQzh::getName, name == null ? "" : name);
        queryWrapper.orderByDesc("id");
        return this.page(new Page<>(num, size), queryWrapper);
    }
}

package com.yintu.rixing.system.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yintu.rixing.dto.system.SysDictionariesFormDto;
import com.yintu.rixing.dto.system.SysDictionariesQueryDto;
import com.yintu.rixing.system.ISysDictionariesService;
import com.yintu.rixing.system.SysDictionaries;
import com.yintu.rixing.system.SysDictionariesMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统数据字典表 服务实现类
 * </p>
 *
 * @author mlf
 * @since 2021-02-27
 */
@Service
public class SysDictionariesServiceImpl extends ServiceImpl<SysDictionariesMapper, SysDictionaries> implements ISysDictionariesService {

    @Override
    public void save(SysDictionariesFormDto sysDictionariesFormDto) {
        SysDictionaries sysDictionaries = new SysDictionaries();
        BeanUtil.copyProperties(sysDictionariesFormDto, sysDictionaries);
        this.save(sysDictionaries);
    }

    @Override
    public void updateById(SysDictionariesFormDto sysDictionariesFormDto) {
        Integer id = sysDictionariesFormDto.getId();
        SysDictionaries sysDictionaries = this.getById(id);
        if (sysDictionaries != null) {
            BeanUtil.copyProperties(sysDictionariesFormDto, sysDictionaries);
            this.updateById(sysDictionaries);
        }

    }

    @Override
    public Page<SysDictionaries> page(SysDictionariesQueryDto sysDictionariesQueryDto) {
        Integer num = sysDictionariesQueryDto.getNum();
        Integer size = sysDictionariesQueryDto.getSize();
        String name = sysDictionariesQueryDto.getName();
        QueryWrapper<SysDictionaries> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().like(SysDictionaries::getName, name == null ? "" : name);
        return this.page(new Page<>(num, size), queryWrapper);
    }
}

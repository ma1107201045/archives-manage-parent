package com.yintu.rixing.system.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yintu.rixing.dto.system.SysDictionariesDetailsFormDto;
import com.yintu.rixing.dto.system.SysDictionariesDetailsQueryDto;
import com.yintu.rixing.system.ISysDictionariesDetailsService;
import com.yintu.rixing.system.SysDictionariesDetails;
import com.yintu.rixing.system.SysDictionariesDetailsMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统数据字典详情表 服务实现类
 * </p>
 *
 * @author mlf
 * @since 2021-02-27
 */
@Service
public class SysDictionariesDetailsServiceImpl extends ServiceImpl<SysDictionariesDetailsMapper, SysDictionariesDetails> implements ISysDictionariesDetailsService {

    @Override
    public void save(SysDictionariesDetailsFormDto sysDictionariesDetailsFormDto) {
        SysDictionariesDetails sysDictionariesDetails = new SysDictionariesDetails();
        BeanUtil.copyProperties(sysDictionariesDetailsFormDto, sysDictionariesDetails);
        this.save(sysDictionariesDetails);
    }

    @Override
    public void updateById(SysDictionariesDetailsFormDto sysDictionariesDetailsFormDto) {
        Integer id = sysDictionariesDetailsFormDto.getId();
        SysDictionariesDetails sysDictionariesDetails = this.getById(id);
        if (sysDictionariesDetails != null) {
            BeanUtil.copyProperties(sysDictionariesDetailsFormDto, sysDictionariesDetails);
            this.updateById(sysDictionariesDetails);
        }
    }

    @Override
    public Page<SysDictionariesDetails> page(SysDictionariesDetailsQueryDto sysDictionariesDetailsQueryDto) {
        Integer num = sysDictionariesDetailsQueryDto.getNum();
        Integer size = sysDictionariesDetailsQueryDto.getSize();
        Integer dictionariesId = sysDictionariesDetailsQueryDto.getDictionariesId();
        QueryWrapper<SysDictionariesDetails> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysDictionariesDetails::getDictionariesId, dictionariesId);
        return this.page(new Page<>(num, size), queryWrapper);
    }
}

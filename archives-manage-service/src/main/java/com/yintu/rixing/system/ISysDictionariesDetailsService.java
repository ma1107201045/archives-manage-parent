package com.yintu.rixing.system;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yintu.rixing.dto.system.SysDictionariesDetailsFormDto;
import com.yintu.rixing.dto.system.SysDictionariesDetailsQueryDto;
import com.yintu.rixing.dto.system.SysDictionariesFormDto;
import com.yintu.rixing.dto.system.SysDictionariesQueryDto;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 系统数据字典详情表 服务类
 * </p>
 *
 * @author mlf
 * @since 2021-02-27
 */
public interface ISysDictionariesDetailsService extends IService<SysDictionariesDetails> {

    @Transactional(rollbackFor = {Exception.class})
    void save(SysDictionariesDetailsFormDto sysDictionariesDetailsFormDto);

    @Transactional(rollbackFor = {Exception.class})
    void updateById(SysDictionariesDetailsFormDto sysDictionariesDetailsFormDto);

    Page<SysDictionariesDetails> page(SysDictionariesDetailsQueryDto sysDictionariesDetailsQueryDto);

}

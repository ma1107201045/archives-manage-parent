package com.yintu.rixing.system;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yintu.rixing.dto.system.SysDictionariesFormDto;
import com.yintu.rixing.dto.system.SysDictionariesQueryDto;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 系统数据字典表 服务类
 * </p>
 *
 * @author mlf
 * @since 2021-02-27
 */
public interface ISysDictionariesService extends IService<SysDictionaries> {

    @Transactional(rollbackFor = {Exception.class})
    void save(SysDictionariesFormDto sysDictionariesFormDto);

    @Transactional(rollbackFor = {Exception.class})
    void updateById(SysDictionariesFormDto sysDictionariesFormDto);

    Page<SysDictionaries> page(SysDictionariesQueryDto sysDictionariesQueryDto);
}

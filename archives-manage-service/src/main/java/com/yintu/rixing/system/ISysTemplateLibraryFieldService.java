package com.yintu.rixing.system;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yintu.rixing.dto.system.SysTemplateLibraryFieldFormDto;
import com.yintu.rixing.dto.system.SysTemplateLibraryFieldQueryDto;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 系统模板库字段表 服务类
 * </p>
 *
 * @author mlf
 * @since 2020-12-30
 */
public interface ISysTemplateLibraryFieldService extends IService<SysTemplateLibraryField> {

    @Transactional(rollbackFor = {Exception.class})
    void save(SysTemplateLibraryFieldFormDto sysTemplateLibraryFieldFormDto);

    @Transactional(rollbackFor = {Exception.class})
    void updateById(SysTemplateLibraryFieldFormDto sysTemplateLibraryFieldFormDto);

    Page<SysTemplateLibraryField> page(SysTemplateLibraryFieldQueryDto sysTemplateLibraryFieldQueryDto);


}

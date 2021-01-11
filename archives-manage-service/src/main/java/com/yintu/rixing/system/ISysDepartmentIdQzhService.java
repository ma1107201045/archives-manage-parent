package com.yintu.rixing.system;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yintu.rixing.dto.system.*;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author mlf
 * @since 2020-11-27
 */
public interface ISysDepartmentIdQzhService extends IService<SysDepartmentQzh> {

    @Transactional(rollbackFor = {Exception.class})
    void save(SysDepartmentQzhFromDto sysUserFormDto);

    @Transactional(rollbackFor = {Exception.class})
    void updateById(SysDepartmentQzhFromDto sysQzhFromDto);

    Page<SysDepartmentQzh> page(SysDepartmentQzhQueryDto sysQzhQueryDto);

}

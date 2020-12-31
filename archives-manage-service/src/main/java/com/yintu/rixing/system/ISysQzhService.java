package com.yintu.rixing.system;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yintu.rixing.dto.system.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author mlf
 * @since 2020-11-27
 */
public interface ISysQzhService extends IService<SysQzh> {

    @Transactional(rollbackFor = {Exception.class})
    void save(SysQzhFromDto sysUserFormDto);

    @Transactional(rollbackFor = {Exception.class})
    void updateById(SysQzhFromDto sysQzhFromDto);


    Page<SysQzh> page(SysQzhQueryDto sysQzhQueryDto);

}

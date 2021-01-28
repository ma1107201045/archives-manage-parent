package com.yintu.rixing.system;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yintu.rixing.dto.system.SysRemoteUserFormDto;
import com.yintu.rixing.dto.system.SysRemoteUserPasswordDto;
import com.yintu.rixing.dto.system.SysRemoteUserQueryDto;
import com.yintu.rixing.dto.system.SysUserQueryDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 系统远程用户表 服务类
 * </p>
 *
 * @author mlf
 * @since 2021-01-28
 */
public interface ISysRemoteUserService extends IService<SysRemoteUser> {

    @Transactional(rollbackFor = {Exception.class})
    void save(SysRemoteUserFormDto sysRemoteUserFormDto);

    @Transactional(rollbackFor = {Exception.class})
    void updateById(SysRemoteUserFormDto sysRemoteUserFormDto);

    @Transactional(rollbackFor = {Exception.class})
    void resetPassword(SysRemoteUserPasswordDto sysRemoteUserPasswordDto);

    List<Integer> listByCertificateNumber(String certificateNumber);

    Page<SysRemoteUser> page(SysRemoteUserQueryDto sysRemoteUserQueryDto);

    SysRemoteUser login(String certificateNumber, String password);

    SysRemoteUser getByCertificateNumber(String certificateNumber);
}

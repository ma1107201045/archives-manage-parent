package com.yintu.rixing.system;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yintu.rixing.dto.system.SysRemoteBorrowedInfoFormDto;
import com.yintu.rixing.dto.system.SysRemoteBorrowedInfoQueryDto;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 系统远程借阅记录表 服务类
 * </p>
 *
 * @author mlf
 * @since 2021-02-01
 */
public interface ISysRemoteBorrowedInfoService extends IService<SysRemoteBorrowedInfo> {

    @Transactional(rollbackFor = {Exception.class})
    void save(SysRemoteBorrowedInfoFormDto sysRemoteBorrowedInfoFormDto);

    @Transactional(rollbackFor = {Exception.class})
    void updateById(SysRemoteBorrowedInfoFormDto sysRemoteBorrowedInfoFormDto);

    Page<SysRemoteBorrowedInfo> page(SysRemoteBorrowedInfoQueryDto sysRemoteBorrowedInfoQueryDto);

}

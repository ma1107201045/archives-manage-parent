package com.yintu.rixing.system.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yintu.rixing.dto.system.SysRemoteBorrowedInfoFormDto;
import com.yintu.rixing.dto.system.SysRemoteBorrowedInfoQueryDto;
import com.yintu.rixing.enumobject.EnumAuditStatus;
import com.yintu.rixing.system.ISysRemoteBorrowedInfoService;
import com.yintu.rixing.system.SysRemoteBorrowedInfo;
import com.yintu.rixing.system.SysRemoteBorrowedInfoMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统远程借阅记录表 服务实现类
 * </p>
 *
 * @author mlf
 * @since 2021-02-01
 */
@Service
public class SysRemoteBorrowedInfoServiceImpl extends ServiceImpl<SysRemoteBorrowedInfoMapper, SysRemoteBorrowedInfo> implements ISysRemoteBorrowedInfoService {


    @Override
    public void save(SysRemoteBorrowedInfoFormDto sysRemoteBorrowedInfoFormDto) {
        SysRemoteBorrowedInfo sysRemoteBorrowedInfo = new SysRemoteBorrowedInfo();
        BeanUtil.copyProperties(sysRemoteBorrowedInfoFormDto, sysRemoteBorrowedInfo);
        sysRemoteBorrowedInfo.setAuditStatus(EnumAuditStatus.AUDIT_IN.getValue());
        this.save(sysRemoteBorrowedInfo);
    }


    @Override
    public Page<SysRemoteBorrowedInfo> page(SysRemoteBorrowedInfoQueryDto sysRemoteBorrowedInfoQueryDto) {
        Integer num = sysRemoteBorrowedInfoQueryDto.getNum();
        Integer size = sysRemoteBorrowedInfoQueryDto.getSize();
        QueryWrapper<SysRemoteBorrowedInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return this.page(new Page<>(num, size), queryWrapper);
    }
}

package com.yintu.rixing.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yintu.rixing.dto.system.SysRemoteBorrowedInfoQueryDto;
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
    public Page<SysRemoteBorrowedInfo> page(SysRemoteBorrowedInfoQueryDto sysRemoteBorrowedInfoQueryDto) {
        Integer num = sysRemoteBorrowedInfoQueryDto.getNum();
        Integer size = sysRemoteBorrowedInfoQueryDto.getSize();
        QueryWrapper<SysRemoteBorrowedInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return this.page(new Page<>(num, size), queryWrapper);
    }
}

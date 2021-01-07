package com.yintu.rixing.security.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yintu.rixing.dto.security.SecLogDto;
import com.yintu.rixing.security.ISecLogService;
import com.yintu.rixing.security.SecLog;
import com.yintu.rixing.security.SecLogMapper;
import com.yintu.rixing.system.SysUser;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 安全日志表 服务实现类
 * </p>
 *
 * @author mlf
 * @since 2020-11-26
 */
@Service
public class SecLogServiceImpl extends ServiceImpl<SecLogMapper, SecLog> implements ISecLogService {

    @Override
    public Page<SecLog> page(SecLogDto secLogDto) {
        Integer num = secLogDto.getNum();
        Integer size = secLogDto.getSize();
        String operator = secLogDto.getOperator();
        String loginIp = secLogDto.getLoginId();
        Short level = secLogDto.getLevel();
        Date startDate = secLogDto.getBeginDate();
        Date endDate = secLogDto.getEndDate();
        QueryWrapper<SecLog> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<SecLog> lambdaQueryWrapper = queryWrapper.lambda();
        if (operator != null)
            lambdaQueryWrapper.like(SecLog::getOperator, operator);
        if (loginIp != null && !"".equals(loginIp))
            lambdaQueryWrapper.eq(SecLog::getLoginIp, loginIp);
        if (secLogDto.getLevel() != null)
            lambdaQueryWrapper.eq(SecLog::getLevel, level);
        if (startDate != null && endDate != null)
            lambdaQueryWrapper.between(SecLog::getCreateTime, startDate, endDate);
        queryWrapper.orderByDesc("id");
        return this.page(new Page<>(num, size), queryWrapper);
    }
}

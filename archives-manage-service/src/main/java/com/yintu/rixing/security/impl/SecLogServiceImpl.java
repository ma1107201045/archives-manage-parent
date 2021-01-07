package com.yintu.rixing.security.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yintu.rixing.dto.security.SecLogDto;
import com.yintu.rixing.security.ISecLogService;
import com.yintu.rixing.security.SecLog;
import com.yintu.rixing.security.SecLogMapper;
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
        QueryWrapper<SecLog> queryWrapper = new QueryWrapper<>();
        String operator = secLogDto.getOperator();
        if (operator != null && !"".equals(operator))
            queryWrapper.lambda().eq(SecLog::getOperator, operator);
        String loginIp = secLogDto.getOperator();
        if (loginIp != null && !"".equals(loginIp))
            queryWrapper.lambda().eq(SecLog::getLoginIp, loginIp);
        Short level = secLogDto.getLevel();
        if (secLogDto.getLevel() != null)
            queryWrapper.lambda().eq(SecLog::getLevel, level);
        Date startDate = secLogDto.getBeginDate();
        Date endDate = secLogDto.getEndDate();
        if (startDate != null && endDate != null)
            queryWrapper.lambda().between(SecLog::getCreateTime, startDate, endDate);
        queryWrapper.orderByDesc("id");
        Integer num = secLogDto.getNum();
        Integer size = secLogDto.getSize();
        return this.page(new Page<>(num, size), queryWrapper);
    }
}

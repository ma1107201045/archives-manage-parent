package com.yintu.rixing.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yintu.rixing.dto.system.SysLogDto;
import com.yintu.rixing.system.ISysLogService;
import com.yintu.rixing.system.SysLog;
import com.yintu.rixing.system.SysLogMapper;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 系统日志表 服务实现类
 * </p>
 *
 * @author mlf
 * @since 2020-11-26
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements ISysLogService {

    @Override
    public Page<SysLog> page(SysLogDto sysLogDto) {
        QueryWrapper<SysLog> queryWrapper = new QueryWrapper<>();
        String operator = sysLogDto.getOperator();
        if (operator != null && !"".equals(operator))
            queryWrapper.lambda().eq(SysLog::getOperator, operator);
        String loginIp = sysLogDto.getOperator();
        if (loginIp != null && !"".equals(loginIp))
            queryWrapper.lambda().eq(SysLog::getLoginIp, loginIp);
        Short level = sysLogDto.getLevel();
        if (sysLogDto.getLevel() != null)
            queryWrapper.lambda().eq(SysLog::getLevel, level);
        Date startDate = sysLogDto.getBeginDate();
        Date endDate = sysLogDto.getEndDate();
        if (startDate != null && endDate != null)
            queryWrapper.lambda().between(SysLog::getCreateTime, startDate, endDate);
        queryWrapper.orderByDesc("id");
        Integer num = sysLogDto.getNum();
        Integer size = sysLogDto.getSize();
        return this.page(new Page<>(num, size), queryWrapper);
    }
}

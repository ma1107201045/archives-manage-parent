package com.yintu.rixing.system.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yintu.rixing.system.ISysLogService;
import com.yintu.rixing.system.SysLog;
import com.yintu.rixing.system.SysLogMapper;
import org.springframework.stereotype.Service;

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

}

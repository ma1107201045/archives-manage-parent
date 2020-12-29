package com.yintu.rixing.system;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yintu.rixing.dto.system.SysLogDto;
import com.yintu.rixing.dto.system.SysUserQueryDto;

/**
 * <p>
 * 系统日志表 服务类
 * </p>
 *
 * @author mlf
 * @since 2020-11-26
 */
public interface ISysLogService extends IService<SysLog> {

    Page<SysLog> page(SysLogDto sysLogDto);

}

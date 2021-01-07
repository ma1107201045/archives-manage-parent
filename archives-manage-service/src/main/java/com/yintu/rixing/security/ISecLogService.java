package com.yintu.rixing.security;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yintu.rixing.dto.security.SecLogDto;

/**
 * <p>
 * 安全日志表 服务类
 * </p>
 *
 * @author mlf
 * @since 2020-11-26
 */
public interface ISecLogService extends IService<SecLog> {

    Page<SecLog> page(SecLogDto sysLogDto);

}

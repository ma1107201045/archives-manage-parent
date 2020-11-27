package com.yintu.rixing.system;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * <p>
 * 系统用户表 服务类
 * </p>
 *
 * @author mlf
 * @since 2020-11-26
 */
public interface ISysUserService extends IService<SysUser>, UserDetailsService {
}

package com.yintu.rixing.system;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yintu.rixing.dto.system.SysUserFormDto;
import com.yintu.rixing.dto.system.SysUserQueryDto;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 系统用户表 服务类
 * </p>
 *
 * @author mlf
 * @since 2020-11-26
 */
public interface ISysUserService extends IService<SysUser>, UserDetailsService {
    @Transactional(rollbackFor = {Exception.class})
    void save(SysUserFormDto sysUserFormDto);

    @Transactional(rollbackFor = {Exception.class})
    void updateById(SysUserFormDto sysUserFormDto);

    @Transactional(rollbackFor = {Exception.class})
    void saveRolesById(Integer id, Set<Integer> roleIds);

    Page<SysUser> page(SysUserQueryDto sysUserDto);

    List<SysRole> sysRolesById(Integer id);
}

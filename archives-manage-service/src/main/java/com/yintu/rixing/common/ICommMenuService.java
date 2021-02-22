package com.yintu.rixing.common;

import com.yintu.rixing.system.SysUser;
import com.yintu.rixing.util.TreeUtil;
import com.yintu.rixing.vo.common.CommAuthorityVo;
import com.yintu.rixing.vo.common.CommPermissionVo;

import java.util.List;

/**
 * @Author: mlf
 * @Date: 2021/1/4 10:39:12
 * @Version: 1.0
 */
public interface ICommMenuService {

    /**
     * 查询全部菜单
     *
     * @param permissionId 主键id
     * @return 菜单树信息集
     */
    List<TreeUtil> findMenus(Integer permissionId, Integer userId);


    /**
     * 查询全部权限
     *
     * @param userId 用户id
     * @return 权限树信息集
     */
    List<CommAuthorityVo> findAuthorities(Integer userId);

    /**
     * 获取菜单跟权限
     *
     * @param sysUser 系统用户信息
     * @return ..
     */
    CommPermissionVo findPermissions(SysUser sysUser);


}

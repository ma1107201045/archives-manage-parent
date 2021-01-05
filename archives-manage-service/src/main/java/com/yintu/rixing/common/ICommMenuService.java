package com.yintu.rixing.common;

import com.yintu.rixing.util.TreeUtil;

import java.util.List;

/**
 * @Author: mlf
 * @Date: 2021/1/4 10:39:12
 * @Version: 1.0
 */
public interface ICommMenuService {


    /**
     * 查询全部权限
     *
     * @param parentId 父级id
     * @param menu     是否是菜单项
     * @return 权限树信息集
     */
    List<TreeUtil> findMenus(Integer parentId, Short menu);

    /**
     * 通过用户id查询权限(用户菜单栏)
     *
     * @param id       用户id
     * @param parentId 父级id
     * @param menu     是否是菜单项
     * @return 权限树信息集
     */
    List<TreeUtil> findMenusByUserId(Integer id, Integer parentId, Short menu);

}

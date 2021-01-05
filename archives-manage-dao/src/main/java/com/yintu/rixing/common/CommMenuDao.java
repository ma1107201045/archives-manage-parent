package com.yintu.rixing.common;

import com.yintu.rixing.vo.common.CommMenuVo;

import java.util.List;

/**
 * @Author: mlf
 * @Date: 2021/1/4 10:42:51
 * @Version: 1.0
 */
public interface CommMenuDao {

    /**
     * 查询全部权限
     *
     * @param parentId 父级id
     * @param menu     是否是菜单项
     * @return 菜单栏信息集
     */
    List<CommMenuVo> selectByExample(Integer parentId, Short menu);

    /**
     * 通过用户id查询权限(用户菜单栏)
     *
     * @param parentId 父级id
     * @param menu     是否是菜单项
     * @param userId   用户id
     * @return 菜单栏信息集
     */
    List<CommMenuVo> selectByExampleAndUserId(Integer parentId, Short menu, Integer userId);
}

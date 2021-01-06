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
     * @param permissionId 主键id
     * @return 权限树信息集
     */
    List<TreeUtil> findMenus(Integer permissionId, Integer userId);


}

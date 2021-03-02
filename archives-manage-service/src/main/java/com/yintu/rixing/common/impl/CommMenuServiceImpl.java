package com.yintu.rixing.common.impl;

import cn.hutool.core.bean.BeanUtil;
import com.yintu.rixing.common.CommMenuMapper;
import com.yintu.rixing.common.ICommMenuService;
import com.yintu.rixing.enumobject.EnumAuthType;
import com.yintu.rixing.enumobject.EnumFlag;
import com.yintu.rixing.system.SysUser;
import com.yintu.rixing.util.TreeUtil;
import com.yintu.rixing.vo.common.CommAuthorityVo;
import com.yintu.rixing.vo.common.CommMenuVo;
import com.yintu.rixing.vo.common.CommPermissionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: mlf
 * @Date: 2021/1/4 10:40:02
 * @Version: 1.0
 */
@Service
public class CommMenuServiceImpl implements ICommMenuService {

    @Autowired
    private CommMenuMapper commMenuDao;

    @Override
    public List<TreeUtil> findMenus(Integer permissionId, Integer userId) {
        Short menu = EnumFlag.True.getValue();
        List<CommMenuVo> commMenuVos = userId == null ? commMenuDao.selectByExample(permissionId, menu) :
                commMenuDao.selectByExampleAndUserId(permissionId, menu, userId);
        List<TreeUtil> treeUtils = new ArrayList<>();
        for (CommMenuVo commMenuVo : commMenuVos) {
            TreeUtil treeUtil = new TreeUtil();
            treeUtil.setId(commMenuVo.getId().longValue());
            treeUtil.setLabel(commMenuVo.getName());
            treeUtil.setA_attr(BeanUtil.beanToMap(commMenuVo));
            treeUtil.setChildren(this.findMenus(commMenuVo.getId(), userId));
            treeUtils.add(treeUtil);
        }
        return treeUtils;
    }

    @Override
    public List<CommAuthorityVo> findAuthorities(Integer userId) {
        Short menu = EnumFlag.False.getValue();
        return userId == null ? commMenuDao.selectAuthorityByExample(menu)
                : commMenuDao.selectAuthorityByExampleAndUserId(menu, userId);
    }


    @Override
    public CommPermissionVo findPermissions(SysUser sysUser) {
        CommPermissionVo commPermissionVo = new CommPermissionVo();
        List<TreeUtil> treeUtils = null;
        List<CommAuthorityVo> commAuthorityVos = null;
        if (sysUser != null) {
            if (sysUser.getAuthType().equals(EnumAuthType.ADMIN.getValue())) {
                treeUtils = this.findMenus(TreeUtil.ROOT_PARENT_ID, null);
                commAuthorityVos = this.findAuthorities(null);
            } else {
                Integer userId = sysUser.getId();
                treeUtils = this.findMenus(TreeUtil.ROOT_PARENT_ID, userId);
                this.findAuthorities(userId);
            }
        }
        commPermissionVo.setMenu(treeUtils);
        commPermissionVo.setAuthorities(commAuthorityVos);
        return commPermissionVo;
    }
}

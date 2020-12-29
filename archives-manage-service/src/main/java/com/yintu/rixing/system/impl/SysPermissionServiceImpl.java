package com.yintu.rixing.system.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yintu.rixing.dto.system.SysPermissionFormDto;
import com.yintu.rixing.system.*;
import com.yintu.rixing.util.TreeNodeUtil;
import org.springframework.stereotype.Service;

import java.time.Period;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 系统权限表 服务实现类
 * </p>
 *
 * @author mlf
 * @since 2020-11-26
 */
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements ISysPermissionService {


    @Override
    public void save(SysPermissionFormDto sysPermissionFormDto) {
        SysPermission sysPermission = new SysPermission();
        BeanUtil.copyProperties(sysPermissionFormDto, sysPermission);
        this.save(sysPermission);
    }

    @Override
    public void updateById(SysPermissionFormDto sysPermissionFormDto) {
        SysPermission sysPermission = this.getById(sysPermissionFormDto.getId());
        if (sysPermission != null) {
            BeanUtil.copyProperties(sysPermissionFormDto, sysPermission);
            this.updateById(sysPermission);
        }
    }


    @Override
    public List<TreeNodeUtil> listTree(Integer parentId) {
        QueryWrapper<SysPermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysPermission::getParentId, parentId);
        List<SysPermission> sysPermissions = this.list(queryWrapper);
        List<TreeNodeUtil> treeNodeUtils = new ArrayList<>();
        for (SysPermission sysPermission : sysPermissions) {
            TreeNodeUtil treeNodeUtil = new TreeNodeUtil();
            treeNodeUtil.setId(sysPermission.getId().longValue());
            treeNodeUtil.setLabel(sysPermission.getName());
            treeNodeUtil.setIcon(sysPermission.getImgPath());
            treeNodeUtil.setChildren(this.listTree(sysPermission.getId()));
            treeNodeUtils.add(treeNodeUtil);
        }
        return treeNodeUtils;
    }
}

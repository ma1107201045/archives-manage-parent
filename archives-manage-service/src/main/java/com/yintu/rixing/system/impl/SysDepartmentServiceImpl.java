package com.yintu.rixing.system.impl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yintu.rixing.dto.system.SysDepartmentFormDto;
import com.yintu.rixing.dto.system.SysPermissionFormDto;
import com.yintu.rixing.system.ISysDepartmentService;
import com.yintu.rixing.system.SysDepartment;
import com.yintu.rixing.system.SysDepartmentMapper;
import com.yintu.rixing.system.SysPermission;
import com.yintu.rixing.util.TreeNodeUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 部门表 服务实现类
 * </p>
 *
 * @author mlf
 * @since 2020-12-22
 */
@Service
public class SysDepartmentServiceImpl extends ServiceImpl<SysDepartmentMapper, SysDepartment> implements ISysDepartmentService {


    @Override
    public void save(SysDepartmentFormDto sysDepartmentFormDto) {
        SysDepartment sysPermission = new SysDepartment();
        BeanUtil.copyProperties(sysDepartmentFormDto, sysPermission);
        this.save(sysPermission);
    }

    @Override
    public void updateById(SysDepartmentFormDto sysDepartmentFormDto) {
        SysDepartment sysDepartment = this.getById(sysDepartmentFormDto.getId());
        if (sysDepartment != null) {
            BeanUtil.copyProperties(sysDepartmentFormDto, sysDepartment);
            this.updateById(sysDepartment);
        }
    }

    @Override
    public List<TreeNodeUtil> listTree(Integer parentId) {
        QueryWrapper<SysDepartment> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysDepartment::getParentId, parentId);
        List<SysDepartment> sysPermissions = this.list(queryWrapper);
        List<TreeNodeUtil> treeNodeUtils = new ArrayList<>();
        for (SysDepartment sysDepartment : sysPermissions) {
            TreeNodeUtil treeNodeUtil = new TreeNodeUtil();
            treeNodeUtil.setId(sysDepartment.getId().longValue());
            treeNodeUtil.setLabel(sysDepartment.getName());
            treeNodeUtil.setChildren(this.listTree(sysDepartment.getId()));
            treeNodeUtils.add(treeNodeUtil);
        }
        return treeNodeUtils;
    }
}

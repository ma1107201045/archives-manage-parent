package com.yintu.rixing.system.impl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yintu.rixing.dto.system.SysDepartmentFormDto;
import com.yintu.rixing.system.ISysDepartmentService;
import com.yintu.rixing.system.SysDepartment;
import com.yintu.rixing.system.SysDepartmentMapper;
import com.yintu.rixing.util.TreeUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 系统部门表 服务实现类
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
    public void removeTree(Integer id) {
        this.removeById(id);
        QueryWrapper<SysDepartment> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysDepartment::getParentId, id);
        List<SysDepartment> sysDepartments = this.list(queryWrapper);
        for (SysDepartment sysDepartment : sysDepartments) {
            this.removeTree(sysDepartment.getId());
        }
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
    public List<TreeUtil> listTree(Integer id) {
        QueryWrapper<SysDepartment> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysDepartment::getParentId, id);
        List<SysDepartment> sysPermissions = this.list(queryWrapper);
        List<TreeUtil> treeNodeUtils = new ArrayList<>();
        for (SysDepartment sysDepartment : sysPermissions) {
            TreeUtil treeUtil = new TreeUtil();
            treeUtil.setId(sysDepartment.getId().longValue());
            treeUtil.setLabel(sysDepartment.getName());
            treeUtil.setA_attr(BeanUtil.beanToMap(sysDepartment));
            treeUtil.setChildren(this.listTree(sysDepartment.getId()));
            treeNodeUtils.add(treeUtil);
        }
        return treeNodeUtils;
    }


}

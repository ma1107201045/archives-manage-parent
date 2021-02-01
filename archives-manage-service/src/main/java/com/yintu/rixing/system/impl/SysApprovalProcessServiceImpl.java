package com.yintu.rixing.system.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yintu.rixing.dto.system.SysApprovalProcessFormDto;
import com.yintu.rixing.dto.system.SysApprovalProcessQueryDto;
import com.yintu.rixing.exception.BaseRuntimeException;
import com.yintu.rixing.system.*;
import com.yintu.rixing.util.TreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统审批流程表 服务实现类
 * </p>
 *
 * @author mlf
 * @since 2021-01-16
 */
@Service
public class SysApprovalProcessServiceImpl extends ServiceImpl<SysApprovalProcessMapper, SysApprovalProcess> implements ISysApprovalProcessService {

    @Autowired
    private ISysApprovalProcessConfigurationService iSysApprovalProcessConfigurationService;
    @Autowired
    private ISysRoleService iSysRoleService;
    @Autowired
    private ISysUserService iSysUserService;

    @Override
    public void save(SysApprovalProcessFormDto sysApprovalProcessFormDto) {
        SysApprovalProcess sysApprovalProcess = new SysApprovalProcess();
        BeanUtil.copyProperties(sysApprovalProcessFormDto, sysApprovalProcess);
        this.save(sysApprovalProcess);
        sysApprovalProcessFormDto.setId(sysApprovalProcess.getId());
        this.saveSysApprovalProcessConfigurations(sysApprovalProcessFormDto, true);
    }


    @Override
    public void updateById(SysApprovalProcessFormDto sysApprovalProcessFormDto) {
        Integer id = sysApprovalProcessFormDto.getId();
        SysApprovalProcess sysApprovalProcess = this.getById(id);
        if (sysApprovalProcess != null) {
            BeanUtil.copyProperties(sysApprovalProcessFormDto, sysApprovalProcess);
            this.updateById(sysApprovalProcess);
            this.saveSysApprovalProcessConfigurations(sysApprovalProcessFormDto, false);
        }
    }

    @Override
    public void saveSysApprovalProcessConfigurations(SysApprovalProcessFormDto sysApprovalProcessFormDto, Boolean isSave) {
        Integer id = sysApprovalProcessFormDto.getId();
        List<Integer> roleIds = sysApprovalProcessFormDto.getRoleIds();
        List<Integer> userIds = sysApprovalProcessFormDto.getUserIds();
        List<Integer> orders = sysApprovalProcessFormDto.getOrders();
        if (roleIds == null || userIds == null || orders == null || roleIds.size() == 0 || userIds.size() == 0 || orders.size() == 0)
            throw new BaseRuntimeException("角色或者用户或者顺序不能为空");
        if (roleIds.size() != userIds.size() || userIds.size() != orders.size())
            throw new BaseRuntimeException("角色或者用户或者顺序长度不一致");
        List<Integer> ids = this.listByApprovalType((short) 2);
        if (isSave) {
            if (!ids.isEmpty())
                throw new BaseRuntimeException("此审批类型只能配置一条");
        } else {
            if (!ids.isEmpty() && !ids.get(0).equals(id))
                throw new BaseRuntimeException("此审批类型只能配置一条");
            QueryWrapper<SysApprovalProcessConfiguration> sysApprovalProcessConfigurationQueryWrapper = new QueryWrapper<>();
            sysApprovalProcessConfigurationQueryWrapper.lambda().eq(SysApprovalProcessConfiguration::getApprovalProcessId, id);
            iSysApprovalProcessConfigurationService.remove(sysApprovalProcessConfigurationQueryWrapper);
        }
        List<SysApprovalProcessConfiguration> sysApprovalProcessConfigurations = new ArrayList<>();
        for (int i = 0; i < roleIds.size(); i++) {
            SysApprovalProcessConfiguration sysApprovalProcessConfiguration = new SysApprovalProcessConfiguration();
            sysApprovalProcessConfiguration.setApprovalProcessId(id);
            sysApprovalProcessConfiguration.setRoleId(roleIds.get(i));
            sysApprovalProcessConfiguration.setUserId(userIds.get(i));
            sysApprovalProcessConfiguration.setOrder(orders.get(i));
            sysApprovalProcessConfigurations.add(sysApprovalProcessConfiguration);
        }
        iSysApprovalProcessConfigurationService.saveBatch(sysApprovalProcessConfigurations);

    }

    @Override
    public List<Integer> listByApprovalType(Short approvalType) {
        if (approvalType == null)
            throw new BaseRuntimeException("审批类型不能为空");
        QueryWrapper<SysApprovalProcess> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .select(SysApprovalProcess::getId)
                .eq(SysApprovalProcess::getApprovalType, approvalType);
        return this.listObjs(queryWrapper, id -> (Integer) id);
    }


    @Override
    public Page<SysApprovalProcess> page(SysApprovalProcessQueryDto sysApprovalProcessQueryDto) {
        Integer num = sysApprovalProcessQueryDto.getNum();
        Integer size = sysApprovalProcessQueryDto.getSize();
        String name = sysApprovalProcessQueryDto.getName();
        Short approvalType = sysApprovalProcessQueryDto.getApprovalType();
        QueryWrapper<SysApprovalProcess> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<SysApprovalProcess> lambdaQueryWrapper = queryWrapper.lambda();
        lambdaQueryWrapper.like(SysApprovalProcess::getName, name == null ? "" : name);
        if (approvalType != null)
            lambdaQueryWrapper.eq(SysApprovalProcess::getApprovalType, approvalType);
        queryWrapper.orderByDesc("id");
        return this.page(new Page<>(num, size), queryWrapper);
    }

    @Override
    public List<TreeUtil> tree() {
        List<SysRole> sysRoles = iSysRoleService.list();
        List<TreeUtil> firstTreeNodeUtils = new ArrayList<>();
        for (SysRole sysRole : sysRoles) {
            List<SysUser> sysUsers = iSysRoleService.sysUsersById(sysRole.getId());
            List<TreeUtil> secondTreeNodeUtils = new ArrayList<>();
            TreeUtil firstTreeUtil = new TreeUtil();
            firstTreeUtil.setId(sysRole.getId().longValue());
            firstTreeUtil.setLabel(sysRole.getName());
            firstTreeUtil.setChildren(secondTreeNodeUtils);
            firstTreeNodeUtils.add(firstTreeUtil);
            for (SysUser sysUser : sysUsers) {
                TreeUtil secondTreeUtil = new TreeUtil();
                secondTreeUtil.setId(sysUser.getId().longValue());
                secondTreeUtil.setLabel(sysUser.getNickname());
                secondTreeUtil.setA_attr(BeanUtil.beanToMap(sysRole));
                secondTreeNodeUtils.add(secondTreeUtil);
            }
        }
        return firstTreeNodeUtils;
    }

    @Override
    public List<List<TreeUtil>> treeById(Integer id) {
        List<List<TreeUtil>> lists = new ArrayList<>();
        SysApprovalProcess sysApprovalProcess = this.getById(id);
        if (sysApprovalProcess != null) {
            QueryWrapper<SysApprovalProcessConfiguration> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(SysApprovalProcessConfiguration::getApprovalProcessId, id);
            List<SysApprovalProcessConfiguration> sysApprovalProcessConfigurations = iSysApprovalProcessConfigurationService.list(queryWrapper);
            //1.先按照顺序字段分组
            Map<Integer, List<SysApprovalProcessConfiguration>> groupMapByOrder = sysApprovalProcessConfigurations.stream().collect(Collectors.groupingBy(SysApprovalProcessConfiguration::getOrder));
            for (Integer sort : groupMapByOrder.keySet()) {
                List<SysApprovalProcessConfiguration> eachOrder = groupMapByOrder.get(sort);
                List<Integer> roleIds = eachOrder.stream().map(SysApprovalProcessConfiguration::getRoleId).collect(Collectors.toList());
                List<SysRole> sysRoles = iSysRoleService.listByIds(roleIds);
                List<TreeUtil> firstTreeNodeUtils = new ArrayList<>();
                for (SysRole sysRole : sysRoles) {
                    List<Integer> userIds = eachOrder.stream().filter(sysApprovalProcessConfiguration -> sysApprovalProcessConfiguration.getRoleId().equals(sysRole.getId())).map(SysApprovalProcessConfiguration::getUserId).collect(Collectors.toList());
                    List<SysUser> sysUsers = iSysUserService.listByIds(userIds);
                    List<TreeUtil> secondTreeNodeUtils = new ArrayList<>();
                    TreeUtil firstTreeUtil = new TreeUtil();
                    firstTreeUtil.setId(sysRole.getId().longValue());
                    firstTreeUtil.setLabel(sysRole.getName());
                    firstTreeUtil.setChildren(secondTreeNodeUtils);
                    firstTreeNodeUtils.add(firstTreeUtil);
                    for (SysUser sysUser : sysUsers) {
                        TreeUtil secondTreeUtil = new TreeUtil();
                        secondTreeUtil.setId(sysUser.getId().longValue());
                        secondTreeUtil.setLabel(sysUser.getNickname());
                        secondTreeUtil.setA_attr(BeanUtil.beanToMap(sysRole));
                        secondTreeNodeUtils.add(secondTreeUtil);
                    }
                }
                lists.add(firstTreeNodeUtils);
            }
        }
        return lists;
    }
}

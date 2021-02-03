package com.yintu.rixing.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yintu.rixing.system.SysApprovalProcessConfigurationMapper;
import com.yintu.rixing.system.SysApprovalProcessConfiguration;
import com.yintu.rixing.system.ISysApprovalProcessConfigurationService;
import com.yintu.rixing.util.AssertUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 系统审批流程配置中间表 服务实现类
 * </p>
 *
 * @author mlf
 * @since 2021-01-16
 */
@Service
public class SysApprovalProcessConfigurationServiceImpl extends ServiceImpl<SysApprovalProcessConfigurationMapper, SysApprovalProcessConfiguration> implements ISysApprovalProcessConfigurationService {

    @Override
    public List<SysApprovalProcessConfiguration> listByApprovalProcessId(Integer approvalProcessId) {
        AssertUtil.notNull(approvalProcessId, "审批流id不能为空");
        QueryWrapper<SysApprovalProcessConfiguration> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysApprovalProcessConfiguration::getApprovalProcessId, approvalProcessId);
        return this.list(queryWrapper);
    }
}

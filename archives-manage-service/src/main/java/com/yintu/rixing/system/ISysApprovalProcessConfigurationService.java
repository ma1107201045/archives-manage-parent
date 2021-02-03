package com.yintu.rixing.system;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 系统审批流程配置中间表 服务类
 * </p>
 *
 * @author mlf
 * @since 2021-01-16
 */
public interface ISysApprovalProcessConfigurationService extends IService<SysApprovalProcessConfiguration> {

    List<SysApprovalProcessConfiguration> listByApprovalProcessId(Integer approvalProcessId);
}

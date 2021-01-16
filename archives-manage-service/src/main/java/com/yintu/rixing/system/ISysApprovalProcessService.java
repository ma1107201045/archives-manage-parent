package com.yintu.rixing.system;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yintu.rixing.dto.system.SysApprovalProcessFormDto;
import com.yintu.rixing.dto.system.SysApprovalProcessQueryDto;
import com.yintu.rixing.util.TreeUtil;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 系统审批流程表 服务类
 * </p>
 *
 * @author mlf
 * @since 2021-01-16
 */
public interface ISysApprovalProcessService extends IService<SysApprovalProcess> {

    @Transactional(rollbackFor = {Exception.class})
    void save(SysApprovalProcessFormDto sysApprovalProcessFormDto);

    void parametersToProofread(SysApprovalProcessFormDto sysApprovalProcessFormDto);

    @Transactional(rollbackFor = {Exception.class})
    void updateById(SysApprovalProcessFormDto sysApprovalProcessFormDto);

    @Transactional(rollbackFor = {Exception.class})
    void saveSysApprovalProcessConfigurations(SysApprovalProcessFormDto sysApprovalProcessFormDto, Boolean isSave);

    Page<SysApprovalProcess> page(SysApprovalProcessQueryDto sysApprovalProcessQueryDto);

    List<TreeUtil> tree();

    List<List<TreeUtil>> treeById(Integer id);

}

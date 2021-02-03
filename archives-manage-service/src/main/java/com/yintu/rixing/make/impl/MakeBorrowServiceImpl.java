package com.yintu.rixing.make.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yintu.rixing.dto.make.MakeBorrowRemoteFormDto;
import com.yintu.rixing.dto.make.MakeBorrowRemoteQueryDto;
import com.yintu.rixing.enumobject.EnumAuditStatus;
import com.yintu.rixing.enumobject.EnumFlag;
import com.yintu.rixing.exception.BaseRuntimeException;
import com.yintu.rixing.make.*;
import com.yintu.rixing.system.ISysApprovalProcessConfigurationService;
import com.yintu.rixing.system.ISysApprovalProcessService;
import com.yintu.rixing.system.SysApprovalProcess;
import com.yintu.rixing.system.SysApprovalProcessConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 利用中心的借阅申请表 服务实现类
 * </p>
 *
 * @author mlf
 * @since 2021-02-03
 */
@Service
public class MakeBorrowServiceImpl extends ServiceImpl<MakeBorrowMapper, MakeBorrow> implements IMakeBorrowService {
    @Autowired
    private ISysApprovalProcessService iSysApprovalProcessService;
    @Autowired
    private ISysApprovalProcessConfigurationService iSysApprovalProcessConfigurationService;
    @Autowired
    private IMakeBorrowAuditorService iMakeBorrowAuditorService;

    @Override
    public void saveRemote(MakeBorrowRemoteFormDto makeBorrowFormElectronicDto) {
        MakeBorrow makeBorrow = new MakeBorrow();
        BeanUtil.copyProperties(makeBorrowFormElectronicDto, makeBorrow);
        makeBorrow.setUserType((short) 1);
        makeBorrow.setAuditStatus(EnumAuditStatus.AUDIT_IN.getValue());
        makeBorrow.setPreviewType(EnumFlag.False.getValue());
        this.save(makeBorrow);
        List<Integer> ids = iSysApprovalProcessService.listByApprovalType((short) 2);
        if (ids.isEmpty())
            throw new BaseRuntimeException("管理员没有设置审批人员");
        List<SysApprovalProcessConfiguration> sysApprovalProcessConfigurations = iSysApprovalProcessConfigurationService.listByApprovalProcessId(ids.get(0));
        List<MakeBorrowAuditor> makeBorrowAuditors = new ArrayList<>();
        Integer makeBorrowId = makeBorrow.getId();
        for (SysApprovalProcessConfiguration sysApprovalProcessConfiguration : sysApprovalProcessConfigurations) {
            MakeBorrowAuditor makeBorrowAuditor = new MakeBorrowAuditor();
            makeBorrowAuditor.setMakeBorrowId(makeBorrowId);
            makeBorrowAuditor.setAuditorId(sysApprovalProcessConfiguration.getUserId());
            Short order = sysApprovalProcessConfiguration.getOrder();
            makeBorrowAuditor.setSort(order);
            if (order == 1) {
                makeBorrowAuditor.setActivate(EnumFlag.True.getValue());
            } else {
                makeBorrowAuditor.setActivate(EnumFlag.False.getValue());
            }
            makeBorrowAuditor.setIsDispose(EnumFlag.False.getValue());
            makeBorrowAuditor.setAuditStatus(EnumAuditStatus.AUDIT_IN.getValue());
            makeBorrowAuditors.add(makeBorrowAuditor);
        }
        iMakeBorrowAuditorService.saveBatch(makeBorrowAuditors);
    }

    @Override
    public Page<MakeBorrow> pageRemote(MakeBorrowRemoteQueryDto makeBorrowQueryElectronicDto) {
        Integer num = makeBorrowQueryElectronicDto.getNum();
        Integer size = makeBorrowQueryElectronicDto.getSize();
        Integer userId = makeBorrowQueryElectronicDto.getUserId();
        return null;
    }
}

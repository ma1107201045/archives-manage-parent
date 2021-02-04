package com.yintu.rixing.make.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yintu.rixing.data.DataArchivesLibraryFile;
import com.yintu.rixing.data.IDataArchivesLibraryFileService;
import com.yintu.rixing.data.IDataFormalLibraryService;
import com.yintu.rixing.dto.make.MakeBorrowRemoteFormDto;
import com.yintu.rixing.dto.make.MakeBorrowRemoteQueryDto;
import com.yintu.rixing.enumobject.EnumArchivesLibraryDefaultField;
import com.yintu.rixing.enumobject.EnumAuditStatus;
import com.yintu.rixing.enumobject.EnumFlag;
import com.yintu.rixing.exception.BaseRuntimeException;
import com.yintu.rixing.make.*;
import com.yintu.rixing.system.*;
import com.yintu.rixing.vo.make.MakeArchivesSearchElectronicVo;
import com.yintu.rixing.vo.make.MakeBorrowRemoteVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private ISysArchivesLibraryService iSysArchivesLibraryService;
    @Autowired
    private IDataArchivesLibraryFileService iDataArchivesLibraryFileService;
    @Autowired
    private IDataFormalLibraryService iDataFormalLibraryService;
    @Autowired
    private ISysRemoteUserService iSysRemoteUserService;
    @Autowired
    private IMakeBorrowPurposeService iMakeBorrowPurposeService;

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
            Integer order = sysApprovalProcessConfiguration.getOrder();
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
    public Page<MakeBorrowRemoteVo> pageRemote(MakeBorrowRemoteQueryDto makeBorrowQueryElectronicDto) {
        Page<MakeBorrowRemoteVo> page1 = new Page<>();
        Integer num = makeBorrowQueryElectronicDto.getNum();
        Integer size = makeBorrowQueryElectronicDto.getSize();
        Integer userId = makeBorrowQueryElectronicDto.getUserId();
        QueryWrapper<MakeBorrow> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MakeBorrow::getUserId, userId);
        Page<MakeBorrow> page2 = this.page(new Page<>(num, size), queryWrapper);
        BeanUtil.copyProperties(page2, page1, "records");
        List<MakeBorrow> makeBorrows = page2.getRecords();
        List<MakeBorrowRemoteVo> makeBorrowRemoteVos = new ArrayList<>();
        for (MakeBorrow makeBorrow : makeBorrows) {
            Integer fileId = makeBorrow.getFileid();
            Integer borrowPurposeId = makeBorrow.getBorrowPurposeId();
            MakeBorrowRemoteVo makeBorrowRemoteVo = new MakeBorrowRemoteVo();
            BeanUtil.copyProperties(makeBorrow, makeBorrowRemoteVo);
            DataArchivesLibraryFile dataArchivesLibraryFile = iDataArchivesLibraryFileService.getById(fileId);
            if (dataArchivesLibraryFile != null) {
                makeBorrowRemoteVo.setArchivesFileId(fileId);
                makeBorrowRemoteVo.setArchivesFileOriginalName(dataArchivesLibraryFile.getOriginalName());
                Integer archivesLibraryId = dataArchivesLibraryFile.getArchivesLibraryId();
                SysArchivesLibrary sysArchivesLibrary = iSysArchivesLibraryService.getById(archivesLibraryId);
                makeBorrowRemoteVo.setArchivesLibName(sysArchivesLibrary.getName());
                Map<String, Object> map = iDataFormalLibraryService.getById(fileId, archivesLibraryId);
                if (map != null) {
                    makeBorrowRemoteVo.setArchivesDirectoryNum((String) map.get(EnumArchivesLibraryDefaultField.ARCHIVES_NUM.getDataKey()));
                    makeBorrowRemoteVo.setArchivesDirectoryTopicName((String) map.get(EnumArchivesLibraryDefaultField.TOPIC_NAME.getDataKey()));
                    makeBorrowRemoteVo.setArchivesDirectoryRetentionPeriod(map.get(EnumArchivesLibraryDefaultField.RETENTION_PERIOD.getDataKey()));
                    makeBorrowRemoteVo.setArchivesDirectoryValidPeriod(map.get(EnumArchivesLibraryDefaultField.VALID_PERIOD.getDataKey()));
                    makeBorrowRemoteVo.setArchivesDirectorySecurityLevel(map.get(EnumArchivesLibraryDefaultField.SECURITY_LEVEL.getDataKey()));
                    makeBorrowRemoteVo.setArchivesDirectoryFilingAnnual((String) map.get(EnumArchivesLibraryDefaultField.FILING_ANNUAL.getDataKey()));
                }
            }
            SysRemoteUser sysRemoteUser = iSysRemoteUserService.getById(userId);
            if (sysRemoteUser != null) {
                makeBorrowRemoteVo.setUserName(sysRemoteUser.getUsername());
                makeBorrowRemoteVo.setCertificateNumber(sysRemoteUser.getCertificateNumber());
            }
            MakeBorrowPurpose makeBorrowPurpose = iMakeBorrowPurposeService.getById(borrowPurposeId);
            if (makeBorrowPurpose != null) {
                makeBorrowRemoteVo.setBorrowPurposeName(makeBorrowPurpose.getName());
            }
            makeBorrowRemoteVos.add(makeBorrowRemoteVo);
        }
        page1.setRecords(makeBorrowRemoteVos);
        return page1;
    }
}

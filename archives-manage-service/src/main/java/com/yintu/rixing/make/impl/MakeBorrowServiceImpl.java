package com.yintu.rixing.make.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yintu.rixing.data.DataArchivesLibraryFile;
import com.yintu.rixing.data.IDataArchivesLibraryFileService;
import com.yintu.rixing.data.IDataFormalLibraryService;
import com.yintu.rixing.dto.make.MakeBorrowRemoteFormDto;
import com.yintu.rixing.dto.make.MakeBorrowQueryDto;
import com.yintu.rixing.enumobject.EnumArchivesLibraryDefaultField;
import com.yintu.rixing.enumobject.EnumAuditStatus;
import com.yintu.rixing.enumobject.EnumFlag;
import com.yintu.rixing.exception.BaseRuntimeException;
import com.yintu.rixing.make.*;
import com.yintu.rixing.system.*;
import com.yintu.rixing.util.TableNameUtil;
import com.yintu.rixing.vo.make.MakeBorrowVo;
import com.yintu.rixing.warehouse.IWareTemplateLibraryFieldService;
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
    @Autowired
    private IWareTemplateLibraryFieldService iWareTemplateLibraryFieldService;
    @Autowired
    private ISysUserService iSysUserService;

    @Override
    public void saveRemote(MakeBorrowRemoteFormDto makeBorrowRemoteFormDto) {
        //判断同一个人是否借阅同一文件
        Integer fileId = makeBorrowRemoteFormDto.getFileid();
        Integer userId = makeBorrowRemoteFormDto.getUserId();
        Short borrowType = makeBorrowRemoteFormDto.getBorrowType();
        QueryWrapper<MakeBorrow> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(MakeBorrow::getFileid, fileId)
                .eq(MakeBorrow::getUserId, userId)
                .eq(MakeBorrow::getUserType, (short) 2)
                .eq(MakeBorrow::getBorrowType, borrowType);
        List<MakeBorrow> makeBorrows = this.list(queryWrapper);
        if (!makeBorrows.isEmpty()) {
            MakeBorrow makeBorrow = makeBorrows.get(makeBorrows.size() - 1);
            //审核中或者审核拒绝或者可预览都不能借阅（审批通过并且不可预览才可以借阅）
            if (!EnumAuditStatus.AUDIT_PASS.getValue().equals(makeBorrow.getAuditStatus()) || EnumFlag.True.getValue().equals(makeBorrow.getPreviewType()))
                throw new BaseRuntimeException("无需重复借阅");
        }
        MakeBorrow makeBorrow = new MakeBorrow();
        BeanUtil.copyProperties(makeBorrowRemoteFormDto, makeBorrow);
        makeBorrow.setUserType((short) 2);
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
    public void approve(Integer id) {


    }

    @Override
    public Page<MakeBorrowVo> page(MakeBorrowQueryDto makeBorrowQueryDto) {
        Page<MakeBorrowVo> page1 = new Page<>();
        Integer num = makeBorrowQueryDto.getNum();
        Integer size = makeBorrowQueryDto.getSize();
        Integer userId = makeBorrowQueryDto.getUserId();
        Short userType = makeBorrowQueryDto.getUserType();
        Short borrowType = makeBorrowQueryDto.getBorrowType();
        QueryWrapper<MakeBorrow> queryWrapper = new QueryWrapper<>();
        if (userId != null && userType != null)
            queryWrapper.lambda().eq(MakeBorrow::getUserId, userId).eq(MakeBorrow::getUserType, userType);
        if (borrowType != null)
            queryWrapper.lambda().eq(MakeBorrow::getBorrowType, borrowType);
        Page<MakeBorrow> page2 = this.page(new Page<>(num, size), queryWrapper);
        BeanUtil.copyProperties(page2, page1, "records");
        List<MakeBorrow> makeBorrows = page2.getRecords();
        List<MakeBorrowVo> makeBorrowVos = new ArrayList<>();
        for (MakeBorrow makeBorrow : makeBorrows) {
            Integer fileId = makeBorrow.getFileid();
            Integer makeId = makeBorrow.getMakeId();
            Short b = makeBorrow.getBorrowType();
            Integer uId = makeBorrow.getUserId();
            Short uType = makeBorrow.getUserType();
            MakeBorrowVo makeBorrowVo = new MakeBorrowVo();
            BeanUtil.copyProperties(makeBorrow, makeBorrowVo);
            if (b == 1) {//电子借阅
                DataArchivesLibraryFile dataArchivesLibraryFile = iDataArchivesLibraryFileService.getById(fileId);
                if (dataArchivesLibraryFile != null) {
                    makeBorrowVo.setArchivesFileId(fileId);
                    makeBorrowVo.setArchivesFileOriginalName(dataArchivesLibraryFile.getOriginalName());
                    Integer archivesLibraryId = dataArchivesLibraryFile.getArchivesLibraryId();
                    Integer dataId = dataArchivesLibraryFile.getDataId();
                    SysArchivesLibrary sysArchivesLibrary = iSysArchivesLibraryService.getById(archivesLibraryId);
                    makeBorrowVo.setArchivesLibName(sysArchivesLibrary.getName());
                    Map<String, Object> map = iDataFormalLibraryService.getById(dataId, archivesLibraryId);
                    if (map != null) {
                        makeBorrowVo.setArchivesDirectoryNum((String) map.get(EnumArchivesLibraryDefaultField.ARCHIVES_NUM.getDataKey()));
                        makeBorrowVo.setArchivesDirectoryTopicName((String) map.get(EnumArchivesLibraryDefaultField.TOPIC_NAME.getDataKey()));
                        makeBorrowVo.setArchivesDirectoryRetentionPeriod(map.get(EnumArchivesLibraryDefaultField.RETENTION_PERIOD.getDataKey()));
                        makeBorrowVo.setArchivesDirectoryValidPeriod(map.get(EnumArchivesLibraryDefaultField.VALID_PERIOD.getDataKey()));
                        makeBorrowVo.setArchivesDirectorySecurityLevel(map.get(EnumArchivesLibraryDefaultField.SECURITY_LEVEL.getDataKey()));
                        makeBorrowVo.setArchivesDirectoryFilingAnnual((String) map.get(EnumArchivesLibraryDefaultField.FILING_ANNUAL.getDataKey()));
                    }
                }
                //内部人员
                if (uType == 1) {
                    SysUser sysUser = iSysUserService.getById(uId);
                    if (sysUser != null) {
                        makeBorrowVo.setUserName(sysUser.getUsername());
                    }
                } else {//远程人员
                    SysRemoteUser sysRemoteUser = iSysRemoteUserService.getById(uId);
                    if (sysRemoteUser != null) {
                        makeBorrowVo.setUserName(sysRemoteUser.getUsername());
                        makeBorrowVo.setCertificateNumber(sysRemoteUser.getCertificateNumber());
                    }
                }
                MakeBorrowPurpose makeBorrowPurpose = iMakeBorrowPurposeService.getById(makeId);
                if (makeBorrowPurpose != null) {
                    makeBorrowVo.setBorrowPurposeName(makeBorrowPurpose.getName());
                }
            } else { //实体借阅
                String tableName = TableNameUtil.tableName;
                Integer count = iWareTemplateLibraryFieldService.findTable(tableName);
                if (count == 0)
                    throw new BaseRuntimeException("请先创建实体库表");
                Map<String, Object> map = iWareTemplateLibraryFieldService.findByIdAndTableName(fileId, tableName);
                if (map != null) {
                    makeBorrowVo.setArchivesLibName((String) map.get("archivesName"));
                    makeBorrowVo.setArchivesDirectoryNum((String) map.get("archivesNum"));
                }
            }
            makeBorrowVos.add(makeBorrowVo);
        }
        page1.setRecords(makeBorrowVos);
        return page1;
    }
}

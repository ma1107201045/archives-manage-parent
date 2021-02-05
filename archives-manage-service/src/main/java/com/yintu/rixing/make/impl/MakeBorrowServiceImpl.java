package com.yintu.rixing.make.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yintu.rixing.data.DataArchivesLibraryFile;
import com.yintu.rixing.data.IDataArchivesLibraryFileService;
import com.yintu.rixing.data.IDataFormalLibraryService;
import com.yintu.rixing.dto.make.MakeBorrowApproveDto;
import com.yintu.rixing.dto.make.MakeBorrowQueryDto;
import com.yintu.rixing.dto.make.MakeBorrowRemoteFormDto;
import com.yintu.rixing.enumobject.EnumArchivesLibraryDefaultField;
import com.yintu.rixing.enumobject.EnumAuditStatus;
import com.yintu.rixing.enumobject.EnumFlag;
import com.yintu.rixing.exception.BaseRuntimeException;
import com.yintu.rixing.make.*;
import com.yintu.rixing.pojo.MakeBorrowAuditorPojo;
import com.yintu.rixing.system.*;
import com.yintu.rixing.util.TableNameUtil;
import com.yintu.rixing.vo.make.MakeBorrowTransferVo;
import com.yintu.rixing.vo.make.MakeBorrowVo;
import com.yintu.rixing.warehouse.IWareTemplateLibraryFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public void approve(MakeBorrowApproveDto makeBorrowApproveDto) {
        Integer id = makeBorrowApproveDto.getId();
        Integer auditorId = makeBorrowApproveDto.getAuditorId();
        Short auditStatus = makeBorrowApproveDto.getAuditStatus();
        String context = makeBorrowApproveDto.getContext();
        String accessoryName = makeBorrowApproveDto.getAccessoryName();
        String accessoryPath = makeBorrowApproveDto.getAccessoryPath();
        if (auditStatus != 2 && auditStatus != 3 && auditStatus != 4)
            throw new BaseRuntimeException("审核状态错误");
        MakeBorrow makeBorrow = this.getById(id);
        if (makeBorrow != null) {
            //1.判断此文件是否审批过
            List<MakeBorrowAuditor> makeBorrowAuditors = iMakeBorrowAuditorService.listByMakeBorrowAuditorPojo(new MakeBorrowAuditorPojo(id, null, EnumFlag.True.getValue()));
            if (makeBorrowAuditors.isEmpty()) {
                throw new BaseRuntimeException("此文件已审核，无需重复审核");
            }
            //2.更改当前顺序的审核人群的状态
            Integer sort = null;
            for (MakeBorrowAuditor makeBorrowAuditor : makeBorrowAuditors) {
                // 3.当前人需要同步附件等其他信息
                if (auditorId.equals(makeBorrowAuditor.getAuditorId())) {
                    sort = makeBorrowAuditor.getSort();
                    makeBorrowAuditor.setContext(context);
                    makeBorrowAuditor.setAccessoryName(accessoryName);
                    makeBorrowAuditor.setAccessoryPath(accessoryPath);
                }
                makeBorrowAuditor.setActivate(EnumFlag.False.getValue());
                makeBorrowAuditor.setIsDispose(EnumFlag.True.getValue());
                makeBorrowAuditor.setAuditStatus(auditStatus);
                makeBorrowAuditor.setAuditFinishTime(DateUtil.date());
            }
            if (sort == null)
                throw new BaseRuntimeException("你无权审核此文件或已被其他人审批");
            iMakeBorrowAuditorService.updateBatchById(makeBorrowAuditors);

            //4.判断审核状态
            //4.1如果通过状态，进行判断是否还有下一批人
            if (auditStatus == 2) {
                makeBorrowAuditors = iMakeBorrowAuditorService.listByMakeBorrowAuditorPojo(new MakeBorrowAuditorPojo(id, sort + 1, null));
                //4.2 如果没有人则审核完全通过，回写审核记录状态----------------
                if (makeBorrowAuditors.isEmpty()) {
                    makeBorrow.setAuditStatus(auditStatus);
                    makeBorrow.setAuditFinishTime(DateUtil.date());
                    makeBorrow.setPreviewType(EnumFlag.True.getValue());
                    this.updateById(makeBorrow);
                    //4.3 如果有人则审核进行下一批
                } else {
                    for (MakeBorrowAuditor eachMakeBorrowAuditor : makeBorrowAuditors) {//更改当前顺序的审核人群的激活状态
                        eachMakeBorrowAuditor.setActivate(EnumFlag.True.getValue());
                        iMakeBorrowAuditorService.updateById(eachMakeBorrowAuditor);
                    }
                }
            } else if (auditStatus == 3) {
                //4.4 如果拒绝状态，直接回写审核记录状态
                makeBorrow.setAuditStatus(auditStatus);
                makeBorrow.setAuditFinishTime(DateUtil.date());
                this.updateById(makeBorrow);
            } else {
                //4.5 如果转交状态，添加转交人信息（此人顺序跟当前转交人信息一致）
                MakeBorrowAuditor makeBorrowAuditor = new MakeBorrowAuditor();
                makeBorrowAuditor.setMakeBorrowId(id);
                makeBorrowAuditor.setAuditorId(auditorId);
                makeBorrowAuditor.setSort(sort);
                makeBorrowAuditor.setActivate(EnumFlag.True.getValue());
                makeBorrowAuditor.setIsDispose(EnumFlag.False.getValue());
                makeBorrowAuditor.setAuditStatus(EnumAuditStatus.AUDIT_IN.getValue());
                makeBorrowAuditor.setAuditFinishTime(DateUtil.date());
                iMakeBorrowAuditorService.save(makeBorrowAuditor);
            }
        }
    }

    @Override
    public List<MakeBorrowTransferVo> listTransferById(Integer id, Integer currentUserId) {
        List<MakeBorrowAuditor> makeBorrowAuditors = iMakeBorrowAuditorService.listByMakeBorrowAuditorPojo(new MakeBorrowAuditorPojo(id, null, null));
        List<Integer> ids = makeBorrowAuditors.stream().map(MakeBorrowAuditor::getAuditorId).collect(Collectors.toList());
        ids.add(currentUserId);
        List<SysUser> sysUsers = iSysUserService.listByNotIds(ids);
        return sysUsers.stream().map(sysUser -> {
            MakeBorrowTransferVo makeBorrowTransferVo = new MakeBorrowTransferVo();
            makeBorrowTransferVo.setId(sysUser.getId());
            makeBorrowTransferVo.setUsername(sysUser.getUsername());
            makeBorrowTransferVo.setNickName(sysUser.getNickname());
            return makeBorrowTransferVo;
        }).collect(Collectors.toList());
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
            //内部人员
            if (uType == 1) {
                SysUser sysUser = iSysUserService.getById(uId);
                if (sysUser != null) {
                    makeBorrowVo.setUserName(sysUser.getUsername());
                    makeBorrowVo.setCertificateNumber(sysUser.getCertificateNumber());
                }
            } else {//远程人员
                SysRemoteUser sysRemoteUser = iSysRemoteUserService.getById(uId);
                if (sysRemoteUser != null) {
                    makeBorrowVo.setUserName(sysRemoteUser.getUsername());
                    makeBorrowVo.setCertificateNumber(sysRemoteUser.getCertificateNumber());
                }
            }
            //利用
            MakeBorrowPurpose makeBorrowPurpose = iMakeBorrowPurposeService.getById(makeId);
            if (makeBorrowPurpose != null) {
                makeBorrowVo.setBorrowPurposeName(makeBorrowPurpose.getName());
            }
            makeBorrowVos.add(makeBorrowVo);
        }
        page1.setRecords(makeBorrowVos);
        return page1;
    }


}

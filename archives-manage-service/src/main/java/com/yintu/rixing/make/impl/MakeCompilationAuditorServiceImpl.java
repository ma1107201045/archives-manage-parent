package com.yintu.rixing.make.impl;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yintu.rixing.dto.make.MakeBorrowApproveDto;
import com.yintu.rixing.enumobject.EnumAuditStatus;
import com.yintu.rixing.enumobject.EnumFlag;
import com.yintu.rixing.exception.BaseRuntimeException;
import com.yintu.rixing.make.*;
import com.yintu.rixing.pojo.MakeBorrowAuditorPojo;
import com.yintu.rixing.system.ISysApprovalProcessConfigurationService;
import com.yintu.rixing.system.SysApprovalProcessConfiguration;
import com.yintu.rixing.vo.make.MakeBorrowTransferVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 编研和审核人中间表 服务实现类
 * </p>
 *
 * @author Mr.liu
 * @since 2021-02-22
 */
@Service
public class MakeCompilationAuditorServiceImpl extends ServiceImpl<MakeCompilationAuditorMapper, MakeCompilationAuditor> implements IMakeCompilationAuditorService {

    @Autowired
    private ISysApprovalProcessConfigurationService iSysApprovalProcessConfigurationService;
    @Autowired
    private IMakeCompilationAuditorService iMakeCompilationAuditorService;
    @Autowired
    private IMakeCompilationService iMakeCompilationService;




    @Override
    public List<MakeCompilationAuditor> listByMakeCompilationAuditor(MakeBorrowAuditorPojo makeBorrowAuditorPojo) {
        Integer makeBorrowId = makeBorrowAuditorPojo.getMakeBorrowId();
        Integer sort = makeBorrowAuditorPojo.getSort();
        Short activate = makeBorrowAuditorPojo.getActivate();
        QueryWrapper<MakeCompilationAuditor> queryWrapper = new QueryWrapper<>();
        if (makeBorrowId != null)
            queryWrapper.lambda().eq(MakeCompilationAuditor::getMakeCompilationId, makeBorrowId);
        if (sort != null)
            queryWrapper.lambda().eq(MakeCompilationAuditor::getSort, sort);
        if (activate != null)
            queryWrapper.lambda().eq(MakeCompilationAuditor::getActivate, activate);
        return this.list(queryWrapper);
    }

    @Override
    public void approve(MakeBorrowApproveDto makeBorrowApproveDto) {
        Integer id = makeBorrowApproveDto.getId();
        Integer userId = makeBorrowApproveDto.getUserId();
        Integer auditorId = makeBorrowApproveDto.getAuditorId();
        Short auditStatus = makeBorrowApproveDto.getAuditStatus();
        String context = makeBorrowApproveDto.getContext();
        String accessoryName = makeBorrowApproveDto.getAccessoryName();
        String accessoryPath = makeBorrowApproveDto.getAccessoryPath();
        if (auditStatus != 2 && auditStatus != 3 && auditStatus != 4) {
            throw new BaseRuntimeException("审核状态错误");
        }
        MakeCompilation makeCompilation = iMakeCompilationService.getById(id);
        //1.判断此文件是否审批过
        if (makeCompilation != null) {
            List<MakeCompilationAuditor> makeCompilationAuditors = iMakeCompilationAuditorService.listByMakeCompilationAuditor(new MakeBorrowAuditorPojo(id, null, EnumFlag.True.getValue()));
            if (makeCompilationAuditors.isEmpty()) {
                throw new BaseRuntimeException("此编研已审核，无需重复审核");
            }
            //2.更改当前顺序的审核人群的状态
            Integer sort = null;
            for (MakeCompilationAuditor makeCompilationAuditor : makeCompilationAuditors) {
                // 3.当前人需要同步附件等其他信息
                if (userId.equals(makeCompilationAuditor.getAuditorId())) {
                    sort = makeCompilationAuditor.getSort();
                    makeCompilationAuditor.setContext(context);
                    makeCompilationAuditor.setAccessoryName(accessoryName);
                    makeCompilationAuditor.setAccessoryPath(accessoryPath);
                }
                makeCompilationAuditor.setActivate((int) EnumFlag.False.getValue());
                makeCompilationAuditor.setIsDispose((int) EnumFlag.True.getValue());
                makeCompilationAuditor.setAuditStatus((int) auditStatus);
                makeCompilationAuditor.setAuditFinishTime(DateUtil.date());
            }
            if (sort == null) {
                throw new BaseRuntimeException("你无权审核此编研或已被其他人审批");
            }
            iMakeCompilationAuditorService.updateBatchById(makeCompilationAuditors);
            //4.判断审核状态
            //4.1如果通过状态，进行判断是否还有下一批人
            if (auditStatus == 2) {
                makeCompilationAuditors = iMakeCompilationAuditorService.listByMakeCompilationAuditor(new MakeBorrowAuditorPojo(id, sort + 1, null));
                //4.2 如果没有人则审核完全通过，回写审核记录状态----------------
                if (makeCompilationAuditors.isEmpty()) {
                    makeCompilation.setAuditStatus((int)auditStatus);
                    makeCompilation.setAuditFinishTime(DateUtil.date());
                    iMakeCompilationService.updateById(makeCompilation);

                    //更新审核时间和审批意见
                    MakeCompilation makeCompilation1=new MakeCompilation();
                    makeCompilation1.setAudittime(new Date());
                    makeCompilation1.setAuditview(context);
                    makeCompilation1.setId(id);
                    iMakeCompilationService.updateById(makeCompilation1);
                    //4.3 如果有人则审核进行下一批
                }else {
                    for (MakeCompilationAuditor makeCompilationAuditor : makeCompilationAuditors) {
                        makeCompilationAuditor.setActivate((int)EnumFlag.True.getValue());
                        iMakeCompilationAuditorService.updateById(makeCompilationAuditor);
                    }
                }
            }else if (auditStatus == 3) {
                //4.4 如果拒绝状态，直接回写审核记录状态
                makeCompilation.setAuditStatus((int)auditStatus);
                makeCompilation.setAuditFinishTime(DateUtil.date());
                iMakeCompilationService.updateById(makeCompilation);

                //更新审核时间和审批意见
                MakeCompilation makeCompilation1=new MakeCompilation();
                makeCompilation1.setAudittime(new Date());
                makeCompilation1.setAuditview(context);
                makeCompilation1.setId(id);
                iMakeCompilationService.updateById(makeCompilation1);
            }else {
                //4.5 如果转交状态，添加转交人信息（此人顺序跟当前转交人信息一致）
                MakeCompilationAuditor makeCompilationAuditor=new MakeCompilationAuditor();
                makeCompilationAuditor.setMakeCompilationId(id);
                makeCompilationAuditor.setAuditorId(auditorId);
                makeCompilationAuditor.setSort(sort);
                makeCompilationAuditor.setActivate((int)EnumFlag.True.getValue());
                makeCompilationAuditor.setIsDispose((int)EnumFlag.False.getValue());
                makeCompilationAuditor.setAuditStatus((int)EnumAuditStatus.AUDIT_IN.getValue());
                makeCompilationAuditor.setAuditFinishTime(DateUtil.date());
                iMakeCompilationAuditorService.save(makeCompilationAuditor);
            }
        }
    }

    @Override
    public void addAuditors(Integer compilationId, Integer auditorId) {
        List<SysApprovalProcessConfiguration> sysApprovalProcessConfigurations = iSysApprovalProcessConfigurationService.listByApprovalProcessId(auditorId);
        List<MakeCompilationAuditor> makeBorrowAuditors = new ArrayList<>();
        for (SysApprovalProcessConfiguration sysApprovalProcessConfiguration : sysApprovalProcessConfigurations) {
            MakeCompilationAuditor makeCompilationAuditor = new MakeCompilationAuditor();
            makeCompilationAuditor.setMakeCompilationId(compilationId);
            makeCompilationAuditor.setAuditorId(sysApprovalProcessConfiguration.getUserId());
            Integer order = sysApprovalProcessConfiguration.getOrder();
            makeCompilationAuditor.setSort(order);
            if (order == 1) {
                makeCompilationAuditor.setActivate((int) EnumFlag.True.getValue());
            } else {
                makeCompilationAuditor.setActivate((int) EnumFlag.False.getValue());
            }
            makeCompilationAuditor.setIsDispose((int) EnumFlag.False.getValue());
            makeCompilationAuditor.setAuditStatus((int) EnumAuditStatus.AUDIT_IN.getValue());
            makeBorrowAuditors.add(makeCompilationAuditor);
        }
        iMakeCompilationAuditorService.saveBatch(makeBorrowAuditors);
        MakeCompilation makeCompilation =new MakeCompilation();
        makeCompilation.setIsnotAudit(1);
        makeCompilation.setId(compilationId);
        iMakeCompilationService.updateById(makeCompilation);
    }
}

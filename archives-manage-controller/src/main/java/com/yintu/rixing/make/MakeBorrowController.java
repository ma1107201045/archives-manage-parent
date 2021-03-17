package com.yintu.rixing.make;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.config.other.Authenticator;
import com.yintu.rixing.data.DataArchivesLibraryFile;
import com.yintu.rixing.data.IDataArchivesLibraryFileService;
import com.yintu.rixing.data.IDataFormalLibraryService;
import com.yintu.rixing.dto.make.MakeBorrowApproveDto;
import com.yintu.rixing.dto.system.SysApprovalProcessQueryDto;
import com.yintu.rixing.enumobject.EnumArchivesLibraryDefaultField;
import com.yintu.rixing.enumobject.EnumFlag;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.system.*;
import com.yintu.rixing.util.ResponseDataUtil;
import com.yintu.rixing.util.ResultDataUtil;
import com.yintu.rixing.vo.make.MakeBorrowTransferVo;
import com.yintu.rixing.vo.make.MakeBorrowVo;
import com.yintu.rixing.warehouse.IWareTemplateLibraryFieldService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 利用中心的借阅申请表 前端控制器
 * </p>
 *
 * @author Mr.liu
 * @since 2021-01-11
 */
@RestController
@RequestMapping("/make/make-borrow")
public class MakeBorrowController extends Authenticator {

    @Autowired
    private IMakeBorrowService iMakeBorrowService;
    @Autowired
    private ISysUserService iSysUserService;
    @Autowired
    private IDataArchivesLibraryFileService iDataArchivesLibraryFileService;
    @Autowired
    private IWareTemplateLibraryFieldService iWareTemplateLibraryFieldService;
    @Autowired
    private ISysRemoteUserService iSysRemoteUserService;
    @Autowired
    private ISysArchivesLibraryService iSysArchivesLibraryService;
    @Autowired
    private IDataFormalLibraryService iDataFormalLibraryService;
    @Autowired
    private IMakeBorrowPurposeService iMakeBorrowPurposeService;
    @Autowired
    private ISysApprovalProcessService iSysApprovalProcessService;
    @Autowired
    private IMakeBorrowAuditorService iMakeBorrowAuditorService;

    //查询审批流
    @Log(level = EnumLogLevel.TRACE, module = "借阅申请", context = "查询审批流程列表信息")
    @GetMapping
    @ApiOperation(value = "查询审批流程列表信息", notes = "查询审批流程列表信息")
    public ResultDataUtil<List<SysApprovalProcess>> findPage() {
        List<SysApprovalProcess> sysApprovalProcessPage = iSysApprovalProcessService.list();
        return ResultDataUtil.ok("查询审批流程列表信息成功", sysApprovalProcessPage);
    }


    @PostMapping("/add")
    @Log(level = EnumLogLevel.INFO, module = "借阅申请", context = "添加新的借阅申请信息")
    @ApiOperation(value = "添加新的借阅申请信息", notes = "添加新的借阅申请信息")
    public Map<String, Object> add(MakeBorrow makeBorrow) {
        makeBorrow.setBorrowType((short)1);
        iMakeBorrowService.add(makeBorrow);
        return ResponseDataUtil.ok("新增借阅申请信息成功");
    }


    @DeleteMapping("/{ids}")
    @Log(level = EnumLogLevel.INFO, module = "借阅申请", context = "根据id删除借阅申请信息")
    @ApiOperation(value = "删除借阅申请", notes = "删除借阅申请")
    @ApiImplicitParam(name = "ids", value = "主键id集", required = true, paramType = "path")
    public Map<String, Object> remove(@PathVariable Set<Integer> ids) {
        if (iMakeBorrowService.removeByIds(ids)) {
            return ResponseDataUtil.ok("删除借阅申请信息成功");
        }
        return ResponseDataUtil.error("删除借阅申请信息失败");
    }

    @PutMapping("/{id}")
    @Log(level = EnumLogLevel.INFO, module = "借阅申请", context = "根据id修改借阅申请信息")
    @ApiOperation(value = "修改借阅申请信息", notes = "修改借阅申请信息")
    @ApiImplicitParam(name = "id", value = "主键id", required = true)
    public Map<String, Object> edit(Integer id, MakeBorrow makeBorrow) {
        iMakeBorrowService.updateById(makeBorrow);
        return ResponseDataUtil.ok("修改借阅申请成功");
    }

    @Log(level = EnumLogLevel.DEBUG, module = "借阅申请", context = "查询借阅申请的电子借阅信息列表")
    @GetMapping("/findElectronicBorrowDatas")
    @ApiOperation(value = "查询借阅申请的电子借阅信息列表", notes = " 查询借阅申请的电子借阅信息列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "num", value = "页码", required = true, defaultValue = "1"),
            @ApiImplicitParam(name = "size", value = "页数", required = true, defaultValue = "10"),
            @ApiImplicitParam(name = "name", value = "姓名")
    })
    public ResultDataUtil<Page<MakeBorrowVo>> findElectronicBorrowDatas(@RequestParam Integer num, @RequestParam Integer size, String name) {
        Page<MakeBorrowVo> page1 = new Page<>();
        if (name != null) {
            QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().like(SysUser::getNickname, name == null ? "" : name);
            List<SysUser> sysUserList = iSysUserService.list(queryWrapper);
            if (sysUserList.size() == 0) {
                return ResultDataUtil.ok("暂无数据", null);
            } else {
                List<Integer> userids = new ArrayList<>();
                for (SysUser sysUser : sysUserList) {
                    Integer id = sysUser.getId();
                    userids.add(id);
                }
                QueryWrapper<MakeBorrow> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.in("user_id", userids);
                queryWrapper1.eq("borrow_type", "1");
                queryWrapper1.orderByDesc("id");
                Page<MakeBorrow> page = iMakeBorrowService.page(new Page<>(num, size), queryWrapper1);
                BeanUtil.copyProperties(page, page1, "records");
                List<MakeBorrowVo> makeBorrowVos = new ArrayList<>();

                for (MakeBorrow record : page.getRecords()) {
                    MakeBorrowVo makeBorrowVo = new MakeBorrowVo();
                    Integer fileid1 = record.getFileid();
                    Short userType = record.getUserType();
                    Integer userId = record.getUserId();
                    Integer makeId = record.getMakeId();
                    Integer id = record.getId();
                    DataArchivesLibraryFile dataArchivesLibraryFile = iDataArchivesLibraryFileService.getById(fileid1);
                    if (dataArchivesLibraryFile != null) {
                        makeBorrowVo.setArchivesFileId(id);
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
                    if (userType == 1) {
                        SysUser sysUser = iSysUserService.getById(userId);
                        if (sysUser != null) {
                            makeBorrowVo.setUserName(sysUser.getUsername());
                        }
                    } else {//远程人员
                        SysRemoteUser sysRemoteUser = iSysRemoteUserService.getById(userId);
                        if (sysRemoteUser != null) {
                            makeBorrowVo.setUserName(sysRemoteUser.getUsername());
                            makeBorrowVo.setCertificateNumber(sysRemoteUser.getCertificateNumber());
                        }
                    }
                    MakeBorrowPurpose makeBorrowPurpose = iMakeBorrowPurposeService.getById(makeId);
                    if (makeBorrowPurpose != null) {
                        makeBorrowVo.setBorrowPurposeName(makeBorrowPurpose.getName());
                    }
                    makeBorrowVo.setBorrowStartTime(record.getBorrowStartTime());
                    makeBorrowVo.setBorrowEndTime(record.getBorrowEndTime());
                    makeBorrowVo.setAuditStatus(record.getAuditStatus());

                    List<Integer> userAuditors = new ArrayList<>();
                    QueryWrapper<MakeBorrowAuditor> queryWrapper2 = new QueryWrapper<>();
                    queryWrapper2.eq("make_borrow_id", record.getId()).eq("activate", EnumFlag.True.getValue());
                    List<MakeBorrowAuditor> makeBorrowAuditors = iMakeBorrowAuditorService.list(queryWrapper2);
                    for (MakeBorrowAuditor makeBorrowAuditor : makeBorrowAuditors) {
                        userAuditors.add(makeBorrowAuditor.getAuditorId());
                    }
                    makeBorrowVo.setList(userAuditors);
                    makeBorrowVos.add(makeBorrowVo);
                }
                page1.setRecords(makeBorrowVos);
                return ResultDataUtil.ok("查询借阅申请的电子借阅信息列表成功", page1);
            }
        } else {
            QueryWrapper<MakeBorrow> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("borrow_type", "1");
            queryWrapper1.orderByDesc("id");
            Page<MakeBorrow> page = iMakeBorrowService.page(new Page<>(num, size), queryWrapper1);
            BeanUtil.copyProperties(page, page1, "records");
            List<MakeBorrowVo> makeBorrowVos = new ArrayList<>();
            for (MakeBorrow record : page.getRecords()) {
                MakeBorrowVo makeBorrowVo = new MakeBorrowVo();
                Integer fileid1 = record.getFileid();
                Short userType = record.getUserType();
                Integer userId = record.getUserId();
                Integer makeId = record.getMakeId();
                Integer id = record.getId();
                DataArchivesLibraryFile dataArchivesLibraryFile = iDataArchivesLibraryFileService.getById(fileid1);
                if (dataArchivesLibraryFile != null) {
                    makeBorrowVo.setArchivesFileId(id);
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
                if (userType == 1) {
                    SysUser sysUser = iSysUserService.getById(userId);
                    if (sysUser != null) {
                        makeBorrowVo.setUserName(sysUser.getUsername());
                    }
                } else {//远程人员
                    SysRemoteUser sysRemoteUser = iSysRemoteUserService.getById(userId);
                    if (sysRemoteUser != null) {
                        makeBorrowVo.setUserName(sysRemoteUser.getUsername());
                        makeBorrowVo.setCertificateNumber(sysRemoteUser.getCertificateNumber());
                    }
                }
                MakeBorrowPurpose makeBorrowPurpose = iMakeBorrowPurposeService.getById(makeId);
                if (makeBorrowPurpose != null) {
                    makeBorrowVo.setBorrowPurposeName(makeBorrowPurpose.getName());
                }
                makeBorrowVo.setBorrowStartTime(record.getBorrowStartTime());
                makeBorrowVo.setBorrowEndTime(record.getBorrowEndTime());
                makeBorrowVo.setAuditStatus(record.getAuditStatus());

                List<Integer> userAuditors = new ArrayList<>();
                QueryWrapper<MakeBorrowAuditor> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("make_borrow_id", record.getId()).eq("activate", EnumFlag.True.getValue());
                List<MakeBorrowAuditor> makeBorrowAuditors = iMakeBorrowAuditorService.list(queryWrapper);
                for (MakeBorrowAuditor makeBorrowAuditor : makeBorrowAuditors) {
                    userAuditors.add(makeBorrowAuditor.getAuditorId());
                }
                makeBorrowVo.setList(userAuditors);
                makeBorrowVos.add(makeBorrowVo);

            }
            page1.setRecords(makeBorrowVos);
        }
        return ResultDataUtil.ok("查询借阅申请的电子借阅信息列表成功", page1);
    }

    @Log(level = EnumLogLevel.DEBUG, module = "借阅申请", context = "查询借阅申请的实体借阅信息列表")
    @GetMapping("/findEntityBorrowDatas")
    @ApiOperation(value = "查询借阅申请的实体借阅信息列表", notes = " 查询借阅申请的实体借阅信息列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "num", value = "页码", required = true, defaultValue = "1"),
            @ApiImplicitParam(name = "size", value = "页数", required = true, defaultValue = "10"),
            @ApiImplicitParam(name = "name", value = "姓名")
    })
    public ResultDataUtil<Page<MakeBorrowVo>> findEntityBorrowDatas(@RequestParam Integer num, @RequestParam Integer size, String name) {
        Page<MakeBorrowVo> page1 = new Page<>();
        if (name != null) {
            QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().like(SysUser::getNickname, name == null ? "" : name);
            List<SysUser> sysUserList = iSysUserService.list(queryWrapper);
            if (sysUserList.size() == 0) {
                return ResultDataUtil.ok("暂无数据", null);
            } else {
                List<Integer> userids = new ArrayList<>();
                for (SysUser sysUser : sysUserList) {
                    Integer id = sysUser.getId();
                    userids.add(id);
                }
                QueryWrapper<MakeBorrow> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.in("user_id", userids);
                queryWrapper1.eq("borrow_type", "2");
                queryWrapper1.orderByDesc("id");
                Page<MakeBorrow> page = iMakeBorrowService.page(new Page<>(num, size), queryWrapper1);
                BeanUtil.copyProperties(page, page1, "records");
                List<MakeBorrowVo> makeBorrowVos = new ArrayList<>();

                for (MakeBorrow record : page.getRecords()) {
                    MakeBorrowVo makeBorrowVo = new MakeBorrowVo();
                    Integer userId = record.getUserId();
                    Integer fileid = record.getFileid();
                    Short userType = record.getUserType();

                    //内部人员
                    if (userType == 1) {
                        SysUser sysUser = iSysUserService.getById(userId);
                        if (sysUser != null) {
                            makeBorrowVo.setUserName(sysUser.getUsername());
                        }
                    } else {//远程人员
                        SysRemoteUser sysRemoteUser = iSysRemoteUserService.getById(userId);
                        if (sysRemoteUser != null) {
                            makeBorrowVo.setUserName(sysRemoteUser.getUsername());
                            makeBorrowVo.setCertificateNumber(sysRemoteUser.getCertificateNumber());
                        }
                    }
                    Map<String, Object> EntityArchives = iWareTemplateLibraryFieldService.findEntityArchivesById(fileid);
                    if (EntityArchives != null) {
                        makeBorrowVo.setArchivesDirectoryTopicName((String) EntityArchives.get("archivesName"));
                        makeBorrowVo.setArchivesDirectoryNum((String) EntityArchives.get("archivesNum"));
                        makeBorrowVo.setId(record.getId());
                        makeBorrowVo.setArchivesLibName("实体库房");
                        makeBorrowVo.setBorrowStartTime(record.getBorrowStartTime());
                        makeBorrowVo.setBorrowEndTime(record.getBorrowEndTime());
                        makeBorrowVo.setAuditStatus(record.getAuditStatus());
                    }
                    List<Integer> userAuditors = new ArrayList<>();
                    QueryWrapper<MakeBorrowAuditor> queryWrapper2 = new QueryWrapper<>();
                    queryWrapper2.eq("make_borrow_id", record.getId()).eq("activate", EnumFlag.True.getValue());
                    List<MakeBorrowAuditor> makeBorrowAuditors = iMakeBorrowAuditorService.list(queryWrapper2);
                    for (MakeBorrowAuditor makeBorrowAuditor : makeBorrowAuditors) {
                        userAuditors.add(makeBorrowAuditor.getAuditorId());
                    }
                    makeBorrowVo.setList(userAuditors);

                    makeBorrowVos.add(makeBorrowVo);
                }
                page1.setRecords(makeBorrowVos);
                return ResultDataUtil.ok("查询借阅申请的实体借阅信息列表成功", page1);
            }
        } else {
            QueryWrapper<MakeBorrow> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("borrow_type", "2");
            queryWrapper1.orderByDesc("id");
            Page<MakeBorrow> page = iMakeBorrowService.page(new Page<>(num, size), queryWrapper1);
            BeanUtil.copyProperties(page, page1, "records");
            List<MakeBorrowVo> makeBorrowVos = new ArrayList<>();

            for (MakeBorrow record : page.getRecords()) {
                MakeBorrowVo makeBorrowVo = new MakeBorrowVo();
                Integer userId = record.getUserId();
                Integer fileid = record.getFileid();
                Short userType = record.getUserType();

                //内部人员
                if (userType == 1) {
                    SysUser sysUser = iSysUserService.getById(userId);
                    if (sysUser != null) {
                        makeBorrowVo.setUserName(sysUser.getUsername());
                    }
                } else {//远程人员
                    SysRemoteUser sysRemoteUser = iSysRemoteUserService.getById(userId);
                    if (sysRemoteUser != null) {
                        makeBorrowVo.setUserName(sysRemoteUser.getUsername());
                        makeBorrowVo.setCertificateNumber(sysRemoteUser.getCertificateNumber());
                    }
                }
                Map<String, Object> EntityArchives = iWareTemplateLibraryFieldService.findEntityArchivesById(fileid);
                if (EntityArchives != null) {
                    makeBorrowVo.setArchivesDirectoryTopicName((String) EntityArchives.get("archivesName"));
                    makeBorrowVo.setArchivesDirectoryNum((String) EntityArchives.get("archivesNum"));
                    makeBorrowVo.setId(record.getId());
                    makeBorrowVo.setArchivesLibName("实体库房");
                    makeBorrowVo.setBorrowStartTime(record.getBorrowStartTime());
                    makeBorrowVo.setBorrowEndTime(record.getBorrowEndTime());
                    makeBorrowVo.setAuditStatus(record.getAuditStatus());
                }
                List<Integer> userAuditors = new ArrayList<>();
                QueryWrapper<MakeBorrowAuditor> queryWrapper2 = new QueryWrapper<>();
                queryWrapper2.eq("make_borrow_id", record.getId()).eq("activate", EnumFlag.True.getValue());
                List<MakeBorrowAuditor> makeBorrowAuditors = iMakeBorrowAuditorService.list(queryWrapper2);
                for (MakeBorrowAuditor makeBorrowAuditor : makeBorrowAuditors) {
                    userAuditors.add(makeBorrowAuditor.getAuditorId());
                }
                makeBorrowVo.setList(userAuditors);

                makeBorrowVos.add(makeBorrowVo);
            }
            page1.setRecords(makeBorrowVos);
            return ResultDataUtil.ok("查询借阅申请的实体借阅信息列表成功", page1);
        }
    }

    //审核借阅信息
    @Log(level = EnumLogLevel.INFO, module = "借阅申请", context = "审核借阅信息")
    @PatchMapping("/{id}")
    @ApiOperation(value = "审核借阅信息", notes = "审核借阅信息")
    @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path")
    public ResultDataUtil<Object> approve(@Validated MakeBorrowApproveDto makeBorrowApproveDto) {
        Integer userId = this.getLoginUserId();
        makeBorrowApproveDto.setUserId(userId);
        iMakeBorrowService.approve(makeBorrowApproveDto);
        return ResultDataUtil.ok("审核借阅信息成功");
    }

    //查询转交人员信息
    @Log(level = EnumLogLevel.TRACE, module = "借阅申请", context = "查询转交用户列表信息")
    @GetMapping("/{id}/sys-user")
    @ApiOperation(value = "查询转交用户列表信息", notes = "查询转交用户列表信息")
    @ApiImplicitParam(name = "id", type = "int", value = "主键id", required = true, paramType = "path")
    @ApiOperationSupport(order = 3)
    public ResultDataUtil<List<MakeBorrowTransferVo>> findUsers(@PathVariable Integer id) {
        List<MakeBorrowTransferVo> makeBorrowTransferVos = iMakeBorrowService.listTransferById(id, this.getLoginUserId());
        return ResultDataUtil.ok("查询转交用户列表信息成功", makeBorrowTransferVos);
    }

}

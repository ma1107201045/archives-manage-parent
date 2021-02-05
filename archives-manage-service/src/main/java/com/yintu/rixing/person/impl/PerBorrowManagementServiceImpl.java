package com.yintu.rixing.person.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yintu.rixing.data.DataArchivesLibraryFile;
import com.yintu.rixing.data.IDataArchivesLibraryFileService;
import com.yintu.rixing.data.IDataFormalLibraryService;
import com.yintu.rixing.dto.person.PerBorrowManagementQueryDto;
import com.yintu.rixing.enumobject.EnumArchivesLibraryDefaultField;
import com.yintu.rixing.exception.BaseRuntimeException;
import com.yintu.rixing.make.IMakeBorrowPurposeService;
import com.yintu.rixing.make.MakeBorrow;
import com.yintu.rixing.make.MakeBorrowPurpose;
import com.yintu.rixing.person.IPerBorrowManagementService;
import com.yintu.rixing.person.PerBorrowManagementMapper;
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
 * @Author: mlf
 * @Date: 2021/2/5 16:41:46
 * @Version: 1.0
 */
@Service
public class PerBorrowManagementServiceImpl implements IPerBorrowManagementService {
    @Autowired
    private PerBorrowManagementMapper perBorrowManagementMapper;
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
    public Page<MakeBorrowVo> selectPage(PerBorrowManagementQueryDto perBorrowManagementQueryDto) {
        Page<MakeBorrowVo> page1 = new Page<>();
        Integer num = perBorrowManagementQueryDto.getNum();
        Integer size = perBorrowManagementQueryDto.getSize();
        Page<MakeBorrow> page2 = perBorrowManagementMapper.selectPage(new Page<>(num, size), perBorrowManagementQueryDto);
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
                    Integer archivesLibraryId = dataArchivesLibraryFile.getArchivesLibraryId();
                    Integer dataId = dataArchivesLibraryFile.getDataId();
                    Map<String, Object> map = iDataFormalLibraryService.getById(dataId, archivesLibraryId);
                    if (map != null) {
                        makeBorrowVo.setArchivesDirectoryNum((String) map.get(EnumArchivesLibraryDefaultField.ARCHIVES_NUM.getDataKey()));
                    }
                }
                //内部人员
                if (uType == 1) {
                    SysUser sysUser = iSysUserService.getById(uId);
                    if (sysUser != null) {
                        makeBorrowVo.setUserName(sysUser.getUsername());
                        makeBorrowVo.setCertificateNumber(sysUser.getUsername());
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
                    makeBorrowVo.setArchivesDirectoryNum((String) map.get("archivesNum"));
                }
            }
            makeBorrowVo.setList(makeBorrow.getMakeBorrowAuditors());
            makeBorrowVos.add(makeBorrowVo);
        }
        page1.setRecords(makeBorrowVos);

        return page1;
    }
}

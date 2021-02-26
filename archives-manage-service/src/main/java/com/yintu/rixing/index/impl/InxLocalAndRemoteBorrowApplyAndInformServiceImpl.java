package com.yintu.rixing.index.impl;

import com.yintu.rixing.data.DataArchivesLibraryFile;
import com.yintu.rixing.data.IDataArchivesLibraryFileService;
import com.yintu.rixing.data.IDataFormalLibraryService;
import com.yintu.rixing.enumobject.EnumArchivesLibraryDefaultField;
import com.yintu.rixing.exception.BaseRuntimeException;
import com.yintu.rixing.index.IInxLocalAndRemoteBorrowApplyAndInformService;
import com.yintu.rixing.index.InxLocalAndRemoteBorrowApplyAndInformMapper;
import com.yintu.rixing.notification.INotNoticemessageService;
import com.yintu.rixing.notification.NotNoticemessage;
import com.yintu.rixing.system.ISysArchivesLibraryFieldService;
import com.yintu.rixing.system.ISysArchivesLibraryService;
import com.yintu.rixing.system.SysArchivesLibrary;
import com.yintu.rixing.util.TableNameUtil;
import com.yintu.rixing.vo.index.InxInformVo;
import com.yintu.rixing.vo.index.InxLocalAndRemoteBorrowApplyAndInformVo;
import com.yintu.rixing.vo.index.InxLocalOrRemoteBorrowApplyVo;
import com.yintu.rixing.warehouse.IWareTemplateLibraryFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: mlf
 * @Date: 2021/2/26 11:50:30
 * @Version: 1.0
 */
@Service
public class InxLocalAndRemoteBorrowApplyAndInformServiceImpl implements IInxLocalAndRemoteBorrowApplyAndInformService {

    @Autowired
    private InxLocalAndRemoteBorrowApplyAndInformMapper inxLocalAndRemoteBorrowApplyAndInformMapper;
    @Autowired
    private IDataArchivesLibraryFileService iDataArchivesLibraryFileService;
    @Autowired
    private IDataFormalLibraryService iDataFormalLibraryService;
    @Autowired
    private IWareTemplateLibraryFieldService iWareTemplateLibraryFieldService;
    @Autowired
    private INotNoticemessageService iNotNoticemessageService;

    @Override
    public InxLocalAndRemoteBorrowApplyAndInformVo findInxLocalOrRemoteBorrowApplyAndInformData() {
        InxLocalAndRemoteBorrowApplyAndInformVo inxLocalAndRemoteBorrowApplyAndInformVo = new InxLocalAndRemoteBorrowApplyAndInformVo();
        //本地借阅
        List<Map<String, Object>> maps1 = inxLocalAndRemoteBorrowApplyAndInformMapper.selectInxLocalBorrowApplyData();
        List<InxLocalOrRemoteBorrowApplyVo> localBorrowApplies = this.listInxLocalOrRemoteBorrowApplyVos(maps1);
        inxLocalAndRemoteBorrowApplyAndInformVo.setLocalBorrowApplies(localBorrowApplies);
        //远程借阅
        List<Map<String, Object>> maps2 = inxLocalAndRemoteBorrowApplyAndInformMapper.selectInxRemoteBorrowApplyData();
        List<InxLocalOrRemoteBorrowApplyVo> remoteBorrowApplies = this.listInxLocalOrRemoteBorrowApplyVos(maps2);
        inxLocalAndRemoteBorrowApplyAndInformVo.setRemoteBorrowApplies(remoteBorrowApplies);
        //通知公告
        List<NotNoticemessage> notNoticeMessages = iNotNoticemessageService.listByLimit(4);
        List<InxInformVo> inxInformVos = notNoticeMessages.stream().map(notNoticeMessage -> {
            InxInformVo inxInformVo = new InxInformVo();
            inxInformVo.setTitle(notNoticeMessage.getTitle());
            inxInformVo.setReleaseDate(notNoticeMessage.getModifiedTime());
            return inxInformVo;
        }).collect(Collectors.toList());
        inxLocalAndRemoteBorrowApplyAndInformVo.setInforms(inxInformVos);
        return inxLocalAndRemoteBorrowApplyAndInformVo;
    }

    private List<InxLocalOrRemoteBorrowApplyVo> listInxLocalOrRemoteBorrowApplyVos(List<Map<String, Object>> maps) {
        List<InxLocalOrRemoteBorrowApplyVo> inxLocalOrRemoteBorrowApplyVos = new ArrayList<>();
        for (Map<String, Object> map : maps) {
            InxLocalOrRemoteBorrowApplyVo inxLocalOrRemoteBorrowApplyVo = new InxLocalOrRemoteBorrowApplyVo();
            Short borrowType = (Short) map.get("borrowType");
            Integer fileId = (Integer) map.get("fileId");
            Map<String, Object> commMap;
            if (Short.valueOf((short) 1).equals(borrowType)) {
                DataArchivesLibraryFile dataArchivesLibraryFile = iDataArchivesLibraryFileService.getById(fileId);
                Integer archivesLibraryId = dataArchivesLibraryFile.getArchivesLibraryId();
                Integer dataId = dataArchivesLibraryFile.getDataId();
                String originalName = dataArchivesLibraryFile.getOriginalName();
                commMap = iDataFormalLibraryService.getById(dataId, archivesLibraryId);
                if (commMap != null) {
                    inxLocalOrRemoteBorrowApplyVo.setArchivesNumber((String) commMap.get(EnumArchivesLibraryDefaultField.ARCHIVES_NUM.getDataKey()));
                }
                inxLocalOrRemoteBorrowApplyVo.setArchivesName(originalName);
            } else {
                String tableName = TableNameUtil.tableName;
                Integer count = iWareTemplateLibraryFieldService.findTable(tableName);
                if (count == 0) {
                    throw new BaseRuntimeException("请先创建实体库表");
                }
                commMap = iWareTemplateLibraryFieldService.findByIdAndTableName(fileId, tableName);
                if (commMap != null) {
                    inxLocalOrRemoteBorrowApplyVo.setArchivesNumber((String) commMap.get("archivesNum"));
                    inxLocalOrRemoteBorrowApplyVo.setArchivesName((String) commMap.get("archivesName"));
                }
            }
            inxLocalOrRemoteBorrowApplyVo.setTransactor((String) map.get("transactor"));
            inxLocalOrRemoteBorrowApplyVo.setBorrower((String) map.get("borrower"));
            inxLocalOrRemoteBorrowApplyVo.setBorrowDate((Date) map.get("borrowDate"));
            inxLocalOrRemoteBorrowApplyVos.add(inxLocalOrRemoteBorrowApplyVo);
        }
        return inxLocalOrRemoteBorrowApplyVos;
    }

}

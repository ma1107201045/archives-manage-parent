package com.yintu.rixing.data.impl;

import cn.hutool.core.date.DateUtil;
import com.yintu.rixing.data.DataCommon;
import com.yintu.rixing.data.DataCommonAll;
import com.yintu.rixing.dto.data.DataCommonFormDto;
import com.yintu.rixing.enumobject.EnumDataType;
import com.yintu.rixing.exception.BaseRuntimeException;
import com.yintu.rixing.system.*;
import com.yintu.rixing.util.AssertUtil;
import com.yintu.rixing.util.TableNameUtil;
import com.yintu.rixing.vo.data.DataCommonTitleVo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: mlf
 * @Date: 2021/1/18 18:35:49
 * @Version: 1.0
 */
public class DataCommonService {

    @Autowired
    protected ISysArchivesLibraryService iSysArchivesLibraryService;
    @Autowired
    protected ISysArchivesLibraryFieldService iSysArchivesLibraryFieldService;

    protected DataCommonAll parametersToProofread(DataCommonFormDto dataCommonDto) {
        Integer archivesId = dataCommonDto.getArchivesId();
        AssertUtil.notNull(archivesId, "档案库id不能为空");
        SysArchivesLibrary sysArchivesLibrary = iSysArchivesLibraryService.getById(archivesId);
        if (sysArchivesLibrary == null)
            throw new BaseRuntimeException("档案库不存在");
        DataCommonAll dataCommonAll = new DataCommonAll();
        String tableName = TableNameUtil.getFullTableName(sysArchivesLibrary.getDataKey());
        List<SysArchivesLibraryField> sysArchivesLibraryFields = iSysArchivesLibraryFieldService.listByArchivesLibraryId(archivesId);
        Map<String, String> params = dataCommonDto.getParams();
        List<DataCommon> dataCommons = new ArrayList<>();
        for (SysArchivesLibraryField sysArchivesLibraryField : sysArchivesLibraryFields) {
            String dataKey = sysArchivesLibraryField.getDataKey();
            Integer dataType = sysArchivesLibraryField.getSysTemplateLibraryFieldType().getId();
            Integer length = sysArchivesLibraryField.getLength();
            Short required = sysArchivesLibraryField.getRequired();
            String value = params.get(dataKey);
            Object newValue = value;
            if (required == 1 && (value == null || value.isEmpty()))
                throw new BaseRuntimeException(dataType + "不能为空");
            switch (EnumDataType.get(dataType)) {
                case VARCHAR:
                case TEXT:
                    if (value != null && value.length() > length)
                        throw new BaseRuntimeException(dataType + "长度超过定义的长度");
                    break;
                case INT:
                    newValue = Integer.valueOf(value);
                    break;
                case SMALLINT:
                    newValue = Short.valueOf(value);
                    break;
                case DATE:
                    newValue = DateUtil.parseDate(value);
                    break;
                case DATETIME:
                    newValue = DateUtil.parseDateTime(value);
                    break;
            }
            DataCommon dataCommon = new DataCommon();
            dataCommon.setFieldName(dataKey);
            dataCommon.setFieldValue(newValue);
            dataCommons.add(dataCommon);
        }
        dataCommonAll.setTableName(tableName);
        dataCommonAll.setId(dataCommonDto.getId());
        dataCommonAll.setDataCommons(dataCommons);
        return dataCommonAll;
    }

    protected List<DataCommonTitleVo> getDataCommonTitles(Integer archivesId) {
        List<SysArchivesLibraryField> sysArchivesLibraryFields = iSysArchivesLibraryFieldService.listByArchivesLibraryId(archivesId);
        List<DataCommonTitleVo> dataCommonTitleVos = new ArrayList<>();
        dataCommonTitleVos.add(getIdTitle());
        for (SysArchivesLibraryField sysArchivesLibraryField : sysArchivesLibraryFields) {
            DataCommonTitleVo dataCommonTitleVo = new DataCommonTitleVo();
            dataCommonTitleVo.setProp(sysArchivesLibraryField.getDataKey());
            dataCommonTitleVo.setLabel(sysArchivesLibraryField.getName());
            SysTemplateLibraryFieldType sysTemplateLibraryFieldType = sysArchivesLibraryField.getSysTemplateLibraryFieldType();
            dataCommonTitleVo.setTypeProp(sysTemplateLibraryFieldType.getDataKey());
            dataCommonTitleVo.setTypeLabel(sysTemplateLibraryFieldType.getName());
        }

        return dataCommonTitleVos;
    }

    protected DataCommonTitleVo getIdTitle() {
        DataCommonTitleVo dataCommonTitleVo = new DataCommonTitleVo();
        dataCommonTitleVo.setProp("id");
        dataCommonTitleVo.setLabel("主键id");
        dataCommonTitleVo.setTypeProp("int");
        dataCommonTitleVo.setTypeLabel("文本框(数值)");
        return dataCommonTitleVo;
    }


}
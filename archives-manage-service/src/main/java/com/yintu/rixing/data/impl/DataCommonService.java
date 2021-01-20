package com.yintu.rixing.data.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.http.server.HttpServerRequest;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.yintu.rixing.data.DataCommon;
import com.yintu.rixing.data.DataCommonAll;
import com.yintu.rixing.data.DataCommonMapper;
import com.yintu.rixing.dto.data.DataCommonFormDto;
import com.yintu.rixing.enumobject.EnumDataType;
import com.yintu.rixing.exception.BaseRuntimeException;
import com.yintu.rixing.system.*;
import com.yintu.rixing.util.AssertUtil;
import com.yintu.rixing.util.TableNameUtil;
import com.yintu.rixing.vo.data.DataCommonTitleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: mlf
 * @Date: 2021/1/18 18:35:49
 * @Version: 1.0
 */
@Service
public class DataCommonService {
    @Autowired
    private DataCommonMapper dataCommonMapper;
    @Autowired
    protected ISysArchivesLibraryService iSysArchivesLibraryService;
    @Autowired
    protected ISysArchivesLibraryFieldService iSysArchivesLibraryFieldService;

    public Map<String, Object> getById(DataCommonAll dataCommonAll) {
        return dataCommonMapper.selectByPrimaryKey(dataCommonAll);
    }

    protected DataCommonAll parametersToProofread(DataCommonFormDto dataCommonDto) {
        Integer archivesLibraryId = dataCommonDto.getArchivesLibraryId();
        SysArchivesLibrary sysArchivesLibrary = iSysArchivesLibraryService.getById(archivesLibraryId);
        AssertUtil.notNull(sysArchivesLibrary, "档案库不存在");
        DataCommonAll dataCommonAll = new DataCommonAll();
        String tableName = TableNameUtil.getFullTableName(sysArchivesLibrary.getDataKey());
        List<SysArchivesLibraryField> sysArchivesLibraryFields = iSysArchivesLibraryFieldService.listByArchivesLibraryId(archivesLibraryId);
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
                case TINYINT:
                    newValue = Byte.valueOf(value);
                    break;
                case SMALLINT:
                    newValue = Short.valueOf(value);
                    break;
                case INT:
                    newValue = Integer.valueOf(value);
                    break;
                case DATETIME:
                    newValue = DateUtil.parseDateTime(value);
                    break;
                case DATE:
                    newValue = DateUtil.parseDate(value);
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

    private List<DataCommonTitleVo> getDefaultDataCommonTitles(Integer archivesLibraryId) {
        List<SysArchivesLibraryField> sysArchivesLibraryFields = iSysArchivesLibraryFieldService.listByArchivesLibraryId(archivesLibraryId);
        List<DataCommonTitleVo> dataCommonTitleVos = new ArrayList<>();
        for (SysArchivesLibraryField sysArchivesLibraryField : sysArchivesLibraryFields) {
            DataCommonTitleVo dataCommonTitleVo = new DataCommonTitleVo();
            dataCommonTitleVo.setProp(sysArchivesLibraryField.getDataKey());
            dataCommonTitleVo.setLabel(sysArchivesLibraryField.getName());
            dataCommonTitleVo.setShow(true);
            SysTemplateLibraryFieldType sysTemplateLibraryFieldType = sysArchivesLibraryField.getSysTemplateLibraryFieldType();
            dataCommonTitleVo.setTypeId(sysTemplateLibraryFieldType.getId());
            dataCommonTitleVo.setTypeProp(sysTemplateLibraryFieldType.getDataKey());
            dataCommonTitleVo.setTypeLabel(sysTemplateLibraryFieldType.getName());
            dataCommonTitleVo.setNotNull(sysArchivesLibraryField.getRequired() == 1);
            dataCommonTitleVos.add(dataCommonTitleVo);
        }
        return dataCommonTitleVos;
    }

    protected List<DataCommonTitleVo> getDataCommonTitles(Integer archivesLibraryId) {
        List<DataCommonTitleVo> dataCommonTitleVos = this.getDefaultDataCommonTitles(archivesLibraryId);
        dataCommonTitleVos.add(this.getIdTitle());
        dataCommonTitleVos.add(this.getStatusTitle());
        return dataCommonTitleVos;
    }

    protected DataCommonTitleVo getIdTitle() {
        DataCommonTitleVo dataCommonTitleVo = new DataCommonTitleVo();
        dataCommonTitleVo.setProp("id");
        dataCommonTitleVo.setLabel("主键id");

        dataCommonTitleVo.setShow(false);
        dataCommonTitleVo.setTypeId(EnumDataType.INT.getValue());
        dataCommonTitleVo.setTypeProp("int");
        dataCommonTitleVo.setTypeLabel(EnumDataType.INT.getName());
        dataCommonTitleVo.setNotNull(false);
        return dataCommonTitleVo;
    }

    protected DataCommonTitleVo getStatusTitle() {
        DataCommonTitleVo dataCommonTitleVo = new DataCommonTitleVo();
        dataCommonTitleVo.setProp("status");
        dataCommonTitleVo.setLabel("档案状态");

        dataCommonTitleVo.setShow(false);
        dataCommonTitleVo.setTypeId(EnumDataType.SMALLINT.getValue());
        dataCommonTitleVo.setTypeProp("smallint");
        dataCommonTitleVo.setTypeLabel(EnumDataType.SMALLINT.getName());
        dataCommonTitleVo.setNotNull(false);
        return dataCommonTitleVo;
    }

    protected List<Map<String, Object>> getDataCommonRecord(Integer archivesLibraryId, Set<Integer> ids) {
        SysArchivesLibrary sysArchivesLibrary = this.iSysArchivesLibraryService.getById(archivesLibraryId);
        AssertUtil.notNull(sysArchivesLibrary, "档案库不能为空");
        String tableName = TableNameUtil.getFullTableName(sysArchivesLibrary.getDataKey());
        // 表头
        List<DataCommonTitleVo> dataCommonTitleVos = this.getDefaultDataCommonTitles(archivesLibraryId);
        List<String> props = dataCommonTitleVos.stream().map(DataCommonTitleVo::getProp).collect(Collectors.toList());
        //记录数
        List<Map<String, Object>> records = this.dataCommonMapper.selectByPrimaryKeys(tableName, ids);
        List<Map<String, Object>> finalRecords = new ArrayList<>();
        props.forEach(prop -> records.forEach(map -> {
            if (map.containsKey(prop))
                finalRecords.add(map);
        }));
        return finalRecords;
    }


    protected void importExcelFile(HttpServerRequest request) {

    }

    protected void exportExcelFile(HttpServletResponse response, String fileName, Set<Integer> ids, Integer archivesLibraryId) throws IOException {
        String fullName = fileName + ".xlsx";
        //表头
        List<DataCommonTitleVo> dataCommonTitleVos = this.getDefaultDataCommonTitles(archivesLibraryId);
        List<String> titles = dataCommonTitleVos.stream().map(DataCommonTitleVo::getLabel).collect(Collectors.toList());
        ExcelWriter excelWriter = ExcelUtil.getWriter(true);
        excelWriter.merge(titles.size() - 1, fileName);
        excelWriter.writeHeadRow(titles);
        //表数据
        if (ids != null) {
            List<Map<String, Object>> records = this.getDataCommonRecord(archivesLibraryId, ids);
            excelWriter.write(records);
        }
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        ServletOutputStream out = response.getOutputStream();
        excelWriter.flush(out, true);
        excelWriter.close();
        IoUtil.close(out);
        //response.setContentType("application/vnd.ms-excel;charset=utf-8");
        //response.setHeader("Content-Disposition","attachment;filename=test.xls");
        //response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        //response.setHeader("Content-Disposition", "attachment;filename=test.xlsx");
    }


}

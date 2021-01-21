package com.yintu.rixing.data.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.net.URLEncoder;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.server.HttpServerRequest;
import cn.hutool.poi.excel.ExcelFileUtil;
import cn.hutool.poi.excel.ExcelReader;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: mlf
 * @Date: 2021/1/18 18:35:49
 * @Version: 1.0
 */
@Service
public class DataCommonService {

    protected static final String ID_PROP = "id";
    protected static final String ID_LABEL = "主键id";
    protected static final String STATUS_PROP = "status";
    protected static final String STATUS_LABEL = "档案状态";

    protected static final String TEMPLATE = "模板";
    protected static final String RECORD = "记录";
    protected static final String SUFFIX = ".xlsx";

    @Autowired
    private DataCommonMapper dataCommonMapper;
    @Autowired
    protected ISysArchivesLibraryService iSysArchivesLibraryService;
    @Autowired
    protected ISysArchivesLibraryFieldService iSysArchivesLibraryFieldService;


    protected DataCommonAll parametersToProofread(DataCommonFormDto dataCommonDto) {
        Integer archivesLibraryId = dataCommonDto.getArchivesLibraryId();
        SysArchivesLibrary sysArchivesLibrary = iSysArchivesLibraryService.getById(archivesLibraryId);
        AssertUtil.notNull(sysArchivesLibrary, "档案库不存在");
        List<SysArchivesLibraryField> sysArchivesLibraryFields = iSysArchivesLibraryFieldService.listByArchivesLibraryId(archivesLibraryId);
        Map<String, String> params = dataCommonDto.getParams();
        List<DataCommon> dataCommons = new ArrayList<>();
        for (SysArchivesLibraryField sysArchivesLibraryField : sysArchivesLibraryFields) {
            String name = sysArchivesLibraryField.getName();
            String dataKey = sysArchivesLibraryField.getDataKey();
            Integer dataType = sysArchivesLibraryField.getSysTemplateLibraryFieldType().getId();
            Integer length = sysArchivesLibraryField.getLength();
            Short required = sysArchivesLibraryField.getRequired();
            String value = params.get(dataKey);
            Object newValue = value;
            if (required == 1 && (value == null || value.isEmpty()))
                throw new BaseRuntimeException(name + "不能为空");
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
        DataCommonAll dataCommonAll = new DataCommonAll();
        String tableName = TableNameUtil.getFullTableName(sysArchivesLibrary.getDataKey());
        dataCommonAll.setTableName(tableName);
        dataCommonAll.setId(dataCommonDto.getId());
        dataCommonAll.setDataCommons(dataCommons);
        return dataCommonAll;
    }

    protected DataCommonAll importExcelFile(MultipartFile multipartFile, Integer archivesLibraryId) throws IOException {
        //浅层次判断此文件是否是excel文件
        String ext = FileUtil.extName(multipartFile.getOriginalFilename());
        if (!("xls".equals(ext) || "xlsx".equals(ext)))
            throw new BaseRuntimeException("此文件类型不支持批量导入");
        InputStream in = multipartFile.getInputStream();
        ExcelReader excelReader = ExcelUtil.getReader(in, true);
        List<Map<String, Object>> records = excelReader.readAll();

        SysArchivesLibrary sysArchivesLibrary = iSysArchivesLibraryService.getById(archivesLibraryId);
        AssertUtil.notNull(sysArchivesLibrary, "档案库不存在");
        List<SysArchivesLibraryField> sysArchivesLibraryFields = iSysArchivesLibraryFieldService.listByArchivesLibraryId(archivesLibraryId);
        List<List<DataCommon>> lists = new ArrayList<>();

        for (Map<String, Object> record : records) {
            List<DataCommon> dataCommons = new ArrayList<>();
            for (SysArchivesLibraryField sysArchivesLibraryField : sysArchivesLibraryFields) {
                String name = sysArchivesLibraryField.getName();
                String dataKey = sysArchivesLibraryField.getDataKey();
                Integer dataType = sysArchivesLibraryField.getSysTemplateLibraryFieldType().getId();
                Integer length = sysArchivesLibraryField.getLength();
                Short required = sysArchivesLibraryField.getRequired();
                Object obj = record.get(name);
                if (required == 1 && (obj == null || (obj instanceof String && ((String) obj).isEmpty())))
                    throw new BaseRuntimeException(name + "不能为空");
                String value = String.valueOf(obj);
                Object newValue = value;
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
            lists.add(dataCommons);
        }
        DataCommonAll dataCommonAll = new DataCommonAll();
        String tableName = TableNameUtil.getFullTableName(sysArchivesLibrary.getDataKey());
        dataCommonAll.setTableName(tableName);
        dataCommonAll.setLists(lists);

        excelReader.close();
        IoUtil.close(in);
        return dataCommonAll;
    }

    public Map<String, Object> getById(DataCommonAll dataCommonAll) {
        return dataCommonMapper.selectByPrimaryKey(dataCommonAll);
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
        dataCommonTitleVo.setProp(ID_PROP);
        dataCommonTitleVo.setLabel(ID_LABEL);

        dataCommonTitleVo.setShow(false);
        dataCommonTitleVo.setTypeId(EnumDataType.INT.getValue());
        dataCommonTitleVo.setTypeProp("int");
        dataCommonTitleVo.setTypeLabel(EnumDataType.INT.getName());
        dataCommonTitleVo.setNotNull(false);
        return dataCommonTitleVo;
    }

    protected DataCommonTitleVo getStatusTitle() {
        DataCommonTitleVo dataCommonTitleVo = new DataCommonTitleVo();
        dataCommonTitleVo.setProp(STATUS_PROP);
        dataCommonTitleVo.setLabel(STATUS_LABEL);

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
        return this.dataCommonMapper.selectByPrimaryKeys(tableName, ids);
    }


    protected void exportExcelFile(HttpServletResponse response, String fileName, Set<Integer> ids, Integer archivesLibraryId) throws IOException {
        SysArchivesLibrary sysArchivesLibrary = iSysArchivesLibraryService.getById(archivesLibraryId);
        AssertUtil.notNull(sysArchivesLibrary, "档案库不存在");
        String name = sysArchivesLibrary.getName();
        long timestamp = DateUtil.date().getTime();
        List<DataCommonTitleVo> dataCommonTitleVos = this.getDefaultDataCommonTitles(archivesLibraryId);
        ExcelWriter excelWriter = ExcelUtil.getWriter(true);
        // excelWriter.merge(dataCommonTitleVos.size() - 1, fileName);
        String fullName = null;
        if (ids == null) { //下载模板
            List<String> titles = dataCommonTitleVos.stream().map(DataCommonTitleVo::getLabel).collect(Collectors.toList());
            excelWriter.writeHeadRow(titles);
            fullName = fileName + TEMPLATE + name + timestamp + SUFFIX;
        } else { //导出数据
            for (DataCommonTitleVo dataCommonTitleVo : dataCommonTitleVos) {
                String prop = dataCommonTitleVo.getProp();
                String label = dataCommonTitleVo.getLabel();
                excelWriter.addHeaderAlias(prop, label);
            }
            excelWriter.setOnlyAlias(true);//仅仅显示表头的数据，则可以过滤掉无用的字段
            List<Map<String, Object>> records = this.getDataCommonRecord(archivesLibraryId, ids);
            excelWriter.write(records, true);
            fullName = fileName + RECORD + name + timestamp + SUFFIX;
        }
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=ISO8859-1");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(fullName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
        ServletOutputStream out = response.getOutputStream();
        excelWriter.flush(out, true);
        excelWriter.close();
        IoUtil.close(out);
    }


}

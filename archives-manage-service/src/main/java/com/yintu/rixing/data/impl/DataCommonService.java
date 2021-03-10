package com.yintu.rixing.data.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.yintu.rixing.data.DataCommonKV;
import com.yintu.rixing.data.DataCommon;
import com.yintu.rixing.data.DataCommonMapper;
import com.yintu.rixing.dto.data.DataCommonFormDto;
import com.yintu.rixing.dto.data.DataCommonQueryDto;
import com.yintu.rixing.enumobject.EnumDataType;
import com.yintu.rixing.enumobject.EnumArchivesLibraryDefaultField;
import com.yintu.rixing.enumobject.EnumFlag;
import com.yintu.rixing.exception.BaseRuntimeException;
import com.yintu.rixing.system.*;
import com.yintu.rixing.util.AssertUtil;
import com.yintu.rixing.util.TableNameUtil;
import com.yintu.rixing.vo.data.DataCommonFieldVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
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

    protected static final String TEMPLATE = "模板";
    protected static final String RECORD = "记录";
    protected static final String SUFFIX = ".xlsx";

    @Autowired
    private DataCommonMapper dataCommonMapper;
    @Autowired
    protected ISysArchivesLibraryService iSysArchivesLibraryService;
    @Autowired
    protected ISysArchivesLibraryFieldDefaultService iSysArchivesLibraryFieldDefaultService;
    @Autowired
    protected ISysArchivesLibraryFieldService iSysArchivesLibraryFieldService;
    @Autowired
    protected ISysDepartmentService iSysDepartmentService;

    protected DataCommon saveOrUpdateHandler(DataCommonFormDto dataCommonFormDto) {
        Integer archivesLibraryId = dataCommonFormDto.getArchivesLibraryId();
        Integer id = dataCommonFormDto.getId();
        SysArchivesLibrary sysArchivesLibrary = iSysArchivesLibraryService.getById(archivesLibraryId);
        AssertUtil.notNull(sysArchivesLibrary, "档案库不存在");
        List<SysArchivesLibraryField> sysArchivesLibraryFields = iSysArchivesLibraryFieldService.listByArchivesLibraryIdAndForm(archivesLibraryId);
        Map<String, String> params = dataCommonFormDto.getParams();
        List<DataCommonKV> dataCommonKVS = new ArrayList<>(this.getDefaultFields(id));
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
                        throw new BaseRuntimeException(name + "[长度超过定义的长度]");
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
                    if ("".equals(value))
                        newValue = null;
                    else
                        newValue = DateUtil.parseDateTime(value);
                    break;
                case DATE:
                    if ("".equals(value))
                        newValue = null;
                    else
                        newValue = DateUtil.parseDate(value);
                    break;
            }
            DataCommonKV dataCommonKV = new DataCommonKV();
            dataCommonKV.setFieldName(dataKey);
            dataCommonKV.setFieldValue(newValue);
            dataCommonKVS.add(dataCommonKV);
        }
        DataCommon dataCommon = new DataCommon();
        String tableName = TableNameUtil.getFullTableName(sysArchivesLibrary.getDataKey());
        dataCommon.setTableName(tableName);
        dataCommon.setId(dataCommonFormDto.getId());
        dataCommon.setDataCommonKVs(dataCommonKVS);
        return dataCommon;
    }

    protected List<DataCommonKV> getDefaultFields(Integer id) {
        List<DataCommonKV> dataCommonKVS = new ArrayList<>();
        if (id == null) {//判断是否是新增还是删除
            DataCommonKV dataCommonKV1 = new DataCommonKV();
            dataCommonKV1.setFieldName(EnumArchivesLibraryDefaultField.CREATE_BY.getDataKey());
            dataCommonKV1.setFieldValue(getLoginUsername());

            DataCommonKV dataCommonKV2 = new DataCommonKV();
            dataCommonKV2.setFieldName(EnumArchivesLibraryDefaultField.CREATE_TIME.getDataKey());
            dataCommonKV2.setFieldValue(DateUtil.date());

            DataCommonKV dataCommonKV = new DataCommonKV();
            dataCommonKV.setFieldName(EnumArchivesLibraryDefaultField.ARCHIVES_NUM.getDataKey());
            dataCommonKV.setFieldValue("DH-" + UUID.randomUUID().toString(true).substring(0, 5));

            dataCommonKVS.add(dataCommonKV1);
            dataCommonKVS.add(dataCommonKV2);
            dataCommonKVS.add(dataCommonKV);
        }
        DataCommonKV dataCommonKV3 = new DataCommonKV();
        dataCommonKV3.setFieldName(EnumArchivesLibraryDefaultField.MODIFIED_BY.getDataKey());
        dataCommonKV3.setFieldValue(getLoginUsername());
        DataCommonKV dataCommonKV4 = new DataCommonKV();
        dataCommonKV4.setFieldName(EnumArchivesLibraryDefaultField.MODIFIED_TIME.getDataKey());
        dataCommonKV4.setFieldValue(DateUtil.date());
        dataCommonKVS.add(dataCommonKV3);
        dataCommonKVS.add(dataCommonKV4);
        return dataCommonKVS;
    }

    protected DataCommonKV getStatusField(Short status) {
        DataCommonKV dataCommonKV = new DataCommonKV();
        dataCommonKV.setFieldName(EnumArchivesLibraryDefaultField.STATUS.getDataKey());
        dataCommonKV.setFieldValue(status);
        return dataCommonKV;
    }

    protected static String getLoginUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication == null ? "unknown" : authentication.getPrincipal() == null ? "unknown" : ((SysUser) authentication.getPrincipal()).getUsername();
    }

    protected DataCommon removeOrGetHandler(Integer id, Integer archivesLibraryId) {
        AssertUtil.notNull(archivesLibraryId, "档案库id不能为空");
        SysArchivesLibrary sysArchivesLibrary = this.iSysArchivesLibraryService.getById(archivesLibraryId);
        AssertUtil.notNull(sysArchivesLibrary, "档案库不能为空");
        String tableName = TableNameUtil.getFullTableName(sysArchivesLibrary.getDataKey());
        DataCommon dataCommon = new DataCommon();
        dataCommon.setId(id);
        dataCommon.setTableName(tableName);
        return dataCommon;
    }

    protected DataCommon page(DataCommonQueryDto dataCommonQueryDto) {
        Integer archivesLibraryId = dataCommonQueryDto.getArchivesLibraryId();
        SysArchivesLibrary sysArchivesLibrary = this.iSysArchivesLibraryService.getById(archivesLibraryId);
        AssertUtil.notNull(sysArchivesLibrary, "档案库不能为空");
        List<SysArchivesLibraryField> sysArchivesLibraryFields = iSysArchivesLibraryFieldService.listByArchivesLibraryIdAndQuery(archivesLibraryId);
        Map<String, String> params = dataCommonQueryDto.getParams();
        List<DataCommonKV> dataCommonKVS = new ArrayList<>();
        for (SysArchivesLibraryField sysArchivesLibraryField : sysArchivesLibraryFields) {
            String dataKey = sysArchivesLibraryField.getDataKey();
            Integer dataType = sysArchivesLibraryField.getSysTemplateLibraryFieldType().getId();
            String value = params.get(dataKey);
            if (value == null || "".equals(value)) {
                continue;
            }
            Object newValue = value;
            switch (EnumDataType.get(dataType)) {
                case VARCHAR:
                case TEXT:
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
                default:
                    break;
            }
            DataCommonKV dataCommonKV = new DataCommonKV();
            dataCommonKV.setFieldName(dataKey);
            dataCommonKV.setFieldValue(newValue);
            dataCommonKVS.add(dataCommonKV);
        }
        DataCommon dataCommon = new DataCommon();
        String tableName = TableNameUtil.getFullTableName(sysArchivesLibrary.getDataKey());
        dataCommon.setTableName(tableName);
        dataCommon.setDataCommonKVs(dataCommonKVS);
        return dataCommon;
    }

    protected DataCommon importExcelFile(MultipartFile multipartFile, Integer archivesLibraryId) throws IOException {
        //浅层次判断此文件是否是excel文件
        String ext = FileUtil.extName(multipartFile.getOriginalFilename());
        if (!("xls".equals(ext) || "xlsx".equals(ext))) {
            throw new BaseRuntimeException("此文件类型不支持批量导入");
        }
        InputStream in = multipartFile.getInputStream();
        ExcelReader excelReader = ExcelUtil.getReader(in, true);
        List<Map<String, Object>> records = excelReader.readAll();

        SysArchivesLibrary sysArchivesLibrary = iSysArchivesLibraryService.getById(archivesLibraryId);
        AssertUtil.notNull(sysArchivesLibrary, "档案库不存在");
        List<SysArchivesLibraryField> sysArchivesLibraryFields = iSysArchivesLibraryFieldService.listByArchivesLibraryIdAndForm(archivesLibraryId);
        List<List<DataCommonKV>> lists = new ArrayList<>();
        for (Map<String, Object> record : records) {
            List<DataCommonKV> dataCommons = new ArrayList<>(this.getDefaultFields(null));
            for (SysArchivesLibraryField sysArchivesLibraryField : sysArchivesLibraryFields) {
                String name = sysArchivesLibraryField.getName();
                String dataKey = sysArchivesLibraryField.getDataKey();
                Integer dataType = sysArchivesLibraryField.getSysTemplateLibraryFieldType().getId();
                Integer length = sysArchivesLibraryField.getLength();
                Short required = sysArchivesLibraryField.getRequired();
                String value = (String) record.get(name);
                if (required == 1 && (value == null || value.isEmpty())) {
                    throw new BaseRuntimeException(name + "不能为空");
                }
                Object newValue = value;
                switch (EnumDataType.get(dataType)) {
                    case VARCHAR:
                    case TEXT:
                        if (value != null && value.length() > length) {
                            throw new BaseRuntimeException(dataType + "长度超过定义的长度");
                        }
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
                        if ("".equals(value)) {
                            newValue = null;
                        } else {
                            newValue = DateUtil.parseDateTime(value);
                        }
                        break;
                    case DATE:
                        if ("".equals(value)) {
                            newValue = null;
                        } else {
                            newValue = DateUtil.parseDate(value);
                        }
                        break;
                    default:
                        break;
                }
                DataCommonKV dataCommon = new DataCommonKV();
                dataCommon.setFieldName(dataKey);
                dataCommon.setFieldValue(newValue);
                dataCommons.add(dataCommon);
            }
            lists.add(dataCommons);
        }
        DataCommon dataCommon = new DataCommon();
        String tableName = TableNameUtil.getFullTableName(sysArchivesLibrary.getDataKey());
        dataCommon.setTableName(tableName);
        dataCommon.setLists(lists);

        excelReader.close();
        IoUtil.close(in);
        return dataCommon;
    }

    public Map<String, Object> getById(DataCommon dataCommon) {
        return dataCommonMapper.selectByPrimaryKey(dataCommon);
    }

    protected List<DataCommonFieldVo> getDataCommonFieldVos(Integer archivesLibraryId) {
        List<SysArchivesLibraryField> sysArchivesLibraryFields = iSysArchivesLibraryFieldService.listByArchivesLibraryId(archivesLibraryId);
        List<DataCommonFieldVo> dataCommonFieldVos = new ArrayList<>();
        for (SysArchivesLibraryField sysArchivesLibraryField : sysArchivesLibraryFields) {

            String dataKey = sysArchivesLibraryField.getDataKey();
            String name = sysArchivesLibraryField.getName();

            SysDataType sysTemplateLibraryFieldType = sysArchivesLibraryField.getSysTemplateLibraryFieldType();
            Integer fieldTypeId = sysTemplateLibraryFieldType.getId();
            String fieldTypeDataKey = sysTemplateLibraryFieldType.getDataKey();
            String fieldTypeName = sysTemplateLibraryFieldType.getName();

            Short query = sysArchivesLibraryField.getQuery();
            Short title = sysArchivesLibraryField.getTitle();
            Short form = sysArchivesLibraryField.getForm();

            DataCommonFieldVo dataCommonTitleVo = new DataCommonFieldVo();
            dataCommonTitleVo.setProp(dataKey);
            dataCommonTitleVo.setLabel(name);

            dataCommonTitleVo.setTypeId(fieldTypeId);
            dataCommonTitleVo.setTypeProp(fieldTypeDataKey);
            dataCommonTitleVo.setTypeLabel(fieldTypeName);
            dataCommonTitleVo.setNotNull(sysArchivesLibraryField.getRequired().equals(EnumFlag.True.getValue()));

            dataCommonTitleVo.setQuery(query.equals(EnumFlag.True.getValue()));
            dataCommonTitleVo.setTitle(title.equals(EnumFlag.True.getValue()));
            dataCommonTitleVo.setForm(form.equals(EnumFlag.True.getValue()));
            dataCommonFieldVos.add(dataCommonTitleVo);
        }
        return dataCommonFieldVos;
    }


    protected List<Map<String, Object>> getDataCommonRecord(Integer archivesLibraryId, Set<Integer> ids) {
        SysArchivesLibrary sysArchivesLibrary = this.iSysArchivesLibraryService.getById(archivesLibraryId);
        AssertUtil.notNull(sysArchivesLibrary, "档案库不能为空");
        String tableName = TableNameUtil.getFullTableName(sysArchivesLibrary.getDataKey());
        return this.dataCommonMapper.selectByPrimaryKeys(tableName, ids);

    }


    protected void exportExcelFile(HttpServletResponse response, String fileName, Set<Integer> ids, Integer
            archivesLibraryId) throws IOException {
        SysArchivesLibrary sysArchivesLibrary = iSysArchivesLibraryService.getById(archivesLibraryId);
        AssertUtil.notNull(sysArchivesLibrary, "档案库不存在");
        String name = sysArchivesLibrary.getName();
        long timestamp = DateUtil.date().getTime();
        List<SysArchivesLibraryField> sysArchivesLibraryFields = this.iSysArchivesLibraryFieldService.listByArchivesLibraryIdAndTitle(archivesLibraryId);
        ExcelWriter excelWriter = ExcelUtil.getWriter(true);
        // excelWriter.merge(dataCommonFieldVos.size() - 1, fileName);
        String fullName;
        if (ids == null) { //下载模板
            List<String> titles = sysArchivesLibraryFields.stream().map(SysArchivesLibraryField::getName).collect(Collectors.toList());
            excelWriter.writeHeadRow(titles);
            fullName = fileName + TEMPLATE + name + timestamp + SUFFIX;
        } else { //导出数据
            for (SysArchivesLibraryField sysArchivesLibraryField : sysArchivesLibraryFields) {
                String prop = sysArchivesLibraryField.getDataKey();
                String label = sysArchivesLibraryField.getName();
                excelWriter.addHeaderAlias(prop, label);
            }
            excelWriter.setOnlyAlias(true);//仅仅显示表头的数据，则可以过滤掉无用的字段
            List<Map<String, Object>> records = this.getDataCommonRecord(archivesLibraryId, ids);
            //特殊字段需要处理
            records.forEach(map -> {
                String dataKey = EnumArchivesLibraryDefaultField.DEPARTMENT_ID.getDataKey();
                if (map.containsKey(dataKey)) {
                    Integer departmentId = (Integer) map.get(dataKey);
                    SysDepartment sysDepartment = iSysDepartmentService.getById(departmentId);
                    map.put("dataKey", sysDepartment.getName());
                }
            });
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

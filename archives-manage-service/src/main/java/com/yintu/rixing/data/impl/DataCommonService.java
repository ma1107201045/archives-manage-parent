package com.yintu.rixing.data.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.yintu.rixing.data.DataCommon;
import com.yintu.rixing.data.DataCommonAll;
import com.yintu.rixing.data.DataCommonMapper;
import com.yintu.rixing.dto.data.DataCommonFormDto;
import com.yintu.rixing.enumobject.EnumDataType;
import com.yintu.rixing.enumobject.EnumFlag;
import com.yintu.rixing.exception.BaseRuntimeException;
import com.yintu.rixing.system.*;
import com.yintu.rixing.util.AssertUtil;
import com.yintu.rixing.util.TableNameUtil;
import com.yintu.rixing.vo.data.DataCommonFieldVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
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

    protected List<DataCommonFieldVo> getDataCommonFields(Integer archivesLibraryId) {
        List<SysArchivesLibraryField> sysArchivesLibraryFields = iSysArchivesLibraryFieldService.listByArchivesLibraryId(archivesLibraryId);
        List<DataCommonFieldVo> dataCommonFieldVos = new ArrayList<>();
        for (SysArchivesLibraryField sysArchivesLibraryField : sysArchivesLibraryFields) {

            String dataKey = sysArchivesLibraryField.getDataKey();
            String name = sysArchivesLibraryField.getName();

            SysTemplateLibraryFieldType sysTemplateLibraryFieldType = sysArchivesLibraryField.getSysTemplateLibraryFieldType();
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
            dataCommonTitleVo.setNotNull(sysArchivesLibraryField.getRequired() == 1);

            dataCommonTitleVo.setQuery(query.equals(EnumFlag.True.getValue()));
            dataCommonTitleVo.setTitle(title.equals(EnumFlag.True.getValue()));
            dataCommonTitleVo.setForm(form.equals(EnumFlag.True.getValue()));
            dataCommonFieldVos.add(dataCommonTitleVo);
        }
        return dataCommonFieldVos;
    }

    private List<DataCommonFieldVo> getDataCommonQueryFields(Integer archivesLibraryId) {
        return this.getDataCommonFields(archivesLibraryId).stream().filter(DataCommonFieldVo::getQuery).collect(Collectors.toList());
    }

    private List<DataCommonFieldVo> getDataCommonTitleFields(Integer archivesLibraryId) {
        return this.getDataCommonFields(archivesLibraryId).stream().filter(DataCommonFieldVo::getTitle).collect(Collectors.toList());
    }

    private List<DataCommonFieldVo> getDataCommonFormFields(Integer archivesLibraryId) {
        return this.getDataCommonFields(archivesLibraryId).stream().filter(DataCommonFieldVo::getForm).collect(Collectors.toList());
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
        List<DataCommonFieldVo> dataCommonFieldVos = this.getDataCommonTitleFields(archivesLibraryId);
        ExcelWriter excelWriter = ExcelUtil.getWriter(true);
        // excelWriter.merge(dataCommonFieldVos.size() - 1, fileName);
        String fullName = null;
        if (ids == null) { //下载模板
            List<String> titles = dataCommonFieldVos.stream().map(DataCommonFieldVo::getLabel).collect(Collectors.toList());
            excelWriter.writeHeadRow(titles);
            fullName = fileName + TEMPLATE + name + timestamp + SUFFIX;
        } else { //导出数据
            for (DataCommonFieldVo dataCommonFieldVo : dataCommonFieldVos) {
                if (dataCommonFieldVo.getTitle()) {
                    String prop = dataCommonFieldVo.getProp();
                    String label = dataCommonFieldVo.getLabel();
                    excelWriter.addHeaderAlias(prop, label);
                }
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

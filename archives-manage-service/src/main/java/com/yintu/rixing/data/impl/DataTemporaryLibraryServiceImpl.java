package com.yintu.rixing.data.impl;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yintu.rixing.data.DataCommon;
import com.yintu.rixing.data.DataCommonKV;
import com.yintu.rixing.data.DataTemporaryLibraryMapper;
import com.yintu.rixing.data.IDataTemporaryLibraryService;
import com.yintu.rixing.dto.data.DataCommonFormDto;
import com.yintu.rixing.dto.data.DataCommonMarkDto;
import com.yintu.rixing.dto.data.DataCommonQueryDto;
import com.yintu.rixing.enumobject.EnumArchivesLibraryDefaultField;
import com.yintu.rixing.enumobject.EnumArchivesOrder;
import com.yintu.rixing.system.SysDepartment;
import com.yintu.rixing.vo.data.DataCommonFieldVo;
import com.yintu.rixing.vo.data.DataCommonVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author: mlf
 * @Date: 2021/1/21 16:06:33
 * @Version: 1.0
 */
@Service
public class DataTemporaryLibraryServiceImpl extends DataCommonService implements IDataTemporaryLibraryService {
    @Autowired
    private DataTemporaryLibraryMapper dataTemporaryLibraryMapper;

    @Override
    public void save(DataCommonFormDto dataCommonFormDto) {
        DataCommon dataCommon = this.saveOrUpdateHandler(dataCommonFormDto);
        dataCommon.getDataCommonKVs().add(this.getStatusField(EnumArchivesOrder.TEMPORARY_LIBRARY.getValue()));
        dataTemporaryLibraryMapper.insertSelective(dataCommon);
    }

    @Override
    public void removeByIds(Set<Integer> ids, Integer archivesLibraryId) {
//        DataCommon dataCommon = this.removeOrGetHandler(null, archivesLibraryId);
//        dataTemporaryLibraryMapper.deleteByPrimaryKeys(ids, dataCommon.getTableName());
        for (Integer id : ids) {
            this.updateStatusById(id, archivesLibraryId, EnumArchivesOrder.RECYCLE_BIN.getValue());
        }
    }

    @Override
    public void updateById(DataCommonFormDto dataCommonFormDto) {
        DataCommon dataCommon = this.saveOrUpdateHandler(dataCommonFormDto);
        Map<String, Object> map = dataTemporaryLibraryMapper.selectByPrimaryKey(dataCommon);
        if (map != null) {
            dataTemporaryLibraryMapper.updateByPrimaryKeySelective(dataCommon);
        }
    }

    @Override
    public void updateStatusById(Integer id, Integer archivesLibraryId, Short status) {
        DataCommon dataCommon = this.removeOrGetHandler(id, archivesLibraryId);
        Map<String, Object> map = dataTemporaryLibraryMapper.selectByPrimaryKey(dataCommon);
        if (map != null) {
            List<DataCommonKV> dataCommonKVS = new ArrayList<>();

            DataCommonKV dataCommonKV = new DataCommonKV();
            dataCommonKV.setFieldName(EnumArchivesLibraryDefaultField.STATUS.getDataKey());
            dataCommonKV.setFieldValue(status);
            DataCommonKV dataCommon1 = new DataCommonKV();
            dataCommon1.setFieldName(EnumArchivesLibraryDefaultField.STATUS_FIELD1.getDataKey());
            dataCommon1.setFieldValue(EnumArchivesOrder.TEMPORARY_LIBRARY.getValue());
            DataCommonKV dataCommon2 = new DataCommonKV();
            dataCommon2.setFieldName(EnumArchivesLibraryDefaultField.OPERATION_TIME_FIELD1.getDataKey());
            dataCommon2.setFieldValue(DateUtil.date());

            dataCommonKVS.add(dataCommonKV);
            dataCommonKVS.add(dataCommon1);
            dataCommonKVS.add(dataCommon2);

            dataCommon.setDataCommonKVs(dataCommonKVS);
            dataTemporaryLibraryMapper.updateByPrimaryKeySelective(dataCommon);
        }
    }

    @Override
    public Map<String, Object> getById(Integer id, Integer archivesLibraryId) {
        DataCommon dataCommon = this.removeOrGetHandler(id, archivesLibraryId);
        return dataTemporaryLibraryMapper.selectByPrimaryKey(dataCommon);
    }


    @Override
    public DataCommonVo getPage(DataCommonQueryDto dataCommonPageDto) {
        DataCommon dataCommon = this.page(dataCommonPageDto);
        dataCommon.getDataCommonKVs().add(this.getStatusField(EnumArchivesOrder.TEMPORARY_LIBRARY.getValue()));
        Integer archivesLibraryId = dataCommonPageDto.getArchivesLibraryId();
        Integer num = dataCommonPageDto.getNum();
        Integer size = dataCommonPageDto.getSize();

        DataCommonVo dataCommonVo = new DataCommonVo();
        List<DataCommonFieldVo> dataCommonFieldVos = this.getDataCommonFieldVos(archivesLibraryId);
        Page<Map<String, Object>> page = this.dataTemporaryLibraryMapper.selectPage(new Page<>(num, size), dataCommon);
        //特殊字段需要处理
        page.getRecords().forEach(map -> {
            String dataKey = EnumArchivesLibraryDefaultField.DEPARTMENT_ID.getDataKey();
            if (map.containsKey(dataKey)) {
                Integer departmentId = (Integer) map.get(dataKey);
                SysDepartment sysDepartment = iSysDepartmentService.getById(departmentId);
                map.put(dataKey, sysDepartment.getName());
            }
        });
        dataCommonVo.setFields(dataCommonFieldVos);
        dataCommonVo.setPage(page);
        return dataCommonVo;
    }

    @Override
    public void importExcelRecord(MultipartFile multipartFile, Integer archivesLibraryId) throws IOException {
        DataCommon dataCommon = this.importExcelFile(multipartFile, archivesLibraryId);
        dataCommon.getLists().forEach(dataCommonKvs -> dataCommonKvs.add(this.getStatusField(EnumArchivesOrder.TEMPORARY_LIBRARY.getValue())));
        dataTemporaryLibraryMapper.insertSelectiveBatch(dataCommon);
    }

    @Override
    public void exportExcelTemplateFile(HttpServletResponse response, String fileName, Integer archivesLibraryId) throws IOException {
        this.exportExcelFile(response, fileName, null, archivesLibraryId);
    }

    @Override
    public void exportExcelRecordFile(HttpServletResponse response, String fileName, Set<Integer> ids, Integer archivesLibraryId) throws IOException {
        this.exportExcelFile(response, fileName, ids, archivesLibraryId);
    }


    /**
     * 普通搜索
     * @param dataCommonPageDto
     * @return
     */
    @Override
    public DataCommonVo getPageEasy(DataCommonQueryDto dataCommonPageDto) {
        DataCommon dataCommon = this.page(dataCommonPageDto);
        DataCommonKV statusField = this.getStatusField(EnumArchivesOrder.TEMPORARY_LIBRARY.getValue());
        Integer archivesLibraryId = dataCommonPageDto.getArchivesLibraryId();
        Integer num = dataCommonPageDto.getNum();
        Integer size = dataCommonPageDto.getSize();
        DataCommonVo dataCommonVo = new DataCommonVo();
        List<DataCommonFieldVo> dataCommonFieldVos = this.getDataCommonFieldVos(archivesLibraryId);
        if(dataCommonFieldVos.size()>0){
            for (DataCommonFieldVo dataCommonFieldVo : dataCommonFieldVos) {
                if(dataCommonFieldVo.getQuery()){
                    DataCommonKV dataCommonKV = new DataCommonKV();
                    dataCommonKV.setFieldName(dataCommonFieldVo.getProp());
                    String keyword = dataCommonPageDto.getParams().get("keyword");
                    if(keyword == null){
                        keyword = "";
                    }
                    dataCommonKV.setFieldValue(keyword.trim());
                    dataCommon.getDataCommonKVs().add(dataCommonKV);
                }
            }
        }
        Page<Map<String, Object>> page = this.dataTemporaryLibraryMapper.selectPageEasy(new Page<>(num, size),statusField, dataCommon);
        //特殊字段需要处理
        page.getRecords().forEach(map -> {
            String dataKey = EnumArchivesLibraryDefaultField.DEPARTMENT_ID.getDataKey();
            if (map.containsKey(dataKey)) {
                Integer departmentId = (Integer) map.get(dataKey);
                SysDepartment sysDepartment = iSysDepartmentService.getById(departmentId);
                map.put(dataKey, sysDepartment.getName());
            }
        });
        dataCommonVo.setFields(dataCommonFieldVos);
        dataCommonVo.setPage(page);
        return dataCommonVo;
    }


    /**
     * 高级搜索
     * @param dataCommonPageDto
     * @return
     */
    @Override
    public DataCommonVo getPageComplex(DataCommonQueryDto dataCommonPageDto) {
        DataCommon dataCommon = this.pageComplex(dataCommonPageDto);
        dataCommon.getDataCommonKVs().add(this.getStatusField(EnumArchivesOrder.TEMPORARY_LIBRARY.getValue()));
        Integer archivesLibraryId = dataCommonPageDto.getArchivesLibraryId();
        Integer num = dataCommonPageDto.getNum();
        Integer size = dataCommonPageDto.getSize();

        DataCommonVo dataCommonVo = new DataCommonVo();
        List<DataCommonFieldVo> dataCommonFieldVos = this.getDataCommonFieldVos(archivesLibraryId);
        Page<Map<String, Object>> page = this.dataTemporaryLibraryMapper.selectPageComplex(new Page<>(num, size), dataCommon);
        //特殊字段需要处理
        page.getRecords().forEach(map -> {
            String dataKey = EnumArchivesLibraryDefaultField.DEPARTMENT_ID.getDataKey();
            if (map.containsKey(dataKey)) {
                Integer departmentId = (Integer) map.get(dataKey);
                SysDepartment sysDepartment = iSysDepartmentService.getById(departmentId);
                map.put(dataKey, sysDepartment.getName());
            }
        });
        dataCommonVo.setFields(dataCommonFieldVos);
        dataCommonVo.setPage(page);
        return dataCommonVo;
    }

    /**
     * 标记/取消病档
     * @param dataCommonMarkDto 信息
     */
    @Override
    public void mark(DataCommonMarkDto dataCommonMarkDto) {
        DataCommon dataCommon = this.removeOrGetHandler(dataCommonMarkDto.getId(), dataCommonMarkDto.getArchivesLibraryId());
        Map<String, Object> map = dataTemporaryLibraryMapper.selectByPrimaryKey(dataCommon);
        if (map != null) {
            List<DataCommonKV> dataCommonKVS = new ArrayList<>();
            int o = (int) map.get(EnumArchivesLibraryDefaultField.STATUS.getDataKey());
            if(o!=EnumArchivesOrder.TEMPORARY_LIBRARY.getValue()){
                return;
            }
            //病档状态
            DataCommonKV dataCommonKV = new DataCommonKV();
            dataCommonKV.setFieldName(EnumArchivesLibraryDefaultField.DISEASE.getDataKey());
            dataCommonKV.setFieldValue(dataCommonMarkDto.getType());
            if(dataCommonMarkDto.getType() == 1){
                //病档备注
                DataCommonKV dataCommon1 = new DataCommonKV();
                dataCommon1.setFieldName(EnumArchivesLibraryDefaultField.DISEASE_REMARK.getDataKey());
                dataCommon1.setFieldValue(dataCommonMarkDto.getDiseaseRemark() == null ? "" : dataCommonMarkDto.getDiseaseRemark());
                dataCommonKVS.add(dataCommon1);
            }else{
                //病档备注
                DataCommonKV dataCommon1 = new DataCommonKV();
                dataCommon1.setFieldName(EnumArchivesLibraryDefaultField.DISEASE_REMARK.getDataKey());
                dataCommon1.setFieldValue("");
                dataCommonKVS.add(dataCommon1);
            }
            dataCommonKVS.add(dataCommonKV);
            dataCommon.setDataCommonKVs(dataCommonKVS);
            dataTemporaryLibraryMapper.updateByPrimaryKeySelective(dataCommon);
        }
    }
}

package com.yintu.rixing.data.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yintu.rixing.data.DataCommon;
import com.yintu.rixing.data.DataCommonKV;
import com.yintu.rixing.data.DataDiseaseArchivesManagementMapper;
import com.yintu.rixing.data.IDataDiseaseArchivesManagementService;
import com.yintu.rixing.dto.data.DataCommonMarkDto;
import com.yintu.rixing.dto.data.DataCommonQueryDto;
import com.yintu.rixing.enumobject.EnumArchivesLibraryDefaultField;
import com.yintu.rixing.enumobject.EnumArchivesOrder;
import com.yintu.rixing.system.SysDepartment;
import com.yintu.rixing.vo.data.DataCommonFieldVo;
import com.yintu.rixing.vo.data.DataCommonVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author: mlf
 * @Date: 2021/1/25 17:33:02
 * @Version: 1.0
 */
@Service
public class DataDiseaseArchivesManagementServiceImpl extends DataCommonService implements IDataDiseaseArchivesManagementService {
    @Autowired
    private DataDiseaseArchivesManagementMapper dataDiseaseArchivesManagementMapper;

    @Override
    public void removeByIds(Set<Integer> ids, Integer archivesLibraryId) {
//        DataCommon dataCommon = this.removeOrGetHandler(null, archivesLibraryId);
//        dataDiseaseArchivesManagementMapper.deleteByPrimaryKeys(ids, dataCommon.getTableName());
        for (Integer id : ids) {
            this.updateStatusById(id, archivesLibraryId, EnumArchivesOrder.RECYCLE_BIN.getValue());
        }
    }

    @Override
    public void updateStatusById(Integer id, Integer archivesLibraryId, Short status) {
        DataCommon dataCommon = this.removeOrGetHandler(id, archivesLibraryId);
        Map<String, Object> map = dataDiseaseArchivesManagementMapper.selectByPrimaryKey(dataCommon);
        if (map != null) {
            List<DataCommonKV> dataCommonKVS = new ArrayList<>();
            DataCommonKV dataCommonKV = new DataCommonKV();
            dataCommonKV.setFieldName(EnumArchivesLibraryDefaultField.STATUS.getDataKey());
            if (status == null) {//判断是取消病档 还是病档删除
                Integer value = (Integer) map.get(EnumArchivesLibraryDefaultField.STATUS_FIELD1.getDataKey());
                status = value.shortValue();
            } else {
                DataCommonKV dataCommon1 = new DataCommonKV();
                dataCommon1.setFieldName(EnumArchivesLibraryDefaultField.STATUS_FIELD2.getDataKey());
                dataCommon1.setFieldValue(EnumArchivesOrder.DISEASE_ARCHIVES.getValue());
                DataCommonKV dataCommon2 = new DataCommonKV();
                dataCommon2.setFieldName(EnumArchivesLibraryDefaultField.OPERATION_TIME_FIELD2.getDataKey());
                dataCommon2.setFieldValue(DateUtil.date());
                dataCommonKVS.add(dataCommon1);
                dataCommonKVS.add(dataCommon2);
            }
            dataCommonKV.setFieldValue(status);
            dataCommonKVS.add(dataCommonKV);
            dataCommon.setDataCommonKVs(dataCommonKVS);
            dataDiseaseArchivesManagementMapper.updateByPrimaryKeySelective(dataCommon);
        }
    }

    @Override
    public Map<String, Object> getById(Integer id, Integer archivesLibraryId) {
        DataCommon dataCommon = this.removeOrGetHandler(id, archivesLibraryId);
        return dataDiseaseArchivesManagementMapper.selectByPrimaryKey(dataCommon);
    }

    @Override
    public DataCommonVo getPage(DataCommonQueryDto dataCommonPageDto) {
        DataCommon dataCommon = this.page(dataCommonPageDto);
        dataCommon.getDataCommonKVs().add(this.getStatusField(EnumArchivesOrder.DISEASE_ARCHIVES.getValue()));
        Integer archivesLibraryId = dataCommonPageDto.getArchivesLibraryId();
        Integer num = dataCommonPageDto.getNum();
        Integer size = dataCommonPageDto.getSize();

        DataCommonVo dataCommonVo = new DataCommonVo();
        List<DataCommonFieldVo> dataCommonFieldVos = this.getDataCommonFieldVos(archivesLibraryId);
        Page<Map<String, Object>> page = this.dataDiseaseArchivesManagementMapper.selectPage(new Page<>(num, size), dataCommon);
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
     * 普通搜索
     *
     * @param dataCommonPageDto
     * @return
     */
    @Override
    public DataCommonVo getPageEasy(DataCommonQueryDto dataCommonPageDto) {
        DataCommon dataCommon = this.page(dataCommonPageDto);
        //查询正式库并且是病档的数据
        DataCommonKV statusField = this.getStatusField(EnumArchivesOrder.FORMAL_LIBRARY.getValue());
        Integer archivesLibraryId = dataCommonPageDto.getArchivesLibraryId();
        Integer num = dataCommonPageDto.getNum();
        Integer size = dataCommonPageDto.getSize();
        DataCommonVo dataCommonVo = new DataCommonVo();
        List<DataCommonFieldVo> dataCommonFieldVos = this.getDataCommonFieldVos(archivesLibraryId);
        if (dataCommonFieldVos.size() > 0) {
            for (DataCommonFieldVo dataCommonFieldVo : dataCommonFieldVos) {
                if (dataCommonFieldVo.getQuery()) {
                    DataCommonKV dataCommonKV = new DataCommonKV();
                    dataCommonKV.setFieldName(dataCommonFieldVo.getProp());
                    String keyword = dataCommonPageDto.getParams().get("keyword");
                    if (keyword == null) {
                        keyword = "";
                    }
                    dataCommonKV.setFieldValue(keyword.trim());
                    dataCommon.getDataCommonKVs().add(dataCommonKV);
                }
            }
        }
        Page<Map<String, Object>> page = this.dataDiseaseArchivesManagementMapper.selectPageEasy(new Page<>(num, size), statusField, dataCommon);
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
     *
     * @param dataCommonPageDto
     * @return
     */
    @Override
    public DataCommonVo getPageComplex(DataCommonQueryDto dataCommonPageDto) {
        DataCommon dataCommon = this.pageComplex(dataCommonPageDto);
        dataCommon.getDataCommonKVs().add(this.getStatusField(EnumArchivesOrder.FORMAL_LIBRARY.getValue()));
        Integer archivesLibraryId = dataCommonPageDto.getArchivesLibraryId();
        Integer num = dataCommonPageDto.getNum();
        Integer size = dataCommonPageDto.getSize();

        DataCommonVo dataCommonVo = new DataCommonVo();
        List<DataCommonFieldVo> dataCommonFieldVos = this.getDataCommonFieldVos(archivesLibraryId);
        Page<Map<String, Object>> page = this.dataDiseaseArchivesManagementMapper.selectPageComplex(new Page<>(num, size), dataCommon);
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
     * 取消病档
     *
     * @param id                数据id
     * @param archivesLibraryId 档案库id
     */
    @Override
    public void mark(Integer id, Integer archivesLibraryId) {
        DataCommon dataCommon = this.removeOrGetHandler(id, archivesLibraryId);
        Map<String, Object> map = dataDiseaseArchivesManagementMapper.selectByPrimaryKey(dataCommon);
        if (map != null) {
            List<DataCommonKV> dataCommonKVS = new ArrayList<>();
            int o = (int) map.get(EnumArchivesLibraryDefaultField.STATUS.getDataKey());
            if (o != EnumArchivesOrder.FORMAL_LIBRARY.getValue()) {
                return;
            }
            //病档状态
            DataCommonKV dataCommonKV = new DataCommonKV();
            dataCommonKV.setFieldName(EnumArchivesLibraryDefaultField.DISEASE.getDataKey());
            dataCommonKV.setFieldValue(0);
            //病档备注
            DataCommonKV dataCommon1 = new DataCommonKV();
            dataCommon1.setFieldName(EnumArchivesLibraryDefaultField.DISEASE_REMARK.getDataKey());
            dataCommon1.setFieldValue("");
            dataCommonKVS.add(dataCommon1);
            dataCommonKVS.add(dataCommonKV);
            dataCommon.setDataCommonKVs(dataCommonKVS);
            dataDiseaseArchivesManagementMapper.updateByPrimaryKeySelective(dataCommon);
        }
    }
}

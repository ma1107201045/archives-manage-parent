package com.yintu.rixing.data.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yintu.rixing.data.DataCommon;
import com.yintu.rixing.data.DataCommonKV;
import com.yintu.rixing.data.DataDiseaseArchivesManagementMapper;
import com.yintu.rixing.data.IDataDiseaseArchivesManagementService;
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
}

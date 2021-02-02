package com.yintu.rixing.data.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yintu.rixing.data.DataCommon;
import com.yintu.rixing.data.DataCommonKV;
import com.yintu.rixing.data.DataFormalLibraryMapper;
import com.yintu.rixing.data.IDataFormalLibraryService;
import com.yintu.rixing.dto.data.DataCommonFormDto;
import com.yintu.rixing.dto.data.DataCommonQueryDto;
import com.yintu.rixing.enumobject.EnumArchivesLibraryDefaultField;
import com.yintu.rixing.enumobject.EnumArchivesOrder;
import com.yintu.rixing.system.SysDepartment;
import com.yintu.rixing.vo.data.DataCommonFieldVo;
import com.yintu.rixing.vo.data.DataCommonVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author: mlf
 * @Date: 2021/1/25 11:50:24
 * @Version: 1.0
 */
@Service
public class DataFormalLibraryServiceImpl extends DataCommonService implements IDataFormalLibraryService {
    @Autowired
    private DataFormalLibraryMapper dataFormalLibraryMapper;

    @Override
    public void save(DataCommonFormDto dataCommonFormDto) {
        DataCommon dataCommon = this.saveOrUpdateHandler(dataCommonFormDto);
        dataCommon.getDataCommonKVs().add(this.getStatusField(EnumArchivesOrder.FORMAL_LIBRARY.getValue()));
        dataFormalLibraryMapper.insertSelective(dataCommon);
    }

    @Override
    public void removeByIds(Set<Integer> ids, Integer archivesLibraryId) {
        DataCommon dataCommon = this.removeOrGetHandler(null, archivesLibraryId);
        dataFormalLibraryMapper.deleteByPrimaryKeys(ids, dataCommon.getTableName());
    }

    @Override
    public void updateById(DataCommonFormDto dataCommonDto) {
        DataCommon dataCommon = this.saveOrUpdateHandler(dataCommonDto);
        Map<String, Object> map = dataFormalLibraryMapper.selectByPrimaryKey(dataCommon);
        if (map != null) {
            dataFormalLibraryMapper.updateByPrimaryKeySelective(dataCommon);
        }
    }

    @Override
    public void updateById(DataCommon dataCommon) {
        dataFormalLibraryMapper.updateByPrimaryKeySelective(dataCommon);
    }

    @Override
    public void updateStatusById(Integer id, Integer archivesLibraryId, Short status) {
        DataCommon dataCommon = this.removeOrGetHandler(id, archivesLibraryId);
        Map<String, Object> map = dataFormalLibraryMapper.selectByPrimaryKey(dataCommon);
        if (map != null) {
            List<DataCommonKV> dataCommonKVS = new ArrayList<>();
            DataCommonKV dataCommonKV = new DataCommonKV();
            dataCommonKV.setFieldName(EnumArchivesLibraryDefaultField.STATUS.getDataKey());
            dataCommonKV.setFieldValue(status);
            dataCommonKVS.add(dataCommonKV);
            dataCommon.setDataCommonKVs(dataCommonKVS);
            dataFormalLibraryMapper.updateByPrimaryKeySelective(dataCommon);
        }
    }

    @Override
    public Map<String, Object> getById(Integer id, Integer archivesLibraryId) {
        DataCommon dataCommon = this.removeOrGetHandler(id, archivesLibraryId);
        return dataFormalLibraryMapper.selectByPrimaryKey(dataCommon);
    }


    @Override
    public List<Map<String, Object>> getList(DataCommon dataCommon) {
        return dataFormalLibraryMapper.selectList(dataCommon);
    }

    @Override
    public DataCommonVo getPage(DataCommonQueryDto dataCommonPageDto) {
        DataCommon dataCommon = this.page(dataCommonPageDto);
        dataCommon.getDataCommonKVs().add(this.getStatusField(EnumArchivesOrder.FORMAL_LIBRARY.getValue()));
        Integer archivesLibraryId = dataCommonPageDto.getArchivesLibraryId();
        Integer num = dataCommonPageDto.getNum();
        Integer size = dataCommonPageDto.getSize();

        DataCommonVo dataCommonVo = new DataCommonVo();
        List<DataCommonFieldVo> dataCommonFieldVos = this.getDataCommonFieldVos(archivesLibraryId);
        Page<Map<String, Object>> page = this.dataFormalLibraryMapper.selectPage(new Page<>(num, size), dataCommon);
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
    public void exportExcelRecordFile(HttpServletResponse response, String fileName, Set<Integer> ids, Integer archivesLibraryId) throws IOException {
        this.exportExcelFile(response, fileName, ids, archivesLibraryId);
    }
}

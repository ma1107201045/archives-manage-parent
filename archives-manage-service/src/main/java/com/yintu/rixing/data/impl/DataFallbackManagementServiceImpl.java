package com.yintu.rixing.data.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yintu.rixing.data.DataCommon;
import com.yintu.rixing.data.DataCommonKV;
import com.yintu.rixing.data.DataFallbackManagementMapper;
import com.yintu.rixing.data.IDataFallbackManagementService;
import com.yintu.rixing.dto.data.DataCommonFormDto;
import com.yintu.rixing.dto.data.DataCommonQueryDto;
import com.yintu.rixing.enumobject.EnumArchivesOrder;
import com.yintu.rixing.system.SysArchivesLibrary;
import com.yintu.rixing.util.AssertUtil;
import com.yintu.rixing.util.TableNameUtil;
import com.yintu.rixing.vo.data.DataCommonVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author: mlf
 * @Date: 2021/1/26 17:29:48
 * @Version: 1.0
 */
@Service
public class DataFallbackManagementServiceImpl extends DataCommonService implements IDataFallbackManagementService {
    @Autowired
    private DataFallbackManagementMapper dataFallbackManagementMapper;

    @Override
    public void save(String tableName, Map<String, Object> params) {
        DataCommon dataCommon = new DataCommon();

        String rollbackTableName = tableName + TableNameUtil.ROLLBACK_SUFFIX;
        Integer dataId = (Integer) params.get("id");
        params.remove("id");
        params.put("data_id", dataId);

        List<DataCommonKV> dataCommonKVS = new ArrayList<>();
        for (String key : params.keySet()) {
            DataCommonKV dataCommonKV = new DataCommonKV();
            dataCommonKV.setFieldName(key);
            dataCommonKV.setFieldValue(params.get(key));
            dataCommonKVS.add(dataCommonKV);
        }
        dataCommon.setTableName(rollbackTableName);
        dataCommon.setDataCommonKVs(dataCommonKVS);
        dataFallbackManagementMapper.insertSelectiveBatch(dataCommon);
    }

    @Override
    public void removeByIds(Set<Integer> ids, Integer archivesLibraryId) {
        AssertUtil.notNull(archivesLibraryId, "档案库id不能为空");
        SysArchivesLibrary sysArchivesLibrary = this.iSysArchivesLibraryService.getById(archivesLibraryId);
        AssertUtil.notNull(sysArchivesLibrary, "档案库不能为空");
        String tableName = TableNameUtil.getRollbackTableName(sysArchivesLibrary.getDataKey());
        dataFallbackManagementMapper.deleteByPrimaryKeys(ids, tableName);
    }


    @Override
    public DataCommonVo getPage(DataCommonQueryDto dataCommonPageDto) {
        DataCommon dataCommon = this.page(dataCommonPageDto);
        dataCommon.getDataCommonKVs().add(this.getStatusField(EnumArchivesOrder.FORMAL_LIBRARY.getValue()));
        Integer archivesLibraryId = dataCommonPageDto.getArchivesLibraryId();
        Integer num = dataCommonPageDto.getNum();
        Integer size = dataCommonPageDto.getSize();

        DataCommonVo dataCommonVo = new DataCommonVo();
        dataCommonVo.setFields(this.getDataCommonVos(archivesLibraryId));
        dataCommonVo.setPage(dataFallbackManagementMapper.selectPage(new Page<>(num, size), dataCommon));
        return dataCommonVo;
    }
}

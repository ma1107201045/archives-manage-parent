package com.yintu.rixing.data.impl;

import com.yintu.rixing.data.DataCommon;
import com.yintu.rixing.data.DataCommonKV;
import com.yintu.rixing.data.DataRecycleBinMapper;
import com.yintu.rixing.data.IDataRecycleBinService;
import com.yintu.rixing.enumobject.EnumArchivesLibraryDefaultField;
import com.yintu.rixing.enumobject.EnumArchivesOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author: mlf
 * @Date: 2021/1/25 16:07:07
 * @Version: 1.0
 */
@Service
public class DataRecycleBinServiceImpl extends DataCommonService implements IDataRecycleBinService {

    @Autowired
    private DataRecycleBinMapper dataRecycleBinMapper;

    @Override
    public void regainByIds(Set<Integer> ids, Integer archivesLibraryId) {
        for (Integer id : ids) {
            this.updateStatusById(id, archivesLibraryId);
        }
    }

    @Override
    public void removeByIds(Set<Integer> ids, Integer archivesLibraryId) {
        DataCommon dataCommon = this.removeOrGetHandler(null, archivesLibraryId);
        dataRecycleBinMapper.deleteByPrimaryKeys(ids, dataCommon.getTableName());
    }

    @Override
    public void updateStatusById(Integer id, Integer archivesLibraryId) {
        DataCommon dataCommon = this.removeOrGetHandler(id, archivesLibraryId);
        Map<String, Object> map = dataRecycleBinMapper.selectByPrimaryKey(dataCommon);
        if (map != null) {
            String dataKey = EnumArchivesLibraryDefaultField.STATUS.getDataKey();
            Integer status = (Integer) map.get(dataKey);
            List<DataCommonKV> dataCommonKVS = new ArrayList<>();
            DataCommonKV dataCommonKV = new DataCommonKV();
            dataCommonKV.setFieldName(dataKey);
            dataCommonKV.setFieldValue(status.toString().substring(1));
            dataCommonKVS.add(dataCommonKV);
            dataCommon.setDataCommonKVs(dataCommonKVS);
            dataRecycleBinMapper.updateByPrimaryKeySelective(dataCommon);
        }
    }
}

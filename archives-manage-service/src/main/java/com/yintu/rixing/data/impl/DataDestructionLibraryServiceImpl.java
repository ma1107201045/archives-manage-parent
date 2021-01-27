package com.yintu.rixing.data.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yintu.rixing.data.DataCommon;
import com.yintu.rixing.data.DataCommonKV;
import com.yintu.rixing.data.DataDestructionLibraryMapper;
import com.yintu.rixing.data.IDataDestructionLibraryService;
import com.yintu.rixing.dto.data.DataCommonFormDto;
import com.yintu.rixing.dto.data.DataCommonQueryDto;
import com.yintu.rixing.enumobject.EnumArchivesLibraryDefaultField;
import com.yintu.rixing.enumobject.EnumArchivesOrder;
import com.yintu.rixing.vo.data.DataCommonVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: mlf
 * @Date: 2021/1/25 17:18:09
 * @Version: 1.0
 */
@Service
public class DataDestructionLibraryServiceImpl extends DataCommonService implements IDataDestructionLibraryService {
    @Autowired
    private DataDestructionLibraryMapper destructionLibraryMapper;


    @Override
    public void updateById(DataCommonFormDto dataCommonFormDto) {
        DataCommon dataCommon = this.saveOrUpdateHandler(dataCommonFormDto);
        Map<String, Object> map = destructionLibraryMapper.selectByPrimaryKey(dataCommon);
        if (map != null) {
            destructionLibraryMapper.updateByPrimaryKeySelective(dataCommon);
        }
    }


    @Override
    public void updateStatusById(Integer id, Integer archivesLibraryId, Short status) {
        DataCommon dataCommon = this.removeOrGetHandler(id, archivesLibraryId);
        Map<String, Object> map = destructionLibraryMapper.selectByPrimaryKey(dataCommon);
        if (map != null) {
            List<DataCommonKV> dataCommonKVS = new ArrayList<>();
            DataCommonKV dataCommonKV = new DataCommonKV();
            dataCommonKV.setFieldName(EnumArchivesLibraryDefaultField.STATUS.getDataKey());
            dataCommonKV.setFieldValue(status);
            dataCommonKVS.add(dataCommonKV);
            dataCommon.setDataCommonKVs(dataCommonKVS);
            destructionLibraryMapper.updateByPrimaryKeySelective(dataCommon);
        }
    }

    @Override
    public Map<String, Object> getById(Integer id, Integer archivesLibraryId) {
        DataCommon dataCommon = this.removeOrGetHandler(id, archivesLibraryId);
        return destructionLibraryMapper.selectByPrimaryKey(dataCommon);
    }

    @Override
    public DataCommonVo getPage(DataCommonQueryDto dataCommonPageDto) {
        DataCommon dataCommon = this.page(dataCommonPageDto);
        dataCommon.getDataCommonKVs().add(this.getStatusField(EnumArchivesOrder.DESTRUCTION_LIBRARY.getValue()));
        Integer archivesLibraryId = dataCommonPageDto.getArchivesLibraryId();
        Integer num = dataCommonPageDto.getNum();
        Integer size = dataCommonPageDto.getSize();

        DataCommonVo dataCommVo = new DataCommonVo();
        dataCommVo.setFields(this.getDataCommonFieldVos(archivesLibraryId));
        dataCommVo.setPage(destructionLibraryMapper.selectPage(new Page<>(num, size), dataCommon));
        return dataCommVo;
    }
}

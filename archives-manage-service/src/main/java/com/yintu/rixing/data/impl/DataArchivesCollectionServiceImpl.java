package com.yintu.rixing.data.impl;

import com.yintu.rixing.data.DataArchivesCollectionMapper;
import com.yintu.rixing.data.DataCommonAll;
import com.yintu.rixing.data.IDataArchivesCollectionService;
import com.yintu.rixing.dto.data.DataCommonDto;
import com.yintu.rixing.exception.BaseRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

/**
 * @Author: mlf
 * @Date: 2021/1/18 11:09:59
 * @Version: 1.0
 */
@Service
public class DataArchivesCollectionServiceImpl extends DataCommonService implements IDataArchivesCollectionService {
    @Autowired
    private DataArchivesCollectionMapper dataArchivesCollectionMapper;

    @Override
    public void save(DataCommonDto dataCommonDto) {
        DataCommonAll dataCommonAll = this.parametersToProofread(dataCommonDto);
        dataArchivesCollectionMapper.insertSelective(dataCommonAll);
    }

    @Override
    public void removeByIds(Set<Integer> ids, Integer archivesId) {

    }

    @Override
    public void updateById(DataCommonDto dataCommonDto) {
        DataCommonAll dataCommonAll = this.parametersToProofread(dataCommonDto);
        Map<String, Object> map = dataArchivesCollectionMapper.selectByPrimaryKey(dataCommonAll);
        dataArchivesCollectionMapper.updateByPrimaryKeySelective(dataCommonAll);
    }

    @Override
    public void getById(Integer archivesId, Integer id) {

    }


}

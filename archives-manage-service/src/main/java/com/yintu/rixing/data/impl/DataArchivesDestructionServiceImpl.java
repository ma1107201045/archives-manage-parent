package com.yintu.rixing.data.impl;

import com.yintu.rixing.data.IDataArchivesDestructionService;
import com.yintu.rixing.dto.data.DataCommonQueryDto;
import com.yintu.rixing.vo.data.DataCommonVo;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

/**
 * @Author: mlf
 * @Date: 2021/1/26 11:47:30
 * @Version: 1.0
 */
@Service
public class DataArchivesDestructionServiceImpl implements IDataArchivesDestructionService {


    @Override
    public void removeByIds(Set<Integer> ids, Integer archivesLibraryId) {

    }

    @Override
    public Map<String, Object> getById(Integer id, Integer archivesLibraryId) {
        return null;
    }

    @Override
    public DataCommonVo getPage(DataCommonQueryDto dataCommonPageDto) {
        return null;
    }
}

package com.yintu.rixing.data;

import com.yintu.rixing.dto.data.DataCommonDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * @Author: mlf
 * @Date: 2021/1/18 11:08:44
 * @Version: 1.0
 */
public interface IDataArchivesCollectionService {

    @Transactional(rollbackFor = {Exception.class})
    void save(DataCommonDto dataCommonDto);

    @Transactional(rollbackFor = {Exception.class})
    void removeByIds(Integer archivesId, Set<Integer> ids);

    @Transactional(rollbackFor = {Exception.class})
    void updateById(DataCommonDto dataCommonDto);

    @Transactional(rollbackFor = {Exception.class})
    void getById(Integer archivesId, Integer id);
}

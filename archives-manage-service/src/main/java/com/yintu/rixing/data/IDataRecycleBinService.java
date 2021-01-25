package com.yintu.rixing.data;

import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * @Author: mlf
 * @Date: 2021/1/25 16:06:47
 * @Version: 1.0
 */
public interface IDataRecycleBinService {

    @Transactional(rollbackFor = {Exception.class})
    void regainByIds(Set<Integer> ids, Integer archivesLibraryId);

    @Transactional(rollbackFor = {Exception.class})
    void removeByIds(Set<Integer> ids, Integer archivesLibraryId);

    void updateStatusById(Integer id, Integer archivesLibraryId);
}

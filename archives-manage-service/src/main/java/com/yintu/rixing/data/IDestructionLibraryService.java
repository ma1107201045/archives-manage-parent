package com.yintu.rixing.data;

import com.yintu.rixing.dto.data.DataCommonQueryDto;
import com.yintu.rixing.vo.data.DataCommonVo;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Set;

/**
 * @Author: mlf
 * @Date: 2021/1/25 17:15:27
 * @Version: 1.0
 */
public interface IDestructionLibraryService {


    @Transactional(rollbackFor = {Exception.class})
    void removeByIds(Set<Integer> ids, Integer archivesLibraryId);

    Map<String, Object> getById(Integer id, Integer archivesLibraryId);

    DataCommonVo getPage(DataCommonQueryDto dataCommonPageDto);
}

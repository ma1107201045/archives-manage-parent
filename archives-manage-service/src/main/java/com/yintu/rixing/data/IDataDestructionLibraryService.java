package com.yintu.rixing.data;

import com.yintu.rixing.dto.data.DataCommonFormDto;
import com.yintu.rixing.dto.data.DataCommonQueryDto;
import com.yintu.rixing.vo.data.DataCommonVo;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * @Author: mlf
 * @Date: 2021/1/25 17:15:27
 * @Version: 1.0
 */
public interface IDataDestructionLibraryService {


    @Transactional(rollbackFor = {Exception.class})
    void updateById(DataCommonFormDto dataCommonFormDto);

    @Transactional(rollbackFor = {Exception.class})
    void updateStatusById(Integer id, Integer archivesLibraryId, Short status);

    Map<String, Object> getById(Integer id, Integer archivesLibraryId);

    DataCommonVo getPage(DataCommonQueryDto dataCommonPageDto);

    //简单查询
    DataCommonVo getPageEasy(DataCommonQueryDto queryDto);
    //高级查询
    DataCommonVo getPageComplex(DataCommonQueryDto queryDto);
}

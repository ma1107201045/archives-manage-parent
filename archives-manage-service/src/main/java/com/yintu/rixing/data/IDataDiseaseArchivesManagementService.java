package com.yintu.rixing.data;

import com.yintu.rixing.dto.data.DataCommonMarkDto;
import com.yintu.rixing.dto.data.DataCommonQueryDto;
import com.yintu.rixing.vo.data.DataCommonVo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Set;

/**
 * @Author: mlf
 * @Date: 2021/1/25 17:32:08
 * @Version: 1.0
 */
public interface IDataDiseaseArchivesManagementService {


    @Transactional(rollbackFor = {Exception.class})
    void removeByIds(Set<Integer> ids, Integer archivesLibraryId);

    @Transactional(rollbackFor = {Exception.class})
    void updateStatusById(Integer id, Integer archivesLibraryId, Short status);

    Map<String, Object> getById(Integer id, Integer archivesLibraryId);

    DataCommonVo getPage(DataCommonQueryDto dataCommonPageDto);

    //简单查询
    DataCommonVo getPageEasy(DataCommonQueryDto queryDto);
    //高级查询
    DataCommonVo getPageComplex(DataCommonQueryDto queryDto);
    //标记/取消病档
    void mark(Integer id, Integer archivesLibraryId);
}

package com.yintu.rixing.data;

import com.yintu.rixing.dto.data.DataCommonFormDto;
import com.yintu.rixing.dto.data.DataCommonMarkDto;
import com.yintu.rixing.dto.data.DataCommonQueryDto;
import com.yintu.rixing.dto.data.DataCommonRollBackDto;
import com.yintu.rixing.vo.data.DataCommonVo;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author: mlf
 * @Date: 2021/1/25 11:49:59
 * @Version: 1.0
 */
public interface IDataFormalLibraryService {


    @Transactional(rollbackFor = {Exception.class})
    void save(DataCommonFormDto dataCommonFormDto);

    @Transactional(rollbackFor = {Exception.class})
    void removeByIds(Set<Integer> ids, Integer archivesLibraryId);

    @Transactional(rollbackFor = {Exception.class})
    void updateById(DataCommonFormDto dataCommonDto);

    @Transactional(rollbackFor = {Exception.class})
    void updateById(DataCommon dataCommon);

    @Transactional(rollbackFor = {Exception.class})
    void updateStatusById(Integer id, Integer archivesLibraryId, Short status);

    Map<String, Object> getById(Integer id, Integer archivesLibraryId);

    List<Map<String, Object>> getList(DataCommon dataCommon);

    DataCommonVo getPage(DataCommonQueryDto dataCommonPageDto);

    void exportExcelRecordFile(HttpServletResponse response, String fileName, Set<Integer> ids, Integer archivesLibraryId) throws IOException;

    //简单查询
    DataCommonVo getPageEasy(DataCommonQueryDto queryDto);
    //高级查询
    DataCommonVo getPageComplex(DataCommonQueryDto queryDto);
    //标记/取消病档
    void mark(DataCommonMarkDto dataCommonMarkDto);
    //回退
    void rollBack(DataCommonRollBackDto dataCommonRollBackDto);
}

package com.yintu.rixing.data;

import com.yintu.rixing.dto.data.DataCommonFormDto;
import com.yintu.rixing.dto.data.DataCommonMarkDto;
import com.yintu.rixing.dto.data.DataCommonQueryDto;
import com.yintu.rixing.vo.data.DataCommonVo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * @Author: mlf
 * @Date: 2021/1/21 16:06:12
 * @Version: 1.0
 */
public interface IDataTemporaryLibraryService {

    @Transactional(rollbackFor = {Exception.class})
    void save(DataCommonFormDto dataCommonFormDto);

    @Transactional(rollbackFor = {Exception.class})
    void removeByIds(Set<Integer> ids, Integer archivesLibraryId);

    @Transactional(rollbackFor = {Exception.class})
    void updateById(DataCommonFormDto dataCommonDto);

    @Transactional(rollbackFor = {Exception.class})
    void updateStatusById(Integer id, Integer archivesLibraryId, Short status);

    Map<String, Object> getById(Integer id, Integer archivesLibraryId);

    DataCommonVo getPage(DataCommonQueryDto dataCommonPageDto);

    @Transactional(rollbackFor = {Exception.class})
    void importExcelRecord(MultipartFile multipartFile, Integer archivesLibraryId) throws IOException;

    void exportExcelTemplateFile(HttpServletResponse response, String fileName, Integer archivesLibraryId) throws IOException;

    void exportExcelRecordFile(HttpServletResponse response, String fileName, Set<Integer> ids, Integer archivesLibraryId) throws IOException;

    //简单查询
    DataCommonVo getPageEasy(DataCommonQueryDto dataCommonPageDto);

    //高级查询
    DataCommonVo getPageComplex(DataCommonQueryDto dataCommonPageDto);

    //设为病档
    void mark(DataCommonMarkDto dataCommonMarkDto);
}

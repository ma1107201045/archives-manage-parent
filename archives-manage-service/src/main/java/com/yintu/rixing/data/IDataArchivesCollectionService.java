package com.yintu.rixing.data;

import com.yintu.rixing.dto.data.DataCommonFormDto;
import com.yintu.rixing.dto.data.DataCommonQueryDto;
import com.yintu.rixing.vo.data.DataCommonVo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * @Author: mlf
 * @Date: 2021/1/18 11:08:44
 * @Version: 1.0
 */
public interface IDataArchivesCollectionService {

    @Transactional(rollbackFor = {Exception.class})
    void save(DataCommonFormDto dataCommonDto);

    @Transactional(rollbackFor = {Exception.class})
    void removeByIds(Set<Integer> ids, Integer archivesLibraryId);

    @Transactional(rollbackFor = {Exception.class})
    void updateById(DataCommonFormDto dataCommonDto);

    Map<String, Object> getById(DataCommonFormDto dataCommonDto);

    DataCommonVo getPage(DataCommonQueryDto dataCommonPageDto);

    void importExcelRecord(MultipartFile multipartFile, Integer archivesLibraryId) throws IOException;

    void exportExcelTemplateFile(HttpServletResponse response, String fileName, Integer archivesLibraryId) throws IOException;

    void exportExcelRecordFile(HttpServletResponse response, String fileName, Set<Integer> ids, Integer archivesLibraryId) throws IOException;
}

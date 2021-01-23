package com.yintu.rixing.data.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yintu.rixing.data.DataCommon;
import com.yintu.rixing.data.DataTemporaryLibraryMapper;
import com.yintu.rixing.data.IDataTemporaryLibraryService;
import com.yintu.rixing.dto.data.DataCommonFormDto;
import com.yintu.rixing.dto.data.DataCommonQueryDto;
import com.yintu.rixing.vo.data.DataCommonVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * @Author: mlf
 * @Date: 2021/1/21 16:06:33
 * @Version: 1.0
 */
@Service
public class DataTemporaryLibraryServiceImpl extends DataCommonService implements IDataTemporaryLibraryService {
    @Autowired
    private DataTemporaryLibraryMapper dataTemporaryLibraryMapper;

    @Override
    public void save(DataCommonFormDto dataCommonFormDto) {
        DataCommon dataCommonAll = this.saveOrUpdateHandler(dataCommonFormDto);
        dataTemporaryLibraryMapper.insertSelective(dataCommonAll);
    }

    @Override
    public void removeByIds(Set<Integer> ids, Integer archivesLibraryId) {
        DataCommon dataCommonAll = this.removeOrGetHandler(archivesLibraryId);
        dataTemporaryLibraryMapper.deleteByPrimaryKeys(ids, dataCommonAll.getTableName());
    }

    @Override
    public void updateById(DataCommonFormDto dataCommonFormDto) {
        DataCommon dataCommonAll = this.saveOrUpdateHandler(dataCommonFormDto);
        Integer id = dataCommonAll.getId();
        String tableName = dataCommonAll.getTableName();
        Map<String, Object> map = dataTemporaryLibraryMapper.selectByPrimaryKey(id, tableName);
        if (map != null) {
            dataTemporaryLibraryMapper.updateByPrimaryKeySelective(dataCommonAll);
        }
    }

    @Override
    public Map<String, Object> getById(Integer id, Integer archivesLibraryId) {
        DataCommon dataCommonAll = this.removeOrGetHandler(archivesLibraryId);
        return dataTemporaryLibraryMapper.selectByPrimaryKey(id, dataCommonAll.getTableName());
    }


    @Override
    public DataCommonVo getPage(DataCommonQueryDto dataCommonPageDto) {
        DataCommon dataCommonAll = this.page(dataCommonPageDto);
        Integer archivesLibraryId = dataCommonPageDto.getArchivesLibraryId();
        Integer num = dataCommonPageDto.getNum();
        Integer size = dataCommonPageDto.getSize();

        DataCommonVo dataCommonVo = new DataCommonVo();
        dataCommonVo.setFields(this.getDataCommonVos(archivesLibraryId));
        dataCommonVo.setPage(dataTemporaryLibraryMapper.selectPage(new Page<>(num, size), dataCommonAll));
        return dataCommonVo;
    }

    @Override
    public void importExcelRecord(MultipartFile multipartFile, Integer archivesLibraryId) throws IOException {
        DataCommon dataCommonAll = this.importExcelFile(multipartFile, archivesLibraryId);
        dataTemporaryLibraryMapper.insertSelectiveBatch(dataCommonAll);
    }

    @Override
    public void exportExcelTemplateFile(HttpServletResponse response, String fileName, Integer archivesLibraryId) throws IOException {
        this.exportExcelFile(response, fileName, null, archivesLibraryId);
    }

    @Override
    public void exportExcelRecordFile(HttpServletResponse response, String fileName, Set<Integer> ids, Integer archivesLibraryId) throws IOException {
        this.exportExcelFile(response, fileName, ids, archivesLibraryId);
    }
}

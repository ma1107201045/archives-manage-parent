package com.yintu.rixing.data.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yintu.rixing.data.DataArchivesCollectionMapper;
import com.yintu.rixing.data.DataCommonAll;
import com.yintu.rixing.data.DataTemporaryLibraryMapper;
import com.yintu.rixing.data.IDataTemporaryLibraryService;
import com.yintu.rixing.dto.data.DataCommonFormDto;
import com.yintu.rixing.dto.data.DataCommonQueryDto;
import com.yintu.rixing.system.SysArchivesLibrary;
import com.yintu.rixing.util.AssertUtil;
import com.yintu.rixing.util.TableNameUtil;
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
        DataCommonAll dataCommonAll = this.parametersToProofread(dataCommonFormDto);
        dataTemporaryLibraryMapper.insertSelective(dataCommonAll);
    }

    @Override
    public void removeByIds(Set<Integer> ids, Integer archivesLibraryId) {
        AssertUtil.notNull(archivesLibraryId, "档案库id不能为空");
        SysArchivesLibrary sysArchivesLibrary = this.iSysArchivesLibraryService.getById(archivesLibraryId);
        AssertUtil.notNull(sysArchivesLibrary, "档案库不能为空");
        dataTemporaryLibraryMapper.deleteByPrimaryKeys(ids, TableNameUtil.getFullTableName(sysArchivesLibrary.getDataKey()));
    }

    @Override
    public void updateById(DataCommonFormDto dataCommonFormDto) {
        DataCommonAll dataCommonAll = this.parametersToProofread(dataCommonFormDto);
        Map<String, Object> map = dataTemporaryLibraryMapper.selectByPrimaryKey(dataCommonAll);
        if (map != null) {
            dataTemporaryLibraryMapper.updateByPrimaryKeySelective(dataCommonAll);
        }
    }

    @Override
    public Map<String, Object> getById(DataCommonFormDto dataCommonFormDto) {
        SysArchivesLibrary sysArchivesLibrary = this.iSysArchivesLibraryService.getById(dataCommonFormDto.getArchivesLibraryId());
        AssertUtil.notNull(sysArchivesLibrary, "档案库不能为空");
        DataCommonAll dataCommonAll = new DataCommonAll();
        dataCommonAll.setId(dataCommonFormDto.getId());
        dataCommonAll.setTableName(TableNameUtil.getFullTableName(sysArchivesLibrary.getDataKey()));
        return dataTemporaryLibraryMapper.selectByPrimaryKey(dataCommonAll);
    }

    @Override
    public DataCommonVo getPage(DataCommonQueryDto dataCommonPageDto) {
        Integer archivesLibraryId = dataCommonPageDto.getArchivesLibraryId();
        Integer num = dataCommonPageDto.getNum();
        Integer size = dataCommonPageDto.getSize();
        SysArchivesLibrary sysArchivesLibrary = this.iSysArchivesLibraryService.getById(archivesLibraryId);
        AssertUtil.notNull(sysArchivesLibrary, "档案库不能为空");
        DataCommonVo dataCommonVo = new DataCommonVo();
        dataCommonVo.setTitles(this.getDataCommonTitles(archivesLibraryId));
        dataCommonVo.setPage(dataTemporaryLibraryMapper.selectPage(new Page<>(num, size), TableNameUtil.getFullTableName(sysArchivesLibrary.getDataKey())));
        return dataCommonVo;
    }

    @Override
    public void importExcelRecord(MultipartFile multipartFile, Integer archivesLibraryId) throws IOException {
        DataCommonAll dataCommonAll = this.importExcelFile(multipartFile, archivesLibraryId);
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

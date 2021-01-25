package com.yintu.rixing.data.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yintu.rixing.data.DataCommon;
import com.yintu.rixing.data.DataCommonKV;
import com.yintu.rixing.data.DataTemporaryLibraryMapper;
import com.yintu.rixing.data.IDataTemporaryLibraryService;
import com.yintu.rixing.dto.data.DataCommonFormDto;
import com.yintu.rixing.dto.data.DataCommonQueryDto;
import com.yintu.rixing.enumobject.EnumArchivesLibraryDefaultField;
import com.yintu.rixing.enumobject.EnumArchivesOrder;
import com.yintu.rixing.vo.data.DataCommonVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
        DataCommon dataCommon = this.saveOrUpdateHandler(dataCommonFormDto);
        dataCommon.getDataCommonKVs().add(this.getStatusField(EnumArchivesOrder.TEMPORARY_LIBRARY.getValue()));
        dataTemporaryLibraryMapper.insertSelective(dataCommon);
    }

    @Override
    public void removeByIds(Set<Integer> ids, Integer archivesLibraryId) {
        DataCommon dataCommon = this.removeOrGetHandler(null, archivesLibraryId);
        dataTemporaryLibraryMapper.deleteByPrimaryKeys(ids, dataCommon.getTableName());
    }

    @Override
    public void updateById(DataCommonFormDto dataCommonFormDto) {
        DataCommon dataCommon = this.saveOrUpdateHandler(dataCommonFormDto);
        Map<String, Object> map = dataTemporaryLibraryMapper.selectByPrimaryKey(dataCommon);
        if (map != null) {
            dataTemporaryLibraryMapper.updateByPrimaryKeySelective(dataCommon);
        }
    }

    @Override
    public void updateStatusById(Integer id, Integer archivesLibraryId, Short status) {
        DataCommon dataCommon = this.removeOrGetHandler(id, archivesLibraryId);
        Map<String, Object> map = dataTemporaryLibraryMapper.selectByPrimaryKey(dataCommon);
        if (map != null) {
            List<DataCommonKV> dataCommonKVS = new ArrayList<>();
            DataCommonKV dataCommonKV = new DataCommonKV();
            dataCommonKV.setFieldName(EnumArchivesLibraryDefaultField.STATUS.getDataKey());
            dataCommonKV.setFieldValue(status);
            dataCommonKVS.add(dataCommonKV);
            dataCommon.setDataCommonKVs(dataCommonKVS);
            dataTemporaryLibraryMapper.updateByPrimaryKeySelective(dataCommon);
        }
    }

    @Override
    public Map<String, Object> getById(Integer id, Integer archivesLibraryId) {
        DataCommon dataCommon = this.removeOrGetHandler(id, archivesLibraryId);
        return dataTemporaryLibraryMapper.selectByPrimaryKey(dataCommon);
    }


    @Override
    public DataCommonVo getPage(DataCommonQueryDto dataCommonPageDto) {
        DataCommon dataCommon = this.page(dataCommonPageDto);
        dataCommon.getDataCommonKVs().add(this.getStatusField(EnumArchivesOrder.TEMPORARY_LIBRARY.getValue()));
        Integer archivesLibraryId = dataCommonPageDto.getArchivesLibraryId();
        Integer num = dataCommonPageDto.getNum();
        Integer size = dataCommonPageDto.getSize();

        DataCommonVo dataCommonVo = new DataCommonVo();
        dataCommonVo.setFields(this.getDataCommonVos(archivesLibraryId));
        dataCommonVo.setPage(dataTemporaryLibraryMapper.selectPage(new Page<>(num, size), dataCommon));
        return dataCommonVo;
    }

    @Override
    public void importExcelRecord(MultipartFile multipartFile, Integer archivesLibraryId) throws IOException {
        DataCommon dataCommon = this.importExcelFile(multipartFile, archivesLibraryId);
        dataCommon.getLists().forEach(dataCommonKVS -> dataCommonKVS.add(this.getStatusField(EnumArchivesOrder.TEMPORARY_LIBRARY.getValue())));
        dataTemporaryLibraryMapper.insertSelectiveBatch(dataCommon);
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

package com.yintu.rixing.data.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yintu.rixing.data.DataArchivesCollectionMapper;
import com.yintu.rixing.data.DataCommonAll;
import com.yintu.rixing.data.IDataArchivesCollectionService;
import com.yintu.rixing.dto.data.DataCommonFormDto;
import com.yintu.rixing.dto.data.DataCommonQueryDto;
import com.yintu.rixing.system.SysArchivesLibrary;
import com.yintu.rixing.util.AssertUtil;
import com.yintu.rixing.util.TableNameUtil;
import com.yintu.rixing.vo.data.DataCommonVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

/**
 * @Author: mlf
 * @Date: 2021/1/18 11:09:59
 * @Version: 1.0
 */
@Service
public class DataArchivesCollectionServiceImpl extends DataCommonService implements IDataArchivesCollectionService {
    @Autowired
    private DataArchivesCollectionMapper dataArchivesCollectionMapper;

    @Override
    public void save(DataCommonFormDto dataCommonFormDto) {
        DataCommonAll dataCommonAll = this.parametersToProofread(dataCommonFormDto);
        dataArchivesCollectionMapper.insertSelective(dataCommonAll);
    }

    @Override
    public void removeByIds(Set<Integer> ids, Integer archivesId) {
        AssertUtil.notNull(archivesId, "档案库id不能为空");
        SysArchivesLibrary sysArchivesLibrary = this.iSysArchivesLibraryService.getById(archivesId);
        AssertUtil.notNull(sysArchivesLibrary, "档案库不能为空");
        dataArchivesCollectionMapper.deleteByPrimaryKeys(ids, TableNameUtil.getFullTableName(sysArchivesLibrary.getDataKey()));
    }

    @Override
    public void updateById(DataCommonFormDto dataCommonFormDto) {
        DataCommonAll dataCommonAll = this.parametersToProofread(dataCommonFormDto);
        Map<String, Object> map = dataArchivesCollectionMapper.selectByPrimaryKey(dataCommonAll);
        if (map != null) {
            dataArchivesCollectionMapper.updateByPrimaryKeySelective(dataCommonAll);
        }
    }

    @Override
    public Map<String, Object> getById(DataCommonFormDto dataCommonFormDto) {
        SysArchivesLibrary sysArchivesLibrary = this.iSysArchivesLibraryService.getById(dataCommonFormDto.getArchivesId());
        AssertUtil.notNull(sysArchivesLibrary, "档案库不能为空");

        DataCommonAll dataCommonAll = new DataCommonAll();
        dataCommonAll.setId(dataCommonFormDto.getId());
        dataCommonAll.setTableName(TableNameUtil.getFullTableName(sysArchivesLibrary.getDataKey()));
        return dataArchivesCollectionMapper.selectByPrimaryKey(dataCommonAll);
    }


    @Override
    public DataCommonVo getPage(DataCommonQueryDto dataCommonPageDto) {
        Integer archivesId = dataCommonPageDto.getArchivesId();
        Integer num = dataCommonPageDto.getNum();
        Integer size = dataCommonPageDto.getSize();
        SysArchivesLibrary sysArchivesLibrary = this.iSysArchivesLibraryService.getById(archivesId);
        AssertUtil.notNull(sysArchivesLibrary, "档案库不能为空");
        DataCommonVo dataCommonVo = new DataCommonVo();
        dataCommonVo.setTitles(this.getDataCommonTitles(archivesId));
        dataCommonVo.setPage(dataArchivesCollectionMapper.selectPage(new Page<>(num, size), TableNameUtil.getFullTableName(sysArchivesLibrary.getDataKey())));
        return dataCommonVo;
    }

}

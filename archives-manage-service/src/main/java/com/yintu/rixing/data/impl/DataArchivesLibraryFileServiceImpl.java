package com.yintu.rixing.data.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yintu.rixing.data.DataArchivesLibraryFileMapper;
import com.yintu.rixing.data.DataArchivesLibraryFile;
import com.yintu.rixing.data.DataCommonAll;
import com.yintu.rixing.data.IDataArchivesLibraryFileService;
import com.yintu.rixing.dto.base.PageDto;
import com.yintu.rixing.dto.data.DataArchivesLibraryFileFormDto;
import com.yintu.rixing.dto.system.SysRoleFormDto;
import com.yintu.rixing.dto.system.SysUserQueryDto;
import com.yintu.rixing.system.ISysArchivesLibraryService;
import com.yintu.rixing.system.SysArchivesLibrary;
import com.yintu.rixing.system.SysUser;
import com.yintu.rixing.util.AssertUtil;
import com.yintu.rixing.util.TableNameUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * <p>
 * 数据档案库文件表 服务实现类
 * </p>
 *
 * @author mlf
 * @since 2021-01-19
 */
@Service
public class DataArchivesLibraryFileServiceImpl extends ServiceImpl<DataArchivesLibraryFileMapper, DataArchivesLibraryFile> implements IDataArchivesLibraryFileService {

    @Autowired
    private ISysArchivesLibraryService iSysArchivesLibraryService;
    @Autowired
    private DataCommonService dataCommonService;

    @Override
    public void save(DataArchivesLibraryFileFormDto dataArchivesLibraryFileFormDto) {
        Integer archivesLibraryId = dataArchivesLibraryFileFormDto.getArchivesLibraryId();
        Integer dataId = dataArchivesLibraryFileFormDto.getDataId();
        SysArchivesLibrary sysArchivesLibrary = iSysArchivesLibraryService.getById(archivesLibraryId);
        AssertUtil.notNull(sysArchivesLibrary, "档案库不存在");
        String tableName = TableNameUtil.getFullTableName(sysArchivesLibrary.getDataKey());
        DataCommonAll dataCommonAll = new DataCommonAll();
        dataCommonAll.setTableName(tableName);
        dataCommonAll.setId(dataId);
        Map<String, Object> map = dataCommonService.getById(dataCommonAll);
        AssertUtil.notNull(map, "档案库数据不存在");
        DataArchivesLibraryFile dataArchivesLibraryFile = new DataArchivesLibraryFile();
        BeanUtil.copyProperties(dataArchivesLibraryFileFormDto, dataArchivesLibraryFile);
        this.save(dataArchivesLibraryFile);
    }

    @Override
    public void updateById(DataArchivesLibraryFileFormDto dataArchivesLibraryFileFormDto) {

    }

    @Override
    public Page<DataArchivesLibraryFile> page(PageDto pageDto) {
        Integer num = pageDto.getNum();
        Integer size = pageDto.getSize();
        return this.page(new Page<>(num, size));
    }

}

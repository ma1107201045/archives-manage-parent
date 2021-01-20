package com.yintu.rixing.data.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yintu.rixing.data.DataArchivesLibraryFileMapper;
import com.yintu.rixing.data.DataArchivesLibraryFile;
import com.yintu.rixing.data.IDataArchivesLibraryFileService;
import com.yintu.rixing.dto.base.PageDto;
import com.yintu.rixing.dto.data.DataArchivesLibraryFileFormDto;
import com.yintu.rixing.dto.system.SysRoleFormDto;
import com.yintu.rixing.dto.system.SysUserQueryDto;
import com.yintu.rixing.system.SysUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


    @Override
    public void save(DataArchivesLibraryFileFormDto dataArchivesLibraryFileFormDto) {
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
        Page<DataArchivesLibraryFile> dataArchivesLibraryFilePage = this.page(new Page<>(num, size));
        return dataArchivesLibraryFilePage;
    }

}

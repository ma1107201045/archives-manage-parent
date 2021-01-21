package com.yintu.rixing.data;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yintu.rixing.dto.data.DataArchivesLibraryFileFormDto;
import com.yintu.rixing.dto.data.DataArchivesLibraryFileQueryDto;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 数据档案库文件表 服务类
 * </p>
 *
 * @author mlf
 * @since 2021-01-19
 */
public interface IDataArchivesLibraryFileService extends IService<DataArchivesLibraryFile> {

    @Transactional(rollbackFor = {Exception.class})
    void save(DataArchivesLibraryFileFormDto dataArchivesLibraryFileFormDto);

    @Transactional(rollbackFor = {Exception.class})
    void updateById(DataArchivesLibraryFileFormDto dataArchivesLibraryFileFormDto);

    @Transactional(rollbackFor = {Exception.class})
    void updateOrderByIds(Integer id1, Integer id2);

    Page<DataArchivesLibraryFile> page(DataArchivesLibraryFileQueryDto dataArchivesLibraryFileQueryDto);
}

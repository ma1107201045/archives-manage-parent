package com.yintu.rixing.data;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yintu.rixing.dto.data.DataArchivesLibraryFileFormDto;
import com.yintu.rixing.dto.data.DataArchivesLibraryFileQueryDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

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
    void removeByIds(Set<Integer> ids);

    @Transactional(rollbackFor = {Exception.class})
    void updateById(DataArchivesLibraryFileFormDto dataArchivesLibraryFileFormDto);

    @Transactional(rollbackFor = {Exception.class})
    void moveById(Integer id1, Integer id2);

    @Transactional(rollbackFor = {Exception.class})
    void resetByIds(Set<Integer> ids, String by);

    @Transactional(rollbackFor = {Exception.class})
    void updateRemark(Integer id, String remark);

    Page<DataArchivesLibraryFile> page(DataArchivesLibraryFileQueryDto dataArchivesLibraryFileQueryDto);
}

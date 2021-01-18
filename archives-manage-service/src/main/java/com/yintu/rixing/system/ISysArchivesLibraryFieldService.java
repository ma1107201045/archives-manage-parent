package com.yintu.rixing.system;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yintu.rixing.common.CommTableField;
import com.yintu.rixing.dto.system.SysArchivesLibraryFieldFormDto;
import com.yintu.rixing.dto.system.SysArchivesLibraryFieldQueryDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 系统档案库字段表 服务类
 * </p>
 *
 * @author mlf
 * @since 2021-01-14
 */
public interface ISysArchivesLibraryFieldService extends IService<SysArchivesLibraryField> {

    @Transactional(rollbackFor = {Exception.class})
    void save(SysArchivesLibraryFieldFormDto sysArchivesLibraryFieldFormDto);

    @Transactional(rollbackFor = {Exception.class})
    void removeByIds(Set<Integer> ids);

    @Transactional(rollbackFor = {Exception.class})
    void updateById(SysArchivesLibraryFieldFormDto sysArchivesLibraryFieldFormDto);

    @Transactional(rollbackFor = {Exception.class})
    void updateOrderByIds(Integer id1, Integer id2);

    List<Integer> listByArchivesLibraryIdDataKeys(Integer archivesLibraryId, String dataKey);

    List<Integer> listByArchivesLibraryIdAndTemplateLibraryId(Integer archivesLibraryId, Integer templateLibraryId);

    List<SysArchivesLibraryField> listByArchivesLibraryId(Integer archivesLibraryId);

    Page<SysArchivesLibraryField> page(SysArchivesLibraryFieldQueryDto sysArchivesLibraryFieldQueryDto);

}

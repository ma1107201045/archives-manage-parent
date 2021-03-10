package com.yintu.rixing.system;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yintu.rixing.dto.system.SysCommonFieldLibraryFormDto;
import com.yintu.rixing.dto.system.SysCommonFieldLibraryQueryDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 系统公共字段库表 服务类
 * </p>
 *
 * @author mlf
 * @since 2020-12-30
 */
public interface ISysCommonFieldLibraryService extends IService<SysCommonFieldLibrary> {

    @Transactional(rollbackFor = {Exception.class})
    void save(SysCommonFieldLibraryFormDto sysCommonFieldLibraryFormDto);

    @Transactional(rollbackFor = {Exception.class})
    void updateById(SysCommonFieldLibraryFormDto sysCommonFieldLibraryFormDto);

    @Transactional(rollbackFor = {Exception.class})
    void updateOrderByIds(Integer id1, Integer id2);

    List<Integer> listByDataKey(String dataKey);

    Page<SysCommonFieldLibrary> page(SysCommonFieldLibraryQueryDto sysCommonFieldLibraryQueryDto);


}

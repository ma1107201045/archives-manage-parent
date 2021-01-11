package com.yintu.rixing.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yintu.rixing.dto.system.SysArchivesLibraryFormDto;
import com.yintu.rixing.util.TreeUtil;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 系统档案库表 服务类
 * </p>
 *
 * @author mlf
 * @since 2021-01-11
 */
public interface ISysArchivesLibraryService extends IService<SysArchivesLibrary> {


    @Transactional(rollbackFor = {Exception.class})
    void save(SysArchivesLibraryFormDto sysArchivesLibraryFormDto);

    @Transactional(rollbackFor = {Exception.class})
    void removeTree(Integer id);

    @Transactional(rollbackFor = {Exception.class})
    void updateById(SysArchivesLibraryFormDto sysArchivesLibraryFormDto);

    List<Integer> listByNumber(Integer number);


    List<Integer> listByDataKey(String dataKey);


    List<TreeUtil> listTree(Integer parentId);
}

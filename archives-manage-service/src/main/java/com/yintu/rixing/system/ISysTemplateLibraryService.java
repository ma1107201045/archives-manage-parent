package com.yintu.rixing.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yintu.rixing.dto.system.SysTemplateLibraryFormDto;
import com.yintu.rixing.util.TreeUtil;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 系统模板库表 服务类
 * </p>
 *
 * @author mlf
 * @since 2020-12-30
 */
public interface ISysTemplateLibraryService extends IService<SysTemplateLibrary> {


    @Transactional(rollbackFor = {Exception.class})
    void save(SysTemplateLibraryFormDto sysTemplateLibraryFormDto);

    @Transactional(rollbackFor = {Exception.class})
    void removeTree(Integer id);

    @Transactional(rollbackFor = {Exception.class})
    void updateById(SysTemplateLibraryFormDto sysTemplateLibraryFormDto);

    List<Integer> listByNumber(Integer number);

    List<Short> listByParentId(Integer parentId);

    List<SysTemplateLibrary> listByType(Short type);

    List<TreeUtil> listTree(Integer parentId);

}

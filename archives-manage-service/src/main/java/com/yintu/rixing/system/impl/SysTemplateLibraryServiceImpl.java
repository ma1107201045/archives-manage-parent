package com.yintu.rixing.system.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yintu.rixing.dto.system.SysTemplateLibraryFormDto;
import com.yintu.rixing.system.ISysTemplateLibraryService;
import com.yintu.rixing.system.SysTemplateLibrary;
import com.yintu.rixing.system.SysTemplateLibraryMapper;
import com.yintu.rixing.util.TreeNodeUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 系统模板库表 服务实现类
 * </p>
 *
 * @author mlf
 * @since 2020-12-30
 */
@Service
public class SysTemplateLibraryServiceImpl extends ServiceImpl<SysTemplateLibraryMapper, SysTemplateLibrary> implements ISysTemplateLibraryService {

    @Override
    public void save(SysTemplateLibraryFormDto sysTemplateLibraryFormDto) {

    }

    @Override
    public void removeTree(Integer id) {

    }

    @Override
    public void updateById(SysTemplateLibraryFormDto sysTemplateLibraryFormDto) {

    }

    @Override
    public List<TreeNodeUtil> listTree(Integer parentId) {
        return null;
    }
}

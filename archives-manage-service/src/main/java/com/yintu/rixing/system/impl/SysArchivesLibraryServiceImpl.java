package com.yintu.rixing.system.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yintu.rixing.dto.system.SysArchivesLibraryFormDto;
import com.yintu.rixing.dto.system.SysTemplateLibraryFormDto;
import com.yintu.rixing.system.ISysArchivesLibraryService;
import com.yintu.rixing.system.SysArchivesLibrary;
import com.yintu.rixing.system.SysArchivesLibraryMapper;
import com.yintu.rixing.util.TreeUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 系统档案库表 服务实现类
 * </p>
 *
 * @author mlf
 * @since 2021-01-11
 */
@Service
public class SysArchivesLibraryServiceImpl extends ServiceImpl<SysArchivesLibraryMapper, SysArchivesLibrary> implements ISysArchivesLibraryService {


    @Override
    public void save(SysArchivesLibraryFormDto sysArchivesLibraryFormDto) {

    }

    @Override
    public void removeTree(Integer id) {

    }

    @Override
    public void updateById(SysArchivesLibraryFormDto sysArchivesLibraryFormDto) {

    }

    @Override
    public List<TreeUtil> listTree(Integer parentId) {
        return null;
    }
}

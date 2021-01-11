package com.yintu.rixing.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yintu.rixing.dto.system.SysArchivesLibraryFormDto;
import com.yintu.rixing.dto.system.SysTemplateLibraryFormDto;
import com.yintu.rixing.system.ISysArchivesLibraryService;
import com.yintu.rixing.system.SysArchivesLibrary;
import com.yintu.rixing.system.SysArchivesLibraryMapper;
import com.yintu.rixing.system.SysTemplateLibrary;
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
        this.removeById(id);
        QueryWrapper<SysArchivesLibrary> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .select(SysArchivesLibrary::getId)
                .eq(SysArchivesLibrary::getParentId, id);
        List<Integer> ids = this.listObjs(queryWrapper, obj -> (Integer) obj);
        for (Integer integer : ids) {
            this.removeTree(integer);
        }
    }

    @Override
    public void updateById(SysArchivesLibraryFormDto sysArchivesLibraryFormDto) {

    }

    @Override
    public List<Integer> listByNumber(Integer number) {
        QueryWrapper<SysArchivesLibrary> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .select(SysArchivesLibrary.class, tableFieldInfo -> tableFieldInfo.getColumn().equals("id"))
                .eq(SysArchivesLibrary::getNumber, number);
        return this.listObjs(queryWrapper, id -> (Integer) id);
    }

    @Override
    public List<TreeUtil> listTree(Integer parentId) {
        return null;
    }
}

package com.yintu.rixing.warehouse.impl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yintu.rixing.util.TreeUtil;
import com.yintu.rixing.warehouse.IWareLibraryTreeService;
import com.yintu.rixing.warehouse.WareLibraryTree;
import com.yintu.rixing.warehouse.WareLibraryTreeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 库房管理的树表 服务实现类
 * </p>
 *
 * @author Mr.liu
 * @since 2021-01-28
 */
@Service
public class WareLibraryTreeServiceImpl extends ServiceImpl<WareLibraryTreeMapper, WareLibraryTree> implements IWareLibraryTreeService {
    @Autowired
    private WareLibraryTreeMapper wareLibraryTreeMapper;


    @Override
    public Integer findParentId(Integer integer) {
        return wareLibraryTreeMapper.findParentId(integer);
    }

    @Override
    public List<TreeUtil> listTree(Integer parentId) {
        QueryWrapper<WareLibraryTree> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("parent_id",parentId);
        List<WareLibraryTree> wareLibraryTrees = this.list(queryWrapper);
        List<TreeUtil> treeUtils = new ArrayList<>();
        for (WareLibraryTree wareLibraryTree : wareLibraryTrees) {
            TreeUtil treeUtil = new TreeUtil();
            Integer id = wareLibraryTree.getId();
            treeUtil.setId(id.longValue());
            treeUtil.setLabel(wareLibraryTree.getName());
            treeUtil.setA_attr(BeanUtil.beanToMap(wareLibraryTree));
            treeUtil.setChildren(this.listTree(id));
            treeUtils.add(treeUtil);
        }
        return treeUtils;
    }
}

package com.yintu.rixing.make.impl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yintu.rixing.make.IMakeCompilationTreeService;
import com.yintu.rixing.make.MakeCompilationTree;
import com.yintu.rixing.make.MakeCompilationTreeMapper;
import com.yintu.rixing.util.TreeUtil;
import com.yintu.rixing.warehouse.WareLibraryTree;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 档案编研的树表 服务实现类
 * </p>
 *
 * @author Mr.liu
 * @since 2021-02-20
 */
@Service
public class MakeCompilationTreeServiceImpl extends ServiceImpl<MakeCompilationTreeMapper, MakeCompilationTree> implements IMakeCompilationTreeService {

    @Override
    public List<TreeUtil> listTree(Integer parentId) {
        QueryWrapper<MakeCompilationTree> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("parent_id",parentId);
        List<MakeCompilationTree> makeCompilationTrees = this.list(queryWrapper);
        List<TreeUtil> treeUtils = new ArrayList<>();
         for (MakeCompilationTree makeCompilationTree : makeCompilationTrees) {
            TreeUtil treeUtil = new TreeUtil();
            Integer id = makeCompilationTree.getId();
            treeUtil.setId(id.longValue());
            treeUtil.setLabel(makeCompilationTree.getName());
            treeUtil.setA_attr(BeanUtil.beanToMap(makeCompilationTree));
            treeUtil.setChildren(this.listTree(id));
            treeUtils.add(treeUtil);
        }
        return treeUtils;
    }
}

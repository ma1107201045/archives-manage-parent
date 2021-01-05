package com.yintu.rixing.common.impl;

import com.yintu.rixing.common.CommMenuDao;
import com.yintu.rixing.common.ICommMenuService;
import com.yintu.rixing.util.TreeUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author: mlf
 * @Date: 2021/1/4 10:40:02
 * @Version: 1.0
 */
public class CommMenuServiceImpl implements ICommMenuService {
    @Autowired
    private CommMenuDao commMenuDao;


    @Override
    public List<TreeUtil> findMenus(Integer parentId, Short menu) {
        return null;
    }

    @Override
    public List<TreeUtil> findMenusByUserId(Integer id, Integer parentId, Short menu) {
        return null;
    }
}

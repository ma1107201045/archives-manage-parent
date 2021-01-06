package com.yintu.rixing.common.impl;

import cn.hutool.core.bean.BeanUtil;
import com.yintu.rixing.common.CommMenuDao;
import com.yintu.rixing.common.ICommMenuService;
import com.yintu.rixing.enumobject.EnumFlag;
import com.yintu.rixing.util.TreeUtil;
import com.yintu.rixing.vo.common.CommMenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: mlf
 * @Date: 2021/1/4 10:40:02
 * @Version: 1.0
 */
@Service
public class CommMenuServiceImpl implements ICommMenuService {

    @Autowired
    private CommMenuDao commMenuDao;


    @Override
    public List<TreeUtil> findMenus(Integer userId, Integer permissionId) {
        List<CommMenuVo> commMenuVos;
        commMenuVos = userId == null ? commMenuDao.selectByExample(permissionId, EnumFlag.True.getValue()) :
                commMenuDao.selectByExampleAndUserId(permissionId, EnumFlag.True.getValue(), userId);
        List<TreeUtil> treeUtils = new ArrayList<>();
        for (CommMenuVo commMenuVo : commMenuVos) {
            TreeUtil treeUtil = new TreeUtil();
            treeUtil.setId(commMenuVo.getId().longValue());
            treeUtil.setLabel(commMenuVo.getName());
            treeUtil.setA_attr(BeanUtil.beanToMap(commMenuVo));
            treeUtil.setChildren(this.findMenus(userId, commMenuVo.getId()));
            treeUtils.add(treeUtil);
        }
        return treeUtils;
    }
}
